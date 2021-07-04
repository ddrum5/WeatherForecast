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
import ddrum.weatherforecast.models.FvLocation;
import ddrum.weatherforecast.models.OneCallWeather;
import ddrum.weatherforecast.models.SearchHistory;
import ddrum.weatherforecast.network.ApiService;
import ddrum.weatherforecast.network.RetrofitInstance;
import ddrum.weatherforecast.roomdatabases.DatabaseInstance;
import ddrum.weatherforecast.roomdatabases.FvLocationsDAO;
import ddrum.weatherforecast.roomdatabases.SearchHistoryDAO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_APPEND;

public class MainViewModel extends BaseViewModel {

    public MutableLiveData<CurrentWeather> defaultWeather = new MutableLiveData<>();
    public MutableLiveData<CurrentWeather> simpleWeather = new MutableLiveData<>();
    public MutableLiveData<List<CurrentWeather>> simpleWeatherList = new MutableLiveData<>();
    public MutableLiveData<OneCallWeather> oneCallWeather = new MutableLiveData<>();
    public MutableLiveData<List<FvLocation>> fvLocationList = new MutableLiveData<>();
    public MutableLiveData<List<SearchHistory>> searchHistoryList = new MutableLiveData<>();
    private ApiService apiService = RetrofitInstance.getInstance().create(ApiService.class);
    private FvLocationsDAO fvLocationsDAO;
    private SearchHistoryDAO searchHistoryDAO;


    public void initDAO(Context context) {
        fvLocationsDAO = DatabaseInstance.getInstance(context).fvLocationsDAO();
        searchHistoryDAO = DatabaseInstance.getInstance(context).searchHistoryDAO();
        updateLocationListLocal();
        updateSearchHistoryListFromLocal();
    }

    public void updateLocationListLocal() {
        if (fvLocationsDAO.getFvLocations().size() > 0) {
            fvLocationList.setValue(fvLocationsDAO.getFvLocations());
        } else {
            fvLocationList.setValue(null);
        }
    }

    public void updateSearchHistoryListFromLocal() {
        searchHistoryList.setValue(searchHistoryDAO.getSearchHistoryList());
    }

    private void addSearchHistory(String text) {
        SearchHistory history = new SearchHistory(getUserId(), text);
        if (isLogged.getValue()) {
            getRefSearch().document(text)
                    .set(history);
        } else {
            searchHistoryDAO.insert(history);
            updateSearchHistoryListFromLocal();
        }
    }

    public void addLocation(FvLocation fvLocation) {
        if (isLogged.getValue()) {
            getRefLocations()
                    .document(System.currentTimeMillis() + "")
                    .set(fvLocation);

        } else {
            fvLocationsDAO.insert(fvLocation);
            updateLocationListLocal();
        }
    }

    public void removeLocation(String cityId) {
        if (isLogged.getValue()) {
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
        } else {
            fvLocationsDAO.removeFvLocationById(cityId);
            updateLocationListLocal();
        }
    }

    public synchronized void setSearchHistoryList() {
        getRefSearch().whereEqualTo(Constant.USER_ID, getUserId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            List<SearchHistory> list = value.toObjects(SearchHistory.class);
                            searchHistoryList.setValue(list);
                        } else {
                            searchHistoryList.setValue(null);
                        }
                    }
                });

    }

    public void setFvLocationList() {
        getRefLocations()
                .whereEqualTo(Constant.USER_ID, getUserId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            List<FvLocation> list = value.toObjects(FvLocation.class);
                            if (list.size() > 0) {
                                fvLocationList.setValue(list);
                            }else {
                                fvLocationList.setValue(null);
                            }
                        }
                    }
                });
    }

    public void setSimpleWeatherList(List<FvLocation> fvLocations) {
        List<CurrentWeather> list = new ArrayList<>();
        for (FvLocation fv : fvLocations) {
            apiService.getWeatherByCityId(fv.getCityId()).enqueue(new Callback<CurrentWeather>() {
                @Override
                public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                    CurrentWeather currentWeather = response.body();
                    if (currentWeather != null) {
                        list.add(currentWeather);
                        simpleWeatherList.setValue(list);
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
                defaultWeather.setValue(currentWeather);
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
                oneCallWeather.setValue(weather);
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
                    setOneCallWeather(weather.getCoord().getLat().toString(), weather.getCoord().getLon().toString());
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
        addSearchHistory(cityName);
        checkCity = false;
        apiService.getWeatherByCityName(cityName).enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                CurrentWeather weather = response.body();
                if (weather != null) {
                    checkCity = true;
                    simpleWeather.setValue(weather);
                    setOneCallWeather(weather.getCoord().getLat().toString(), weather.getCoord().getLon().toString());
                } else checkCity = false;
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                checkCity = false;
            }
        });
    }


}