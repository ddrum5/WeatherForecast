package ddrum.weatherforecast.roomdatabases;




import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ddrum.weatherforecast.models.FvLocation;
import ddrum.weatherforecast.models.SearchHistory;

@Dao
public interface SearchHistoryDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(SearchHistory searchHistory);


    @Query("SELECT * FROM searchHistory")
    public List<SearchHistory> getSearchHistoryList();




}