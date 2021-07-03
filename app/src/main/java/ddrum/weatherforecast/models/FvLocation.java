package ddrum.weatherforecast.models;

import androidx.annotation.NonNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "fvLocations")
public class FvLocation {
    @PrimaryKey

    @NonNull private String cityId;
    private String userId;

    public FvLocation(String userId,String cityId) {
        this.cityId = cityId;
        this.userId = userId;
    }

    public FvLocation() {
    }

    public void setCityId(@NonNull String cityId) {
        this.cityId = cityId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getCityId() {
        return cityId;
    }

    public String getUserId() {
        return userId;
    }
}
