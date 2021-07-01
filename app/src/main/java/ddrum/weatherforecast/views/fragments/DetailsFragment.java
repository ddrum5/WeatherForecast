package ddrum.weatherforecast.views.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.os.Handler;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseFragment;
import ddrum.weatherforecast.databinding.FragmentDetailsBinding;
import ddrum.weatherforecast.models.Coord;
import ddrum.weatherforecast.models.OneCallWeather;
import ddrum.weatherforecast.viewmodels.MainViewModel;
import ddrum.weatherforecast.views.adapters.DailyWeatherAdapter;
import ddrum.weatherforecast.views.adapters.HourlyWeatherAdapter;


public class DetailsFragment extends BaseFragment<MainViewModel, FragmentDetailsBinding> {

    Double lat;
    Double lon;
    String cityId;
    Coord coord;
    HourlyWeatherAdapter hourlyWeatherAdapter;
    DailyWeatherAdapter dailyWeatherAdapter;


    @Override
    protected int getLayout() {
        return R.layout.fragment_details;
    }

    @Override
    protected MainViewModel getViewModel() {
        return new MainViewModel();
    }


    @Override
    protected void initView(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        hourlyWeatherAdapter = new HourlyWeatherAdapter(getContext());
        dailyWeatherAdapter = new DailyWeatherAdapter(getContext());
        binding.rcvWeatherHourly.setAdapter(hourlyWeatherAdapter);
        binding.rcvWeatherDaily.setAdapter(dailyWeatherAdapter);
        binding();
        event();
    }

    private void binding() {
        viewModel.oneCallWeather.observe(this, oneCallWeather -> {
            if (oneCallWeather != null) {
                List<OneCallWeather.Hourly> hourlyList = oneCallWeather.getHourly();
                List<OneCallWeather.Daily> dailyList = oneCallWeather.getDaily();
                hourlyWeatherAdapter.updateData(hourlyList);
                dailyWeatherAdapter.updateData(dailyList);
            }
        });

        viewModel.simpleWeather.observe(getViewLifecycleOwner(), currentWeather -> {
            if (currentWeather != null) {
                lat = currentWeather.getCoord().getLat();
                lon = currentWeather.getCoord().getLon();
                cityId = currentWeather.getId().toString();
                coord = new Coord(viewModel.getUserId(), cityId, lat, lon);
                String cityName = currentWeather.getName() + ", " + currentWeather.getSys().getCountry();
                viewModel.setOneCallWeather(lat.toString(), lon.toString());
                viewModel.oneCallWeather.observe(getViewLifecycleOwner(), new Observer<OneCallWeather>() {
                    @Override
                    public void onChanged(OneCallWeather oneCallWeather) {
                        String description = oneCallWeather.getCurrent().getWeather().get(0).getDescription();
                        String temp = Math.round(oneCallWeather.getCurrent().getTemp()) + getString(R.string.tempUnit) + "C";
                        String feelsLike = "cảm giác như " + Math.round(oneCallWeather.getCurrent().getFeelsLike());
                        String iconUrl = "http://openweathermap.org/img/wn/" + oneCallWeather.getCurrent().getWeather().get(0).getIcon() + "@2x.png";

                        binding.currentTvCityName.setText(cityName);
                        binding.currentTvDescription.setText(description);
                        binding.currentTvTemp.setText(temp);
                        binding.currentTvFeelsLike.setText(feelsLike);
                        Glide.with(getActivity()).load(iconUrl).into(binding.currentIconWeather);
                    }
                });
                viewModel.setOneCallWeather(lat.toString(), lon.toString());
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.fvLocationList.observe(getViewLifecycleOwner(), coords -> {
                    if (coords != null) {
                        if (containsCityId(coords, cityId)) {
                            binding.btnAddToList.setVisibility(View.GONE);
                        } else {
                            binding.btnAddToList.setVisibility(View.VISIBLE);
                        }
                    }
                });

                viewModel.fvLocationListLocal.observe(getViewLifecycleOwner(), list -> {
                    if (list != null) {
                        if (containsCityIdString(list, cityId)) {
                            binding.btnAddToList.setVisibility(View.GONE);
                        } else {
                            binding.btnAddToList.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        },0);


    }

    private void event() {
        binding.btnAddToList.setOnClickListener(v -> {
            if (viewModel.isLogged.getValue()) {
                addLocationToFB();
            } else {
                addLocationToLocal();
            }
        });

    }

    private void addLocationToFB() {
        viewModel.getRefLocations()
                .document(System.currentTimeMillis() + "")
                .set(coord)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isComplete()) {
                            shortSnackBar("Thêm địa điểm thành công");
                        } else {
                            shortSnackBar("Có lỗi" + task.getException());
                        }
                    }
                });

    }

    private void addLocationToLocal() {
        viewModel.setFvLocationListLocal(getContext(), cityId);
        shortSnackBar("Thêm địa điểm thành công");
    }

    public boolean containsCityId(final List<Coord> list, final String cityId) {
        return list.stream().anyMatch(o -> o.getCityId().equals(cityId));
    }

    public boolean containsCityIdString(final List<String> list, final String cityId) {
        return list.stream().anyMatch(o -> o.equals(cityId));
    }


}


