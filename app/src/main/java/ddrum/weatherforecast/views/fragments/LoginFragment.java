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
import ddrum.weatherforecast.databinding.FragmentLoginBinding;
import ddrum.weatherforecast.viewmodels.AuthViewModel;

public class LoginFragment extends BaseFragment<AuthViewModel, FragmentLoginBinding> {


    @Override
    protected int getLayout() {
        return R.layout.fragment_login;
    }

    @Override
    protected AuthViewModel getViewModel() {
        return new AuthViewModel();
    }

    @Override
    protected void initView(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        onClickListener(binding.btnLogin, binding.btnRegister);
    }

    @Override
    protected void initObserve() {
        viewModel.isLoginSuccessful.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoginSuccessful) {
                if (isLoginSuccessful != null)
                    if (isLoginSuccessful) {
                        popBackStack();
                        viewModel.isLoginSuccessful.setValue(null);
                        shortSnackBar("Đăng nhập thành công");
                    } else {
                        shortSnackBar("Sai tài khoản mặc mật khẩu");
                    }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                loginWithEmailPassword();
                break;
            case R.id.btn_register:
                navigateTo(R.id.action_loginFragment2_to_registerFragment2);
                break;
        }
    }

    private void loginWithEmailPassword() {
        String user = binding.edtEmail.getText().toString().isEmpty() ? "ddrum@gmail.com" : binding.edtEmail.getText().toString().trim();
        String pass = binding.edtPassword.getText().toString().isEmpty() ? "111111" : binding.edtPassword.getText().toString();
        viewModel.signInWithEmailAndPassword(user, pass);


    }
}
