package ddrum.weatherforecast.models;

public class Coord {
    String cityId;
    Double lat;
    Double lon;
    String userId; //thêm vào


    public Coord(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Coord(String userId, String cityId, Double lat, Double lon) {
        this.cityId = cityId;
        this.lat = lat;
        this.lon = lon;
        this.userId = userId;
    }


    public Coord() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
