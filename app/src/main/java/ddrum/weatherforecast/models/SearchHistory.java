package ddrum.weatherforecast.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "searchHistory")
public class SearchHistory {

    @PrimaryKey
    @NonNull private String text;
    private String userId;

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public SearchHistory(String userId, String text) {
        this.userId = userId;
        this.text = text;
    }

    public SearchHistory() {
    }



}
