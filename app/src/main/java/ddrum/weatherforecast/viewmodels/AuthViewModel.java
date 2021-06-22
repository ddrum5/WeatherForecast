package ddrum.weatherforecast.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import org.jetbrains.annotations.NotNull;

import ddrum.weatherforecast.base.BaseViewModel;
import ddrum.weatherforecast.models.UserWeather;

public class AuthViewModel extends BaseViewModel {

    public MutableLiveData<Boolean> existedEmail = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoginSuccessful = new MutableLiveData<>();

    public void registerClick(String email, String pass) {
        auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    boolean check = task.getResult().getSignInMethods().isEmpty();
                    if (!check) {
                        existedEmail.setValue(true);
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
        UserWeather u = new UserWeather();
        u.setUId(user.getValue().getUid());
        u.setEmail(user.getValue().getEmail());
        getRef(u.getUId()).setValue(u)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        isLoginSuccessful.setValue(task.isSuccessful());
                    }
                });
    }
    public void signInWithEmailAndPassword(String email, String pass){
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                initUser();
                isLoginSuccessful.setValue(task.isSuccessful());
            }
        });
    }



}
