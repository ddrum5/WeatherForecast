package ddrum.weatherforecast.base;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ddrum.weatherforecast.models.Constant;
import ddrum.weatherforecast.models.UserWeather;

public class BaseViewModel extends ViewModel {

    protected FirebaseAuth auth= FirebaseAuth.getInstance();
    public MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLogged = new MutableLiveData<>();
    public MutableLiveData<UserWeather.Coord> currentLocation = new MutableLiveData<>();


    public void initUser() {
        auth= FirebaseAuth.getInstance();
        updateUserState();
        isLogged.setValue(user!=null);

    }
    public void updateUserState(){
        user.setValue(auth.getCurrentUser());
    }

    public void logout() {
        auth.signOut();
    }

    public DatabaseReference getRef() {
        return FirebaseDatabase.getInstance().getReference(user.getValue().getUid());
    }




}
