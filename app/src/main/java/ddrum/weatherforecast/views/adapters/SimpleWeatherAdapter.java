package ddrum.weatherforecast.views.adapters;

import android.content.Context;
import android.view.View;


import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseAdapter;
import ddrum.weatherforecast.base.BaseViewHolder;
import ddrum.weatherforecast.databinding.ItemCurrentBinding;
import ddrum.weatherforecast.models.CurrentWeather;
import ddrum.weatherforecast.ulti.Ulti;

public class SimpleWeatherAdapter extends BaseAdapter<CurrentWeather, SimpleWeatherAdapter.Holder,ItemCurrentBinding> {

    public SimpleWeatherAdapter(Context context) {
        super(context);
    }
    Callback click;

    public void setClick(Callback click) {
        this.click = click;
    }


    @Override
    protected int getLayout() {
        return R.layout.item_current;
    }

    @Override
    protected Holder getViewHolder(ItemCurrentBinding binding) {
        return new Holder(binding);
    }

    @Override
    protected void bindView(CurrentWeather currentWeather, Holder holder, int position) {
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClick(currentWeather.getId().toString());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                click.onLongClick(currentWeather.getId().toString());
                return false;
            }
        });
    }


    public static class Holder extends BaseViewHolder<ItemCurrentBinding> {
        public Holder(@NonNull @NotNull ItemCurrentBinding binding) {
            super(binding);
        }
    }

    public interface Callback{
         void onClick(String cityId);
         void onLongClick(String cityId);
    }
}
