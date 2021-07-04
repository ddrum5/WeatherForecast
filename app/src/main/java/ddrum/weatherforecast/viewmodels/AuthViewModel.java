package ddrum.weatherforecast.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import org.jetbrains.annotations.NotNull;

import ddrum.weatherforecast.base.BaseViewModel;
import ddrum.weatherforecast.models.User;
import ddrum.weatherforecast.ulti.Util;

public class AuthViewModel extends BaseViewModel {

    public MutableLiveData<Boolean> isLoginSuccessful = new MutableLiveData<>();
    public MutableLiveData<String> message = new MutableLiveData<>(null);


    public void registerClick(String email, String pass, String passConfirm) {
        if (!Util.isValidEmail(email)) {
            message.setValue("Email không đúng định dạng");
            return;
        } else {
            if (!pass.equals(passConfirm)) {
                message.setValue("Xác nhận mật khẩu không đúng");
                return;
            } else {
                if (pass.length() < 6) {
                    message.setValue("Mật khẩu phải có 6 kí tự trở lên");
                    return;
                } else {
                    checkEmailExited(email,pass);
                }
            }
        }
    }

    public void checkEmailExited(String email, String pass) {
        auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    boolean check = task.getResult().getSignInMethods().isEmpty();
                    if (!check) {
                        message.setValue("Email đã tồn tại");
                        return;
                    } else {
                        createWithEmailPass(email, pass);
                    }
                });
    }
    public void createWithEmailPass(String email, String pass) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    saveToDBAndLogin();
                } else {
                    isLoginSuccessful.setValue(false);
                }
            }
        });
    }

    private void saveToDBAndLogin() {
        initUser();
        User u = new User();
        u.setUserId(user.getValue().getUid());
        u.setEmail(user.getValue().getEmail());
        getRefUser().document(System.currentTimeMillis() + "")
                .set(u)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                            isLoginSuccessful.setValue(task.isSuccessful());
                    }
                });
    }

    public void signInWithEmailAndPassword(String email, String pass) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                initUser();
                isLoginSuccessful.setValue(task.isSuccessful());
            }
        });
    }


}
