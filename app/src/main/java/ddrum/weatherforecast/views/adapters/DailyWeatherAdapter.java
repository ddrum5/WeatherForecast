package ddrum.weatherforecast.views.adapters;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import ddrum.weatherforecast.R;
import ddrum.weatherforecast.base.BaseAdapter;
import ddrum.weatherforecast.base.BaseViewHolder;
import ddrum.weatherforecast.databinding.ItemDailyBinding;
import ddrum.weatherforecast.models.OneCallWeather;
import ddrum.weatherforecast.ulti.Util;

public class DailyWeatherAdapter extends BaseAdapter<OneCallWeather.Daily, DailyWeatherAdapter.Holder, ItemDailyBinding> {

    public DailyWeatherAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_daily;
    }

    @Override
    protected Holder getViewHolder(ItemDailyBinding binding) {
        return new Holder(binding);
    }

    @Override
    protected void bindView(OneCallWeather.Daily item, Holder holder, int position) {
        String time = Util.getDayInWeek(item.getDt());
        String tempMinMax =Math.round(item.getTemp().getMin())
                + "  " + Math.round(item.getTemp().getMax());
        String iconUrl = "http://openweathermap.org/img/wn/" + item.getWeather().get(0).getIcon() + "@2x.png";
        String description = item.getWeather().get(0).getDescription();

        holder.binding.dailyTvDay.setText(time);
        holder.binding.dailyTvDescription.setText(description);
        holder.binding.dailyTvTempMinMax.setText(tempMinMax);
        Glide.with(context).load(iconUrl).into(holder.binding.dailyIconWeather);
    }

    public static class Holder extends BaseViewHolder<ItemDailyBinding> {
        public Holder(@NonNull @NotNull ItemDailyBinding binding) {
            super(binding);

        }
    }



}
