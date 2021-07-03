package ddrum.weatherforecast.roomdatabases;




import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ddrum.weatherforecast.models.FvLocation;

@Dao
public interface FvLocationsDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Long insert(FvLocation fvLocations);

    @Delete
    public void delete(FvLocation fvLocation);

    @Query("SELECT * FROM fvLocations")
    public List<FvLocation> getFvLocations();

    @Query("DELETE FROM fvLocations WHERE  cityId=:cityId ")
    public void removeFvLocationById(String cityId);


}