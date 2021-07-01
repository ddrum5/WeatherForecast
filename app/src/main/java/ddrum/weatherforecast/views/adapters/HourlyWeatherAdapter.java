package ddrum.weatherforecast.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseAdapter;
import ddrum.weatherforecast.base.BaseViewHolder;
import ddrum.weatherforecast.databinding.ItemCurrentBinding;
import ddrum.weatherforecast.databinding.ItemHourlyBinding;
import ddrum.weatherforecast.models.CurrentWeather;
import ddrum.weatherforecast.models.OneCallWeather;
import ddrum.weatherforecast.ulti.Ulti;

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
        String time = Ulti.getHour(item.getDt());
        holder.binding.dailyTvTemp.setText(temp);
        holder.binding.dailyTvHour.setText(time);
        Glide.with(context).load(iconUrl).into(holder.binding.dailyIconWeather);
    }


    public static class Holder extends BaseViewHolder<ItemHourlyBinding> {
        public Holder(@NonNull @NotNull ItemHourlyBinding binding) {
            super(binding);

        }
    }



}
