package ddrum.weatherforecast;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import java.util.ArrayList;
import java.util.List;

import ddrum.weatherforecast.base.BaseActivity;
import ddrum.weatherforecast.databinding.ActivityMainBinding;
import ddrum.weatherforecast.models.Coord;
import ddrum.weatherforecast.models.User;
import ddrum.weatherforecast.viewmodels.MainViewModel;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {
    List<User> userList = new ArrayList<>();


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected MainViewModel getViewModel() {
        return new MainViewModel();
    }

    @Override
    protected void initView(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        viewModel.initUser();
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
        event();
    }

    private void event() {
        viewModel.currentLocation.observe(this, new Observer<Coord>() {
            @Override
            public void onChanged(Coord coord) {
                viewModel.setDefaultWeather(coord.getLat().toString(), coord.getLon().toString());
            }
        });
        viewModel.isLogged.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    viewModel.setFvLocationList();
                    viewModel.fvLocationListLocal.setValue(null);
                } else {
                    viewModel.fvLocationList.setValue(null);
                    viewModel.initFvLocationLocal(MainActivity.this);
                }
            }
        });
        viewModel.fvLocationList.observe(this, new Observer<List<Coord>>() {
            @Override
            public void onChanged(List<Coord> coords) {
                if (coords != null) {
                    viewModel.setSimpleWeatherList(coords);
                } else {
                    viewModel.simpleWeatherList.setValue(null);
                }
            }
        });
        viewModel.fvLocationListLocal.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> list) {
                if (list != null) {
                    viewModel.setSimpleWeatherListByFromLocal(list);
                } else {
                    viewModel.simpleWeatherList.setValue(null);
                }
            }
        });
    }


}
