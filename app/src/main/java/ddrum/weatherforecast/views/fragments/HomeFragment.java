package ddrum.weatherforecast.views.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseFragment;
import ddrum.weatherforecast.databinding.FragmentHomeBinding;
import ddrum.weatherforecast.models.Constant;
import ddrum.weatherforecast.models.Coord;
import ddrum.weatherforecast.models.CurrentWeather;
import ddrum.weatherforecast.models.User;
import ddrum.weatherforecast.ulti.Ulti;
import ddrum.weatherforecast.viewmodels.MainViewModel;
import ddrum.weatherforecast.views.adapters.WeatherAdapter;

public class HomeFragment extends BaseFragment<MainViewModel, FragmentHomeBinding> {

    WeatherAdapter adapter;
    List<String> listCityId;
    SharedPreferences sharedPreferences;

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
        adapter = new WeatherAdapter(getContext());
        binding.rcv.setAdapter(adapter);
        binding();
        event();
    }

    @Override
    protected void initObserve() {
        viewModel.isLogged.observe(this, aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {

                } else {

                }
            }
        });
        viewModel.simpleWeatherList.observe(this, list -> {
            if(list!=null){
                adapter.updateData(list);
            }
        });
    }

    private void event() {
        searchBarEvent();
        binding.swiperFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.currentLocation.observe(getViewLifecycleOwner(), new Observer<Coord>() {
                    @Override
                    public void onChanged(Coord coord) {
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
        adapter.setClick(new WeatherAdapter.Callback() {
            @Override
            public void onClick(String cityId) {
                shortSnackBar(cityId);
            }

            @Override
            public void onLongClick(String cityId) {
                showSimpleDialog("Xoá địa điểm?");
                setOnclickDialog(new Callback() {
                    @Override
                    public void onClick() {
                        viewModel.removeLocation(cityId);
                        shortSnackBar("Đã xoá");
                    }
                });
            }
        });
    }

    private void binding() {
        viewModel.defaultWeather.observe(this, currentWeather -> {
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
        });

    }

    private void searchBarEvent() {
        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                viewModel.checkCity(text.toString());
                ProgressDialog dialog = ProgressDialog.show(getContext(), "", "Đang tìm kiếm...", true);
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (!viewModel.checkCity) {
                            longSnackBar("Không tìm thấy thành phố");
                        } else {
                            navigateTo(R.id.detailsFragment2);
                        }
                        dialog.dismiss();
                    }
                }, 1000);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }
}


