package ddrum.weatherforecast.views.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.jetbrains.annotations.NotNull;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseFragment;
import ddrum.weatherforecast.databinding.FragmentHomeBinding;
import ddrum.weatherforecast.models.CurrentWeather;
import ddrum.weatherforecast.models.UserWeather;
import ddrum.weatherforecast.viewmodels.MainViewModel;

public class HomeFragment extends BaseFragment<MainViewModel, FragmentHomeBinding> {



    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected MainViewModel getViewModel() {
        return new MainViewModel();
    }

    @Override
    protected void initView(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        searchBarEvent();
        event();
        binding();
    }

    private void event() {
        viewModel.currentLocation.observe(requireActivity(), new Observer<UserWeather.Coord>() {
            @Override
            public void onChanged(UserWeather.Coord coord) {
                if (coord != null) {
                    viewModel.setCurrentWeather(coord.getLat().toString(), coord.getLon().toString());
                }
            }
        });
        binding.swiperFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.currentLocation.observe(requireActivity(), new Observer<UserWeather.Coord>() {
                    @Override
                    public void onChanged(UserWeather.Coord coord) {
                        if (coord != null) {
                            viewModel.setCurrentWeather(coord.getLat().toString(), coord.getLon().toString());
                        }
                    }
                });
                binding.currentWeather.time.setText(getCurrentTime());
                Snackbar.make(getView(), "Đã cập nhật lúc "+getCurrentTime(), Snackbar.LENGTH_SHORT).show();
                binding.swiperFresh.setRefreshing(false);
            }
        });


    }


    private void binding() {
        viewModel.currentWeather.observe(this, new Observer<CurrentWeather>() {
            @Override
            public void onChanged(CurrentWeather currentWeather) {
                if (currentWeather != null) {

                    String cityName = currentWeather.getName();
                    String description = currentWeather.getWeather().get(0).getDescription();
                    String temp = String.valueOf(Math.round(currentWeather.getMain().getTemp())) + getString(R.string.tempUnit);
                    String tempMinMax = "t:" + String.valueOf(Math.round(currentWeather.getMain().getTempMin()))
                            + " c:" + String.valueOf(Math.round(currentWeather.getMain().getTempMax()));
                    String iconUrl = "http://openweathermap.org/img/wn/" + currentWeather.getWeather().get(0).getIcon() + "@2x.png";
                    binding.currentWeather.currentTvCityName.setText(cityName);
                    binding.currentWeather.currentTvDescription.setText(description);
                    binding.currentWeather.currentTvTemp.setText(temp);
                    binding.currentWeather.currentTvTempMinMax.setText(tempMinMax);
                    binding.currentWeather.time.setText(getCurrentTime());
                    Glide.with(requireActivity()).load(iconUrl).into(binding.currentWeather.currentIconWeather);

                } else {
                    Snackbar.make(getView(), "Không có dữ liệu", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }


    private void searchBarEvent() {
        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                viewModel.setCurrentWeather(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });

    }

}