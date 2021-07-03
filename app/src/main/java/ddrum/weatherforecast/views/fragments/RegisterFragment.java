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
        viewModel.existedEmail.observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean !=null) {
                            if (aBoolean){
                                Toast.makeText(getContext(), "Email đã tồn tại", Toast.LENGTH_LONG).show();
                            } else {
                                viewModel.existedEmail.setValue(null);
                            }
                        }
                    }
                }
        );
        viewModel.isLoginSuccessful.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoginSuccessful) {
                if (isLoginSuccessful!=null)
                    if (isLoginSuccessful) {
                        navigateTo(R.id.profileFragment);
                        viewModel.isLoginSuccessful.setValue(null);
                    } else {
                        Toast.makeText(getContext(), "Có lỗi", Toast.LENGTH_SHORT).show();
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

        if (!pass.equals(passConfirm)) {
            Toast.makeText(getContext(), "Xác nhận mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (pass.length() < 6) {
                Toast.makeText(getContext(), "Mật khẩu phải có 6 kí tự trở lên", Toast.LENGTH_SHORT).show();
                binding.edtPassword.requestFocus();
                return;
            }
        }
        viewModel.registerClick(email, pass);
    }


}
