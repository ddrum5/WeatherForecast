package ddrum.weatherforecast.views.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;

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
import ddrum.weatherforecast.models.Coord;
import ddrum.weatherforecast.ulti.Util;
import ddrum.weatherforecast.viewmodels.MainViewModel;
import ddrum.weatherforecast.views.adapters.SimpleWeatherAdapter;

public class HomeFragment extends BaseFragment<MainViewModel, FragmentHomeBinding> {

    private SimpleWeatherAdapter adapter;

    private ArrayAdapter<String> arrayAdapter;

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
        adapter = new SimpleWeatherAdapter(requireContext());
        binding.rcv.setAdapter(adapter);
        event();
    }

    @Override
    protected void initObserve() {
        viewModel.simpleWeatherList.observe(this, list -> {
            if (list != null) {
                adapter.updateData(list);
            }
        });

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
            binding.currentWeather.time.setText(Util.getCurrentTime());
            Glide.with(requireContext()).load(iconUrl).into(binding.currentWeather.currentIconWeather);
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
        adapter.setClick(new SimpleWeatherAdapter.Callback() {
            @Override
            public void onClick(String cityId) {
                viewModel.setWeatherDetail(cityId);
                Bundle bundle = new Bundle();
                bundle.putString("cityId", cityId);
                navigateTo(R.id.action_homeFragment2_to_detailsFragment2, bundle);
            }
            @Override
            public void onLongClick(String cityId) {
                showSimpleDialog("Xoá địa điểm?");
                setOnclickDialog(new Callback() {
                    @Override
                    public void onClick() {
                        if (viewModel.isLogged.getValue()){
                            viewModel.removeLocation(cityId);
                        } else {
                            viewModel.removeFvLocationLocal(cityId);
                        }
                        shortSnackBar("Đã xoá");
                    }
                });
            }
        });
    }



    private void searchBarEvent() {
//        arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, list);
        binding.lvSuggestion.setAdapter(arrayAdapter);
        binding.searchBar.addTextChangeListener(searchWatcher);
        binding.searchBar.setOnSearchActionListener(searchActionListener);

    }


    private final TextWatcher searchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            arrayAdapter.getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };



    private final MaterialSearchBar.OnSearchActionListener searchActionListener = new MaterialSearchBar.OnSearchActionListener() {
        @Override
        public void onSearchStateChanged(boolean enabled) {
            binding.lvSuggestion.setVisibility(enabled ? View.VISIBLE : View.GONE);
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
    };
}


