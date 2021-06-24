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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseFragment;
import ddrum.weatherforecast.databinding.FragmentHomeBinding;
import ddrum.weatherforecast.models.CurrentWeather;
import ddrum.weatherforecast.models.UserWeather;
import ddrum.weatherforecast.ulti.Ulti;
import ddrum.weatherforecast.viewmodels.MainViewModel;
import ddrum.weatherforecast.views.adapters.WeatherAdapter;

public class HomeFragment extends BaseFragment<MainViewModel, FragmentHomeBinding> {

    WeatherAdapter adapter;
    List<UserWeather.Coord> list = new ArrayList<>();


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
        initInstance();
        viewModel.init();
        searchBarEvent();
        binding();
        event();


    }

    private void event() {
        binding.swiperFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.currentLocation.observe(getViewLifecycleOwner(), new Observer<UserWeather.Coord>() {
                    @Override
                    public void onChanged(UserWeather.Coord coord) {
                        if (coord != null) {
                            viewModel.setDefaultWeather(coord.getLat().toString(), coord.getLon().toString());
                        }
                    }
                });

                binding.currentWeather.time.setText(getCurrentTime());
                Snackbar.make(getView(), "Đã cập nhật lúc " + getCurrentTime(), Snackbar.LENGTH_SHORT).show();

                binding.swiperFresh.setRefreshing(false);
            }
        });
    }


    private void binding() {
        viewModel.defaultWeather.observe(this, new Observer<CurrentWeather>() {
            @Override
            public void onChanged(CurrentWeather currentWeather) {
                String cityName = currentWeather.getName();
                String description = currentWeather.getWeather().get(0).getDescription();
                String temp = Math.round(currentWeather.getMain().getTemp()) + getString(R.string.tempUnit);
                String tempMinMax = "t:" + Math.round(currentWeather.getMain().getTempMin())
                        + " c:" + Math.round(currentWeather.getMain().getTempMax());
                String iconUrl = "http://openweathermap.org/img/wn/" + currentWeather.getWeather().get(0).getIcon() + "@2x.png";

                binding.currentWeather.currentTvCityName.setText(cityName);
                binding.currentWeather.currentTvDescription.setText(description);
                binding.currentWeather.currentTvTemp.setText(temp);
                binding.currentWeather.currentTvTempMinMax.setText(tempMinMax);
                binding.currentWeather.time.setText(Ulti.getCurrentTime());
                Glide.with(getActivity()).load(iconUrl).into(binding.currentWeather.currentIconWeather);
            }
        });


        viewModel.weatherList.observe(this, list -> {
            if (list != null) {
                adapter.updateData(list);
            }
        });

        viewModel.userWeather.observe(this, new Observer<HashMap<String, Object>>() {
            @Override
            public void onChanged(HashMap<String, Object> map) {

            }
        });


    }


    public void initInstance() {
        adapter = new WeatherAdapter(getContext());
        binding.rcv.setAdapter(adapter);

    }

    private void searchBarEvent() {
        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                viewModel.getCheckCityName(text.toString());
                viewModel.checkCity.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean != null) {
                            if (!aBoolean){
                                longSnackBar("Không tìm thấy thành phố");

                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("cityName", text.toString());
                                navigateTo(R.id.detailsFragment2, bundle);
                            }
                            viewModel.checkCity.setValue(null);
                            return;
                        }
                    }
                });

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

}