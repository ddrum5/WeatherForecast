package ddrum.weatherforecast.roomdatabases;



import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ddrum.weatherforecast.models.FvLocation;
import ddrum.weatherforecast.models.SearchHistory;


@Database(entities = {FvLocation.class, SearchHistory.class}, version = 1,exportSchema = false)
public abstract class DatabaseInstance extends RoomDatabase {

    public static final String DATABASE_NAME = "WeatherDB";

    private static DatabaseInstance databaseInstance;

    public abstract FvLocationsDAO fvLocationsDAO();
    public abstract SearchHistoryDAO searchHistoryDAO();


    public static DatabaseInstance getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room.databaseBuilder(context, DatabaseInstance.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return databaseInstance;
    }
}