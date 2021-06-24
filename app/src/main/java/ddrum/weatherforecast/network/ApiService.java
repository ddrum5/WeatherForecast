package ddrum.weatherforecast.network;

import ddrum.weatherforecast.models.CurrentWeather;
import ddrum.weatherforecast.models.OneCallWeather;
import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    //https://api.openweathermap.org/data/2.5/weather?units=metric&lang=vi&appid=4a51b76ea653415bb41003f2381a167a&lat=20.7284&lon=106.7438

    //https://api.openweathermap.org/data/2.5/onecall?units=metric&exclude=minutely&lang=vi&appid=4a51b76ea653415bb41003f2381a167a&lat=20.8561&lon=106.6822
    //api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}


    String API_KEY = "4a51b76ea653415bb41003f2381a167a";

    @GET("/data/2.5/weather?units=metric&lang=vi&appid=" + API_KEY)
    Call<CurrentWeather> getWeatherByCityName(@Query(value = "q", encoded = false) String cityName);

    @GET("/data/2.5/weather?units=metric&lang=vi&appid=" + API_KEY)
    Call<CurrentWeather> getWeatherByCoord(@Query(value = "lat") String lat, @Query(value = "lon") String lon);

    @GET("/data/2.5/onecall?units=metric&exclude=minutely&lang=vi&appid=" + API_KEY)
    Call<OneCallWeather> getOneCallWeather(@Query(value = "lat") String lat, @Query(value = "lon") String lon);

}
