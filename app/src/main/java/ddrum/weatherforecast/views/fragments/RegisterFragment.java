package ddrum.weatherforecast.views.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;


import org.jetbrains.annotations.NotNull;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseFragment;
import ddrum.weatherforecast.databinding.FragmentRegisterBinding;
import ddrum.weatherforecast.viewmodels.AuthViewModel;

public class RegisterFragment extends BaseFragment<AuthViewModel, FragmentRegisterBinding> {


    @Override
    protected int getLayout() {
        return R.layout.fragment_register;
    }

    @Override
    protected AuthViewModel getViewModel() {
        return new AuthViewModel();
    }

    @Override
    protected void initView(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        onClickListener(binding.btnBack, binding.btnRegister);
    }
    @Override
    protected void initObserve() {

        viewModel.isLoginSuccessful.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoginSuccessful) {
                if (isLoginSuccessful!=null)
                    if (isLoginSuccessful) {
                        shortSnackBar("Đăng ký thành công");
                        navigateTo(R.id.profileFragment);
                        viewModel.isLoginSuccessful.setValue(null);
                    } else {
                        shortSnackBar("có lỗi");
                    }
            }
        });

        viewModel.message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null){
                    shortSnackBar(s);
                    viewModel.message.setValue(null);
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register();
                break;
            case R.id.btnBack:
                popBackStack();
                break;
        }
    }
    private void register() {
        String email = binding.edtEmail.getText().toString().trim();
        String pass = binding.edtPassword.getText().toString();
        String passConfirm = binding.edtPasswordConfirm.getText().toString();
        viewModel.registerClick(email, pass, passConfirm);

    }


}
