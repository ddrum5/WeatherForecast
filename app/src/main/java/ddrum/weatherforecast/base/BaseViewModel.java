package ddrum.weatherforecast.base;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

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
    public DatabaseReference getRef(){
        return FirebaseDatabase.getInstance().getReference(user.getValue().getUid());
    }
    long maxRow;
    public DatabaseReference getRefCoord() {

         DatabaseReference reference = FirebaseDatabase.getInstance()
                 .getReference(user.getValue().getUid()).child(Constant.coord);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                maxRow = snapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return reference.child(String.valueOf(maxRow+1));
    }




}
