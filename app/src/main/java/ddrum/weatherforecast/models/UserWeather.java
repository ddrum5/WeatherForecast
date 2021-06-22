package ddrum.weatherforecast.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserWeather {


    @SerializedName("uId")
    private String uId;
    @SerializedName("email")
    private String email;
    @SerializedName("coord")
    private List<Coord> coord;

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
        public Coord(Double lat, Double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        @SerializedName("lat")
        private Double lat;
        @SerializedName("lon")
        private Double lon;

        public Coord() {

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
