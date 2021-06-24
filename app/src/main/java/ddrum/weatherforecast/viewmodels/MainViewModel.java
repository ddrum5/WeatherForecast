package ddrum.weatherforecast.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import ddrum.weatherforecast.base.BaseViewModel;
import ddrum.weatherforecast.models.CurrentWeather;
import ddrum.weatherforecast.models.OneCallWeather;
import ddrum.weatherforecast.models.UserWeather;
import ddrum.weatherforecast.network.ApiService;
import ddrum.weatherforecast.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MainViewModel extends BaseViewModel {


    public MutableLiveData<HashMap<String, Object>> userWeather = new MutableLiveData<>();
    public MutableLiveData<CurrentWeather> defaultWeather = new MutableLiveData<>(); //vi tr hien tai
    public MutableLiveData<List<CurrentWeather>> weatherList = new MutableLiveData<>();
    public MutableLiveData<OneCallWeather> oneCallWeather = new MutableLiveData<>();
    ApiService apiService = RetrofitInstance.getInstance().create(ApiService.class);


    public void init() {
    }

    //==============================================================================================
    public void setDefaultWeather(String lat, String lon) {
        apiService.getWeatherByCoord(lat, lon).enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                CurrentWeather currentWeather = response.body();
                if (currentWeather != null) {
                    defaultWeather.setValue(currentWeather);
                } else {
                    defaultWeather.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                defaultWeather.setValue(null);
                Log.e("hay", "onFailure: ", t.getCause());
            }
        });
    }

    //==============================================================================================
    public void setUserWeather() {
        getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                HashMap<String, Object> map = (HashMap<String, Object>) snapshot.getValue();
                userWeather.setValue(map);
//                UserWeather userWeather = snapshot.getValue(UserWeather.class);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.e(TAG, "onCancelled: " + error.getMessage());
            }
        });
    }

    //==============================================================================================
    public void addWeather(UserWeather.Coord coord) {

    }

    //==============================================================================================
    public MutableLiveData<CurrentWeather> currentWeather = new MutableLiveData<>();
//    public CurrentWeather getCurrentWeather(String cityName) {
//        apiService.getWeatherByCityName(cityName).enqueue(new Callback<CurrentWeather>() {
//            @Override
//            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
//                CurrentWeather weather = response.body();
//                if (weather != null) {
//                    currentWeather = weather;
//                    return;
//                }
//            }
//            @Override
//            public void onFailure(Call<CurrentWeather> call, Throwable t) {
//                currentWeather = null;
//            }
//        });
//        return currentWeather;
//    }
    //==============================================================================================

    public MutableLiveData<Boolean> checkCity= new MutableLiveData<>();
    public void getCheckCityName(String cityName) {
        apiService.getWeatherByCityName(cityName).enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                CurrentWeather weather = response.body();
                if (weather != null) {
                    checkCity.setValue(true);
                    currentWeather.setValue(weather);
                }else {
                    checkCity.setValue(false);
                    currentWeather.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                checkCity.setValue(false);
                currentWeather.setValue(null);
            }
        });

    }

    //==============================================================================================
    public void setOneCallWeather(String lat, String lon) {
        apiService.getOneCallWeather(lat, lon).enqueue(new Callback<OneCallWeather>() {
            @Override
            public void onResponse(Call<OneCallWeather> call, Response<OneCallWeather> response) {
                OneCallWeather weather = response.body();
                if (weather != null) {
                    oneCallWeather.setValue(weather);
                } else {
                    oneCallWeather.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<OneCallWeather> call, Throwable t) {
                Log.e("hay", "onFailure: ", t.getCause());
            }
        });
    }
    //==============================================================================================

    //==============================================================================================
    public void updateWeatherList(List<CurrentWeather> list) {
        weatherList.setValue(list);
    }

    //==============================================================================================

}