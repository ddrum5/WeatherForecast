package ddrum.weatherforecast.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OneCallWeather {


    @SerializedName("lat")
    private Double lat;
    @SerializedName("lon")
    private Double lon;
    @SerializedName("timezone")
    private String timezone;
    @SerializedName("timezone_offset")
    private Integer timezoneOffset;
    @SerializedName("current")
    private Current current;
    @SerializedName("hourly")
    private List<Hourly> hourly;
    @SerializedName("daily")
    private List<Daily> daily;

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

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(Integer timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public List<Hourly> getHourly() {
        return hourly;
    }

    public void setHourly(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

    public static class Current {
        @SerializedName("dt")
        private Integer dt;
        @SerializedName("sunrise")
        private Integer sunrise;
        @SerializedName("sunset")
        private Integer sunset;
        @SerializedName("temp")
        private Double temp;
        @SerializedName("feels_like")
        private Double feelsLike;
        @SerializedName("pressure")
        private Integer pressure;
        @SerializedName("humidity")
        private Integer humidity;
        @SerializedName("dew_point")
        private Double dewPoint;
        @SerializedName("uvi")
        private Double uvi;
        @SerializedName("clouds")
        private Integer clouds;
        @SerializedName("visibility")
        private Integer visibility;
        @SerializedName("wind_speed")
        private Double windSpeed;
        @SerializedName("wind_deg")
        private Integer windDeg;
        @SerializedName("weather")
        private List<Weather> weather;

        public Integer getDt() {
            return dt;
        }

        public void setDt(Integer dt) {
            this.dt = dt;
        }

        public Integer getSunrise() {
            return sunrise;
        }

        public void setSunrise(Integer sunrise) {
            this.sunrise = sunrise;
        }

        public Integer getSunset() {
            return sunset;
        }

        public void setSunset(Integer sunset) {
            this.sunset = sunset;
        }

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public Double getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(Double feelsLike) {
            this.feelsLike = feelsLike;
        }

        public Integer getPressure() {
            return pressure;
        }

        public void setPressure(Integer pressure) {
            this.pressure = pressure;
        }

        public Integer getHumidity() {
            return humidity;
        }

        public void setHumidity(Integer humidity) {
            this.humidity = humidity;
        }

        public Double getDewPoint() {
            return dewPoint;
        }

        public void setDewPoint(Double dewPoint) {
            this.dewPoint = dewPoint;
        }

        public Double getUvi() {
            return uvi;
        }

        public void setUvi(Double uvi) {
            this.uvi = uvi;
        }

        public Integer getClouds() {
            return clouds;
        }

        public void setClouds(Integer clouds) {
            this.clouds = clouds;
        }

        public Integer getVisibility() {
            return visibility;
        }

        public void setVisibility(Integer visibility) {
            this.visibility = visibility;
        }

        public Double getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(Double windSpeed) {
            this.windSpeed = windSpeed;
        }

        public Integer getWindDeg() {
            return windDeg;
        }

        public void setWindDeg(Integer windDeg) {
            this.windDeg = windDeg;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        public static class Weather {
            @SerializedName("id")
            private Integer id;
            @SerializedName("main")
            private String main;
            @SerializedName("description")
            private String description;
            @SerializedName("icon")
            private String icon;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
    }

    public static class Hourly {
        @SerializedName("dt")
        private Integer dt;
        @SerializedName("temp")
        private Double temp;
        @SerializedName("feels_like")
        private Double feelsLike;
        @SerializedName("pressure")
        private Integer pressure;
        @SerializedName("humidity")
        private Integer humidity;
        @SerializedName("dew_point")
        private Double dewPoint;
        @SerializedName("uvi")
        private Double uvi;
        @SerializedName("clouds")
        private Integer clouds;
        @SerializedName("visibility")
        private Integer visibility;
        @SerializedName("wind_speed")
        private Double windSpeed;
        @SerializedName("wind_deg")
        private Integer windDeg;
        @SerializedName("wind_gust")
        private Double windGust;
        @SerializedName("weather")
        private List<Weather> weather;
        @SerializedName("pop")
        private Double pop;
        @SerializedName("rain")
        private Rain rain;

        public Integer getDt() {
            return dt;
        }

        public void setDt(Integer dt) {
            this.dt = dt;
        }

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public Double getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(Double feelsLike) {
            this.feelsLike = feelsLike;
        }

        public Integer getPressure() {
            return pressure;
        }

        public void setPressure(Integer pressure) {
            this.pressure = pressure;
        }

        public Integer getHumidity() {
            return humidity;
        }

        public void setHumidity(Integer humidity) {
            this.humidity = humidity;
        }

        public Double getDewPoint() {
            return dewPoint;
        }

        public void setDewPoint(Double dewPoint) {
            this.dewPoint = dewPoint;
        }

        public Double getUvi() {
            return uvi;
        }

        public void setUvi(Double uvi) {
            this.uvi = uvi;
        }

        public Integer getClouds() {
            return clouds;
        }

        public void setClouds(Integer clouds) {
            this.clouds = clouds;
        }

        public Integer getVisibility() {
            return visibility;
        }

        public void setVisibility(Integer visibility) {
            this.visibility = visibility;
        }

        public Double getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(Double windSpeed) {
            this.windSpeed = windSpeed;
        }

        public Integer getWindDeg() {
            return windDeg;
        }

        public void setWindDeg(Integer windDeg) {
            this.windDeg = windDeg;
        }

        public Double getWindGust() {
            return windGust;
        }

        public void setWindGust(Double windGust) {
            this.windGust = windGust;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        public Double getPop() {
            return pop;
        }

        public void setPop(Double pop) {
            this.pop = pop;
        }

        public Rain getRain() {
            return rain;
        }

        public void setRain(Rain rain) {
            this.rain = rain;
        }

        public static class Rain {
            @SerializedName("1h")
            private Double $1h;

            public Double get$1h() {
                return $1h;
            }

            public void set$1h(Double $1h) {
                this.$1h = $1h;
            }
        }

        public static class Weather {
            @SerializedName("id")
            private Integer id;
            @SerializedName("main")
            private String main;
            @SerializedName("description")
            private String description;
            @SerializedName("icon")
            private String icon;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
    }

    public static class Daily {
        @SerializedName("dt")
        private Integer dt;
        @SerializedName("sunrise")
        private Integer sunrise;
        @SerializedName("sunset")
        private Integer sunset;
        @SerializedName("moonrise")
        private Integer moonrise;
        @SerializedName("moonset")
        private Integer moonset;
        @SerializedName("moon_phase")
        private Double moonPhase;
        @SerializedName("temp")
        private Temp temp;
        @SerializedName("feels_like")
        private FeelsLike feelsLike;
        @SerializedName("pressure")
        private Integer pressure;
        @SerializedName("humidity")
        private Integer humidity;
        @SerializedName("dew_point")
        private Double dewPoint;
        @SerializedName("wind_speed")
        private Double windSpeed;
        @SerializedName("wind_deg")
        private Integer windDeg;
        @SerializedName("wind_gust")
        private Double windGust;
        @SerializedName("weather")
        private List<Weather> weather;
        @SerializedName("clouds")
        private Integer clouds;
        @SerializedName("pop")
        private Double pop;
        @SerializedName("rain")
        private Double rain;
        @SerializedName("uvi")
        private Double uvi;

        public Integer getDt() {
            return dt;
        }

        public void setDt(Integer dt) {
            this.dt = dt;
        }

        public Integer getSunrise() {
            return sunrise;
        }

        public void setSunrise(Integer sunrise) {
            this.sunrise = sunrise;
        }

        public Integer getSunset() {
            return sunset;
        }

        public void setSunset(Integer sunset) {
            this.sunset = sunset;
        }

        public Integer getMoonrise() {
            return moonrise;
        }

        public void setMoonrise(Integer moonrise) {
            this.moonrise = moonrise;
        }

        public Integer getMoonset() {
            return moonset;
        }

        public void setMoonset(Integer moonset) {
            this.moonset = moonset;
        }

        public Double getMoonPhase() {
            return moonPhase;
        }

        public void setMoonPhase(Double moonPhase) {
            this.moonPhase = moonPhase;
        }

        public Temp getTemp() {
            return temp;
        }

        public void setTemp(Temp temp) {
            this.temp = temp;
        }

        public FeelsLike getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(FeelsLike feelsLike) {
            this.feelsLike = feelsLike;
        }

        public Integer getPressure() {
            return pressure;
        }

        public void setPressure(Integer pressure) {
            this.pressure = pressure;
        }

        public Integer getHumidity() {
            return humidity;
        }

        public void setHumidity(Integer humidity) {
            this.humidity = humidity;
        }

        public Double getDewPoint() {
            return dewPoint;
        }

        public void setDewPoint(Double dewPoint) {
            this.dewPoint = dewPoint;
        }

        public Double getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(Double windSpeed) {
            this.windSpeed = windSpeed;
        }

        public Integer getWindDeg() {
            return windDeg;
        }

        public void setWindDeg(Integer windDeg) {
            this.windDeg = windDeg;
        }

        public Double getWindGust() {
            return windGust;
        }

        public void setWindGust(Double windGust) {
            this.windGust = windGust;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        public Integer getClouds() {
            return clouds;
        }

        public void setClouds(Integer clouds) {
            this.clouds = clouds;
        }

        public Double getPop() {
            return pop;
        }

        public void setPop(Double pop) {
            this.pop = pop;
        }

        public Double getRain() {
            return rain;
        }

        public void setRain(Double rain) {
            this.rain = rain;
        }

        public Double getUvi() {
            return uvi;
        }

        public void setUvi(Double uvi) {
            this.uvi = uvi;
        }

        public static class Temp {
            @SerializedName("day")
            private Double day;
            @SerializedName("min")
            private Double min;
            @SerializedName("max")
            private Double max;
            @SerializedName("night")
            private Double night;
            @SerializedName("eve")
            private Double eve;
            @SerializedName("morn")
            private Double morn;

            public Double getDay() {
                return day;
            }

            public void setDay(Double day) {
                this.day = day;
            }

            public Double getMin() {
                return min;
            }

            public void setMin(Double min) {
                this.min = min;
            }

            public Double getMax() {
                return max;
            }

            public void setMax(Double max) {
                this.max = max;
            }

            public Double getNight() {
                return night;
            }

            public void setNight(Double night) {
                this.night = night;
            }

            public Double getEve() {
                return eve;
            }

            public void setEve(Double eve) {
                this.eve = eve;
            }

            public Double getMorn() {
                return morn;
            }

            public void setMorn(Double morn) {
                this.morn = morn;
            }
        }

        public static class FeelsLike {
            @SerializedName("day")
            private Double day;
            @SerializedName("night")
            private Double night;
            @SerializedName("eve")
            private Double eve;
            @SerializedName("morn")
            private Double morn;

            public Double getDay() {
                return day;
            }

            public void setDay(Double day) {
                this.day = day;
            }

            public Double getNight() {
                return night;
            }

            public void setNight(Double night) {
                this.night = night;
            }

            public Double getEve() {
                return eve;
            }

            public void setEve(Double eve) {
                this.eve = eve;
            }

            public Double getMorn() {
                return morn;
            }

            public void setMorn(Double morn) {
                this.morn = morn;
            }
        }

        public static class Weather {
            @SerializedName("id")
            private Integer id;
            @SerializedName("main")
            private String main;
            @SerializedName("description")
            private String description;
            @SerializedName("icon")
            private String icon;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
    }
}
