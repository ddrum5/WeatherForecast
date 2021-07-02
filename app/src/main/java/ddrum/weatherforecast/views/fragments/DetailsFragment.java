package ddrum.weatherforecast.views.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseFragment;
import ddrum.weatherforecast.databinding.FragmentDetailsBinding;
import ddrum.weatherforecast.models.Constant;
import ddrum.weatherforecast.models.Coord;
import ddrum.weatherforecast.models.CurrentWeather;
import ddrum.weatherforecast.models.OneCallWeather;
import ddrum.weatherforecast.viewmodels.MainViewModel;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;


public class DetailsFragment extends BaseFragment<MainViewModel, FragmentDetailsBinding> {

    Double lat = 0.0;
    Double lon = 0.0;
    String cityId = "";
    Coord coord;

    @Override
    protected int getLayout() {
        return R.layout.fragment_details;
    }

    @Override
    protected MainViewModel getViewModel() {
        return new MainViewModel();
    }


    @Override
    protected void initView(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding();
        event();
    }

    private void event() {
        binding.btnAddToList.setOnClickListener(v -> {
            if (viewModel.isLogged.getValue()) {
                addLocationToFB();
            } else {
                addLocationToLocal();
            }
        });

    }

    private void addLocationToFB() {
        viewModel.getRefLocations()
                .document(System.currentTimeMillis() + "")
                .set(coord)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isComplete()) {
                            shortSnackBar("Thêm địa điểm thành công");
                        } else {
                            shortSnackBar("Có lỗi" + task.getException());
                        }
                    }
                });


    }

    private void addLocationToLocal() {
        viewModel.setFvLocationListLocal(getContext(),cityId);
    }

    private void binding() {
        viewModel.detailWeather.observe(this, new Observer<CurrentWeather>() {
            @Override
            public void onChanged(CurrentWeather currentWeather) {
                if (currentWeather != null) {
                    lat = currentWeather.getCoord().getLat();
                    lon = currentWeather.getCoord().getLon();
                    cityId = currentWeather.getId().toString();
                    coord = new Coord(viewModel.getUserId(), cityId, lat, lon);
                    String cityName = currentWeather.getName() + ", " + currentWeather.getSys().getCountry();
                    viewModel.setOneCallWeather(lat.toString(), lon.toString());
                    viewModel.oneCallWeather.observe(getViewLifecycleOwner(), new Observer<OneCallWeather>() {
                        @Override
                        public void onChanged(OneCallWeather oneCallWeather) {
                            String description = oneCallWeather.getCurrent().getWeather().get(0).getDescription();
                            String temp = Math.round(oneCallWeather.getCurrent().getTemp()) + getString(R.string.tempUnit) + "C";
                            String feelsLike = "cảm giác như " + Math.round(oneCallWeather.getCurrent().getFeelsLike());
                            String iconUrl = "http://openweathermap.org/img/wn/" + oneCallWeather.getCurrent().getWeather().get(0).getIcon() + "@2x.png";

                            binding.currentTvCityName.setText(cityName);
                            binding.currentTvDescription.setText(description);
                            binding.currentTvTemp.setText(temp);
                            binding.currentTvFeelsLike.setText(feelsLike);
                            Glide.with(getActivity()).load(iconUrl).into(binding.currentIconWeather);
                        }
                    });
                }
            }
        });

        viewModel.fvLocationList.observe(this, new Observer<List<Coord>>() {
            @Override
            public void onChanged(List<Coord> coords) {
                if (coords != null) {
                    if (containsCityId(coords, cityId)) {
                        binding.btnAddToList.setVisibility(View.GONE);
                    } else {
                        binding.btnAddToList.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        viewModel.fvLocationListLocal.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> list) {
                if (list != null) {
                    if (containsCityIdString(list, cityId)) {
                        binding.btnAddToList.setVisibility(View.GONE);
                    } else {
                        binding.btnAddToList.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }

    public boolean containsCityId(final List<Coord> list, final String cityId) {
        return list.stream().anyMatch(o -> o.getCityId().equals(cityId));
    }

    public boolean containsCityIdString(final List<String> list, final String cityId) {
        return list.stream().anyMatch(o -> o.equals(cityId));
    }




}


