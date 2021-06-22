package ddrum.weatherforecast;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import ddrum.weatherforecast.base.BaseActivity;
import ddrum.weatherforecast.databinding.ActivityMainBinding;
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
        event();
    }

    private void event() {

    }


}

