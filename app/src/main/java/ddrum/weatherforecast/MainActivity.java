package ddrum.weatherforecast;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import ddrum.weatherforecast.base.BaseActivity;
import ddrum.weatherforecast.databinding.ActivityMainBinding;
import ddrum.weatherforecast.models.Coord;
import ddrum.weatherforecast.models.FvLocation;
import ddrum.weatherforecast.viewmodels.MainViewModel;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

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
        viewModel.initDAO(MainActivity.this);
    }

    @Override
    protected void initObserve() {
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
                    viewModel.setSearchHistoryList();
                } else {
                    viewModel.setFvLocationList();
                    viewModel.updateSearchHistoryListFromLocal();
                }
            }
        });
        viewModel.fvLocationList.observe(this, new Observer<List<FvLocation>>() {
            @Override
            public void onChanged(List<FvLocation> fvLocations) {
                if (fvLocations != null) {
                    viewModel.setSimpleWeatherList(fvLocations);
                } else {
                    viewModel.simpleWeatherList.setValue(null);
                }
            }
        });




    }


}
