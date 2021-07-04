package ddrum.weatherforecast.base;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ddrum.weatherforecast.models.Constant;
import ddrum.weatherforecast.models.Coord;

public class BaseViewModel extends ViewModel {


    protected FirebaseAuth auth= FirebaseAuth.getInstance();
    public MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLogged = new MutableLiveData<>();
    public MutableLiveData<Coord> currentLocation = new MutableLiveData<>();


    public synchronized void initUser() {
        auth= FirebaseAuth.getInstance();
        updateUserState();
    }
    public String getUserId(){
        if (user.getValue()!=null) {
            return user.getValue().getUid();
        }
        return "";

    }
    public void updateUserState(){
        user.setValue(auth.getCurrentUser());
        isLogged.setValue(user.getValue() !=null);
    }

    public void logout() {
        auth.signOut();
    }

    public CollectionReference getRefUser(){
        return FirebaseFirestore.getInstance().collection(Constant.USER);
    }
    public CollectionReference getRefLocations(){
        return FirebaseFirestore.getInstance().collection(Constant.LOCATIONS);
    }
    public CollectionReference getRefSearch(){
        return FirebaseFirestore.getInstance().collection(Constant.SEARCH);
    }






}
