package ddrum.weatherforecast.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseFragment;
import ddrum.weatherforecast.databinding.FragmentDetailsBinding;
import ddrum.weatherforecast.models.CurrentWeather;
import ddrum.weatherforecast.models.OneCallWeather;
import ddrum.weatherforecast.viewmodels.MainViewModel;


public class DetailsFragment extends BaseFragment<MainViewModel, FragmentDetailsBinding> {


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
        init();

    }

    private void event(CurrentWeather.Coord coord) {
        binding.btnAddToList.setOnClickListener(v -> {
            viewModel.getRefCoord().setValue(coord);
        });
    }
    String lat;
    String lon;
    String cityName;

    private void init() {
        String ctName = getArguments().getString("cityName");
        viewModel.currentWeather.observe(this, new Observer<CurrentWeather>() {
            @Override
            public void onChanged(CurrentWeather currentWeather) {
                if (currentWeather != null) {
                    lat = currentWeather.getCoord().getLat().toString();
                    lon = currentWeather.getCoord().getLon().toString();
                    cityName = currentWeather.getName();
                    viewModel.setOneCallWeather(lat, lon);
                    viewModel.oneCallWeather.observe(getViewLifecycleOwner(), new Observer<OneCallWeather>() {
                        @Override
                        public void onChanged(OneCallWeather oneCallWeather) {
                            String description = oneCallWeather.getCurrent().getWeather().get(0).getDescription();
                            String temp = Math.round(oneCallWeather.getCurrent().getTemp()) + getString(R.string.tempUnit);
                            String feelsLike = "cảm giác như " + Math.round(oneCallWeather.getCurrent().getFeelsLike());
                            String iconUrl = "http://openweathermap.org/img/wn/" + oneCallWeather.getCurrent().getWeather().get(0).getIcon() + "@2x.png";

                            binding.currentTvCityName.setText(cityName);
                            binding.currentTvDescription.setText(description);
                            binding.currentTvTemp.setText(temp);
                            binding.currentTvFeelsLike.setText(feelsLike);
                            Glide.with(getActivity()).load(iconUrl).into(binding.currentIconWeather);
                        }
                    });
                } else {
                    longSnackBar("Có lỗi");
                }

            }
        });

    }
}


