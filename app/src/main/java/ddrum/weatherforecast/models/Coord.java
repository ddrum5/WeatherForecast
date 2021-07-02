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

    public Coord(String userId, String cityId) {
        this.cityId = cityId;
        this.userId = userId;
    }


    public Coord() {
    }



    public Double getLat() {
        return lat;
    }


    public Double getLon() {
        return lon;
    }


    public String getCityId() {
        return cityId;
    }

}
