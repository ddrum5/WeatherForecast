package ddrum.weatherforecast.views.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.view.View;

import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseFragment;
import ddrum.weatherforecast.databinding.FragmentProfileBinding;
import ddrum.weatherforecast.models.Constant;
import ddrum.weatherforecast.viewmodels.MainViewModel;


public class ProfileFragment extends BaseFragment<MainViewModel, FragmentProfileBinding> {


    @Override
    protected int getLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected MainViewModel getViewModel() {
        return new MainViewModel();
    }


    @Override
    protected void initView(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        viewModel.initUser();
        event();
    }
    @Override
    protected void initObserve() {
        viewModel.isLogged.observe(this, isLogin -> {
            if (isLogin) {
                binding.btnSignIn.setVisibility(View.GONE);
                binding.Logged.setVisibility(View.VISIBLE);
            } else {
                binding.btnSignIn.setVisibility(View.VISIBLE);
                binding.Logged.setVisibility(View.GONE);
            }
        });
        viewModel.user.observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    binding.email.setText(firebaseUser.getEmail());
                }
                viewModel.isLogged.setValue(firebaseUser != null);
            }
        });

    }

    private void event() {
        binding.btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.logout();
                viewModel.updateUserState();
                shortSnackBar("Đã đăng xuất");
            }
        });
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(R.id.action_profileFragment2_to_loginFragment2);
            }
        });
    }

}