package ddrum.weatherforecast.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    
    private String userId;
    private String email;
    private List<Coord> fvLocations;
    
    public User(String userId, String email, List<Coord> fvLocations) {
        this.userId = userId;
        this.email = email;
        this.fvLocations = fvLocations;
    }

    public User() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Coord> getFvLocations() {
        return fvLocations;
    }

    public void setFvLocations(List<Coord> fvLocations) {
        this.fvLocations = fvLocations;
    }

}
