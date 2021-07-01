package ddrum.weatherforecast.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseFragment;
import ddrum.weatherforecast.databinding.FragmentLoginBinding;
import ddrum.weatherforecast.viewmodels.AuthViewModel;
import ddrum.weatherforecast.viewmodels.MainViewModel;

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
        event();
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
                        Toast.makeText(getActivity(), "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    private void event() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithEmailPassword();
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(R.id.action_loginFragment2_to_registerFragment2);
            }
        });


    }

    private void loginWithEmailPassword() {
        String user = binding.edtEmail.getText().toString().isEmpty() ? "ddrum@gmail.com" : binding.edtEmail.getText().toString().trim();
        String pass = binding.edtPassword.getText().toString().isEmpty() ? "111111" : binding.edtPassword.getText().toString();
        viewModel.signInWithEmailAndPassword(user, pass);


    }


}
