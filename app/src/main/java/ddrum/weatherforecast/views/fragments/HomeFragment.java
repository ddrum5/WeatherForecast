package ddrum.weatherforecast.views.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseFragment;
import ddrum.weatherforecast.databinding.FragmentHomeBinding;
import ddrum.weatherforecast.models.Coord;
import ddrum.weatherforecast.models.SearchHistory;
import ddrum.weatherforecast.ulti.Util;
import ddrum.weatherforecast.viewmodels.MainViewModel;
import ddrum.weatherforecast.views.adapters.SimpleWeatherAdapter;

public class HomeFragment extends BaseFragment<MainViewModel, FragmentHomeBinding> {

    private SimpleWeatherAdapter adapter;
    private ArrayAdapter<String> suggestionAdapter;

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
        suggestionAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line);
        binding.rcv.setAdapter(adapter);
        binding.lvSuggestion.setAdapter(suggestionAdapter);
        event();
    }

    @Override
    protected void initObserve() {
        viewModel.simpleWeatherList.observe(getViewLifecycleOwner(), list -> {
            if (list != null) {
                adapter.updateData(list);
            } else {
                adapter.clearData();
            }
        });
        viewModel.defaultWeather.observe(getViewLifecycleOwner(), currentWeather -> {
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
        viewModel.searchHistoryList.observe(getViewLifecycleOwner(), searchHistories -> {
            if (searchHistories != null) {
                suggestionAdapter.clear();
                suggestionAdapter.addAll(Util.getTextList(searchHistories));
            }
        });
    }

    private void event() {
        searchBarEvent();
        binding.swiperFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Coord coord = viewModel.currentLocation.getValue();
                if (coord != null)
                    viewModel.setDefaultWeather(coord.getLat().toString(), coord.getLon().toString());

                binding.currentWeather.time.setText(getCurrentTime());
                Snackbar.make(getView(), "Đã cập nhật lúc " + getCurrentTime(), Snackbar.LENGTH_SHORT).show();
                binding.swiperFresh.setRefreshing(false);
            }
        });
        adapter.setClick(new SimpleWeatherAdapter.Callback() {
            @Override
            public void onClick(String cityId) {
                goToDetail(cityId);
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

        binding.currentWeather.defaultWeather.setOnClickListener(v -> {
            if (viewModel.defaultWeather.getValue() != null) {
                goToDetail(viewModel.defaultWeather.getValue().getId().toString());
            }
        });

    }

    private void goToDetail(String cityId) {
        viewModel.setWeatherDetail(cityId);
        Bundle bundle = new Bundle();
        bundle.putString("cityId", cityId);
        navigateTo(R.id.action_homeFragment2_to_detailsFragment2, bundle);
    }

    private void searchBarEvent() {
        binding.searchBar.addTextChangeListener(searchWatcher);
        binding.searchBar.setOnSearchActionListener(searchActionListener);
        binding.lvSuggestion.setOnItemClickListener((parent, view, position, id) -> {
            searchCity(suggestionAdapter.getItem(position));
        });
    }

    private final TextWatcher searchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            suggestionAdapter.getFilter().filter(s);
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
            searchCity(text.toString());
        }

        @Override
        public void onButtonClicked(int buttonCode) {
        }
    };

    private void searchCity(String text) {
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


}


