package ddrum.weatherforecast.ulti;


import java.text.SimpleDateFormat;

public class Util {

    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(System.currentTimeMillis());
    }
    public static String getHour(Integer time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        return simpleDateFormat.format(time*1000L);
    }

    public static String getDayInWeek(Integer time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        return simpleDateFormat.format(time*1000L);
    }
    public static String upperCaseFirstLetter(String myString){
        return  (myString.substring(0, 1).toUpperCase() + myString.substring(1).toLowerCase());

    }


}
