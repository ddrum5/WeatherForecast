package ddrum.weatherforecast.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.LocationListener;

import ddrum.weatherforecast.models.Coord;

public abstract class BaseActivity<VM extends BaseViewModel, B extends ViewDataBinding> extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    protected Boolean isAccessLocation = false;

    protected abstract int getLayout();

    protected abstract VM getViewModel();

    protected abstract void initView(@Nullable Bundle savedInstanceState);

    protected void initObserve() {
    }

    protected B binding;
    protected VM viewModel;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayout());
        viewModel = (VM) new ViewModelProvider(this).get(getViewModel().getClass());
        if(!isAccessLocation){
            getLocationService();
        }
        initView(savedInstanceState);
        initObserve();

    }

    protected void getLocationService() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 100, this::onLocationChanged);
            isAccessLocation = true;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Double lat = location.getLatitude();
        Double lon = location.getLongitude();
        viewModel.currentLocation.setValue(new Coord(lat,lon));
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isAccessLocation) {
            getLocationService();
        }
        return super.onTouchEvent(event);
    }

}
