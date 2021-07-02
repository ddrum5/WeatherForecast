package ddrum.weatherforecast.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ddrum.weatherforecast.base.BaseViewModel;
import ddrum.weatherforecast.models.Constant;
import ddrum.weatherforecast.models.Coord;
import ddrum.weatherforecast.models.CurrentWeather;
import ddrum.weatherforecast.models.OneCallWeather;
import ddrum.weatherforecast.network.ApiService;
import ddrum.weatherforecast.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_APPEND;

public class MainViewModel extends BaseViewModel {

    public MutableLiveData<CurrentWeather> defaultWeather = new MutableLiveData<>();        //vi tr hien tai
    public MutableLiveData<CurrentWeather> simpleWeather = new MutableLiveData<>();            //Thời tiết đơn giản
    public MutableLiveData<List<CurrentWeather>> simpleWeatherList = new MutableLiveData<>(); //list đơn giản
    public MutableLiveData<OneCallWeather> oneCallWeather = new MutableLiveData<>();        // thời tiết chi tiết
    public MutableLiveData<List<Coord>> fvLocationList = new MutableLiveData<>();          // list dia diem yeu thich tren fb
    public MutableLiveData<List<String>> fvLocationListLocal = new MutableLiveData<>();   // list dia diem yeu thich tren may
    ApiService apiService = RetrofitInstance.getInstance().create(ApiService.class);      //api


    public void init() {
    }

    public void initFvLocationLocal(Context context) {
        fvLocationListLocal.setValue(getListFromLocal(context));
    }

    public void setFvLocationListLocal(Context context, String cityId) {
        try {
            FileOutputStream out = context.openFileOutput(Constant.LOCAL_LOCATIONS_FILENAME, MODE_APPEND);
            out.write((cityId + "\n").getBytes());
            out.close();
        } catch (Exception e) {
        }
        fvLocationListLocal.setValue(getListFromLocal(context));
    }

    public void setFvLocationList() {
        getRefLocations()
                .whereEqualTo(Constant.USER_ID, getUserId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            List<Coord> list = value.toObjects(Coord.class);
                            fvLocationList.setValue(list);
                        } else {
                            fvLocationList.setValue(null);
                        }
                    }
                });
    }

    public void setSimpleWeatherList(List<Coord> coords) {
        List<CurrentWeather> list = new ArrayList<>();
        for (Coord c : coords) {
            apiService.getWeatherByCityId(c.getCityId()).enqueue(new Callback<CurrentWeather>() {
                @Override
                public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                    CurrentWeather currentWeather = response.body();
                    if (currentWeather != null) {
                        list.add(currentWeather);
                        simpleWeatherList.setValue(list);
                    } else {
                        simpleWeatherList.setValue(null);
                    }
                }

                @Override
                public void onFailure(Call<CurrentWeather> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t.getCause());
                }
            });
        }
    }

    public void setSimpleWeatherListByFromLocal(List<String> cityIdList) {
        List<CurrentWeather> list = new ArrayList<>();
        for (String cityId : cityIdList) {
            apiService.getWeatherByCityId(cityId).enqueue(new Callback<CurrentWeather>() {
                @Override
                public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                    CurrentWeather currentWeather = response.body();
                    if (currentWeather != null) {
                        list.add(currentWeather);
                        simpleWeatherList.setValue(list);
                    } else {
                        simpleWeatherList.setValue(null);
                    }
                }

                @Override
                public void onFailure(Call<CurrentWeather> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t.getCause());
                }
            });
        }
    }

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

    public void setWeatherDetail(String cityId) {
        apiService.getWeatherByCityId(cityId).enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
               CurrentWeather weather = response.body();
                if (weather != null) {
                    simpleWeather.setValue(weather);
                    setOneCallWeather(weather.getCoord().getLat().toString(),weather.getCoord().getLon().toString());
                }
            }
            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                simpleWeather.setValue(null);
            }
        });

    }

    public boolean checkCity;
    public void checkCity(String cityName) {
        checkCity = false;
        apiService.getWeatherByCityName(cityName).enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                CurrentWeather weather = response.body();
                if (weather != null) {
                    checkCity = true;
                    simpleWeather.setValue(weather);
                    setOneCallWeather(weather.getCoord().getLat().toString(),weather.getCoord().getLon().toString());
                } else checkCity = false;
            }
            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                checkCity = false;
            }
        });
    }

    public void removeLocation(String cityId) {
        getRefLocations()
                .whereEqualTo(Constant.CITY_ID, cityId)
                .whereEqualTo(Constant.USER_ID, getUserId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                getRefLocations().document(document.getId()).delete();
                            }
                        }
                    }
                });
    }


}