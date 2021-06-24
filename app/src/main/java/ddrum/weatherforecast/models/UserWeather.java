package ddrum.weatherforecast.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserWeather {


    private String uId;
    private String email;
    private Coord currentLocation;
    private List<Coord> coord;

    public UserWeather() {
    }
    public UserWeather(Coord currentLocation) {
        this.currentLocation = currentLocation;
    }


    public Coord getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Coord currentLocation) {
        this.currentLocation = currentLocation;
    }


    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Coord> getCoord() {
        return coord;
    }

    public void setCoord(List<Coord> coord) {
        this.coord = coord;
    }

    public static class Coord {

        private Double lat;
        private Double lon;

        public Coord() {
        }
        public Coord(Double lat, Double lon ) {
            this.lat = lat;
            this.lon = lon;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }
    }



}
