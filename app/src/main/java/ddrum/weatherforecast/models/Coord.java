package ddrum.weatherforecast.models;

public class Coord {

    Double lat;
    Double lon;

    public Coord(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }


    public Double getLat() {
        return lat;
    }


    public Double getLon() {
        return lon;
    }



}
