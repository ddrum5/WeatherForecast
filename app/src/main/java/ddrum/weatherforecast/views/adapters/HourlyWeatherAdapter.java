package ddrum.weatherforecast.views.adapters;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseAdapter;
import ddrum.weatherforecast.base.BaseViewHolder;
import ddrum.weatherforecast.databinding.ItemHourlyBinding;
import ddrum.weatherforecast.models.OneCallWeather;
import ddrum.weatherforecast.ulti.Util;

public class HourlyWeatherAdapter extends BaseAdapter<OneCallWeather.Hourly, HourlyWeatherAdapter.Holder, ItemHourlyBinding> {

    public HourlyWeatherAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_hourly;
    }

    @Override
    protected Holder getViewHolder(ItemHourlyBinding binding) {
        return new Holder(binding);
    }

    @Override
    protected void bindView(OneCallWeather.Hourly item, Holder holder, int position) {


        String temp = Math.round(item.getTemp()) + "°";
        String iconUrl = "http://openweathermap.org/img/wn/" + item.getWeather().get(0).getIcon() + "@2x.png";
        String hour = Util.getHour(item.getDt());

      if(position==0){
          hour ="Bây giờ";
      }

        holder.binding.dailyTvHour.setText(hour);
        holder.binding.dailyTvTemp.setText(temp);
        Glide.with(context).load(iconUrl).into(holder.binding.dailyIconWeather);
    }


    public static class Holder extends BaseViewHolder<ItemHourlyBinding> {
        public Holder(@NonNull @NotNull ItemHourlyBinding binding) {
            super(binding);

        }
    }



}
