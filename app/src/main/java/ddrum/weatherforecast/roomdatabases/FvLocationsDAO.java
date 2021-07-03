package ddrum.weatherforecast.roomdatabases;




import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ddrum.weatherforecast.models.FvLocation;

@Dao
public interface FvLocationsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(FvLocation fvLocations);

    @Query("SELECT * FROM fvLocations")
    public List<FvLocation> getFvLocations();

    @Query("DELETE FROM fvLocations WHERE  cityId=:cityId ")
    public void deleteFvLocationById(String cityId);


}