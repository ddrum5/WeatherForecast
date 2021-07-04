package ddrum.weatherforecast.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseFragment;
import ddrum.weatherforecast.databinding.FragmentDetailsBinding;
import ddrum.weatherforecast.models.Coord;
import ddrum.weatherforecast.models.FvLocation;
import ddrum.weatherforecast.models.OneCallWeather;
import ddrum.weatherforecast.ulti.Util;
import ddrum.weatherforecast.viewmodels.MainViewModel;
import ddrum.weatherforecast.views.adapters.DailyWeatherAdapter;
import ddrum.weatherforecast.views.adapters.HourlyWeatherAdapter;


public class DetailsFragment extends BaseFragment<MainViewModel, FragmentDetailsBinding> {


    String cityId;
    FvLocation fvLocation;
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
        onClickListener(binding.btnAddToList);
        init();
    }
    private void init() {
        hourlyWeatherAdapter = new HourlyWeatherAdapter(getContext());
        dailyWeatherAdapter = new DailyWeatherAdapter(getContext());
        binding.rcvWeatherHourly.setAdapter(hourlyWeatherAdapter);
        binding.rcvWeatherDaily.setAdapter(dailyWeatherAdapter);
        if (getArguments() != null){
            cityId = getArguments().getString("cityId");
        }
    }

    @Override
    protected void initObserve() {
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
                cityId = currentWeather.getId().toString();
                fvLocation = new FvLocation(viewModel.getUserId(), cityId);
                String cityName = currentWeather.getName() + ", " + currentWeather.getSys().getCountry();
                binding.currentTvCityName.setText(cityName);
            }
        });
        viewModel.oneCallWeather.observe(getViewLifecycleOwner(), new Observer<OneCallWeather>() {
            @Override
            public void onChanged(OneCallWeather oneCallWeather) {
                String description = oneCallWeather.getCurrent().getWeather().get(0).getDescription();
                String temp = Math.round(oneCallWeather.getCurrent().getTemp()) + getString(R.string.tempUnit) + "C";
                String feelsLike = Util.upperCaseFirstLetter("cảm giác như " + Math.round(oneCallWeather.getCurrent().getFeelsLike()) + "°") ;
                String iconUrl = "http://openweathermap.org/img/wn/" + oneCallWeather.getCurrent().getWeather().get(0).getIcon() + "@2x.png";
                binding.currentTvDescription.setText(description);
                binding.currentTvTemp.setText(temp);
                binding.currentTvFeelsLike.setText(feelsLike);
                Glide.with(getActivity()).load(iconUrl).into(binding.currentIconWeather);
            }
        });

        viewModel.fvLocationList.observe(getViewLifecycleOwner(), list -> {
            if (list != null) {
                if (containsCityId(list, cityId)) {
                    binding.btnAddToList.setVisibility(View.GONE);
                } else {
                    binding.btnAddToList.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.fvLocationListLocal.observe(getViewLifecycleOwner(), list -> {
            if (list != null) {
                if (containsCityId(list, cityId)) {
                    binding.btnAddToList.setVisibility(View.GONE);
                } else {
                    binding.btnAddToList.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public boolean containsCityId(final List<FvLocation> list, final String cityId) {
        return list.stream().anyMatch(o -> o.getCityId().equals(cityId));
    }

    @Override
    public void onClick(View v) {
        viewModel.addLocation(fvLocation);
    }
}


