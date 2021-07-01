package ddrum.weatherforecast.ulti;


import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

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
    public static String getDayInWeek(Integer time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        return simpleDateFormat.format(time*1000L);
    }





}
