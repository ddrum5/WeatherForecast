package ddrum.weatherforecast.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.databinding.ItemHourlyBinding;
import ddrum.weatherforecast.models.OneCallWeather;
import ddrum.weatherforecast.ulti.Ulti;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder>{



    public void setData(List<OneCallWeather.Hourly> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    Context context;
    List<OneCallWeather.Hourly> list;
    public HourlyWeatherAdapter(Context context ){
        list= new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemHourlyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_hourly,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HourlyWeatherAdapter.ViewHolder holder, int position) {
        OneCallWeather.Hourly hourlyWeather = list.get(position);

        String temp = Math.round(hourlyWeather.getTemp()) + "";
        String iconUrl = "http://openweathermap.org/img/wn/" + hourlyWeather.getWeather().get(0).getIcon() + "@2x.png";
        String time = Ulti.getHour(hourlyWeather.getDt());

        holder.binding.dailyTvDay.setText(temp);
        holder.binding.dailyTvDay.setText(time);
        Glide.with(context).load(iconUrl).into(holder.binding.dailyIconWeather);
    }


    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemHourlyBinding binding;
        public ViewHolder(@NonNull @NotNull ItemHourlyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
