package ddrum.weatherforecast.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseFragment;
import ddrum.weatherforecast.databinding.FragmentDetailsBinding;
import ddrum.weatherforecast.viewmodels.MainViewModel;


public class DetailsFragment extends BaseFragment<MainViewModel,FragmentDetailsBinding> {

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

    }




}