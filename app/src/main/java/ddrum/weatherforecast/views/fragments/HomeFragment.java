package ddrum.weatherforecast.views.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseFragment;
import ddrum.weatherforecast.databinding.FragmentHomeBinding;
import ddrum.weatherforecast.models.Constant;
import ddrum.weatherforecast.models.UserWeather;
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
                            viewModel.setCurrentLocationWeather(coord.getLat().toString(), coord.getLon().toString());
                        }
                    }
                });

                upload();
                binding.currentWeather.time.setText(getCurrentTime());
                Snackbar.make(getView(), "Đã cập nhật lúc " + getCurrentTime(), Snackbar.LENGTH_SHORT).show();

                binding.swiperFresh.setRefreshing(false);
            }
        });
    }


    private void binding() {
        viewModel.currentLocation.observe(this, coord -> {
            if (coord != null) {
                viewModel.setCurrentLocationWeather(coord.getLat().toString(), coord.getLon().toString());
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


    public void upload() {
    }


    private void searchBarEvent() {
        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Bundle bundle = new Bundle();
                bundle.putString("cityName", text.toString());
                navigateTo(R.id.detailsFragment2, bundle);

            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });

    }

}