package ddrum.weatherforecast.ulti;


import java.text.SimpleDateFormat;

public class Ulti {

    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(System.currentTimeMillis());
    }
    public static String getHour(Integer time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        return simpleDateFormat.format(time*1000L);
    }



}
