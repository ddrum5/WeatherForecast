package ddrum.weatherforecast.ulti;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ddrum.weatherforecast.models.FvLocation;
import ddrum.weatherforecast.models.SearchHistory;

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
    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    public static  List<String> getTextList(List<SearchHistory> searchHistories) {
        List<String> list = new ArrayList<>();
        for (SearchHistory sh : searchHistories) {
            list.add(sh.getText());
        }
        Collections.reverse(list);
        return list;
    }




}
