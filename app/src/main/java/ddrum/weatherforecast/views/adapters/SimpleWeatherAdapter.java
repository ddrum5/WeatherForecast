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
import ddrum.weatherforecast.databinding.ItemCurrentBinding;
import ddrum.weatherforecast.models.CurrentWeather;
import ddrum.weatherforecast.ulti.Ulti;

public class SimpleWeatherAdapter extends RecyclerView.Adapter<SimpleWeatherAdapter.ViewHolder>{



    public void setData(List<CurrentWeather> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    Context context;
    List<CurrentWeather> list;
    public SimpleWeatherAdapter(Context context ){
        list= new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemCurrentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_current,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SimpleWeatherAdapter.ViewHolder holder, int position) {
        CurrentWeather currentWeather = list.get(position);
        String cityName = currentWeather.getName();
        String description = currentWeather.getWeather().get(0).getDescription();
        String temp = Math.round(currentWeather.getMain().getTemp()) + context.getString(R.string.tempUnit);
        String tempMinMax = "t:" + Math.round(currentWeather.getMain().getTempMin())
                + " c:" + Math.round(currentWeather.getMain().getTempMax());
        String iconUrl = "http://openweathermap.org/img/wn/" + currentWeather.getWeather().get(0).getIcon() + "@2x.png";

        holder.binding.currentTvCityName.setText(cityName);
        holder.binding.currentTvDescription.setText(description);
        holder.binding.currentTvTemp.setText(temp);
        holder.binding.currentTvTempMinMax.setText(tempMinMax);
        holder.binding.time.setText(Ulti.getCurrentTime());
        Glide.with(context).load(iconUrl).into(holder.binding.currentIconWeather);
    }


    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCurrentBinding binding;
        public ViewHolder(@NonNull @NotNull ItemCurrentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
