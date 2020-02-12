package com.rekhansh.weather.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rekhansh.weather.R;
import com.rekhansh.weather.data.model.Forecast;
import com.rekhansh.weather.data.model.ForecastData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private ForecastData forecastData;
    private Context context;

    public ForecastAdapter(Context context)
    {
        this.context = context;
    }
    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);

        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position)
    {
        Forecast data = forecastData.list.get(position);
        if(data==null) return;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(data.dt*1000);
        holder.mDate.setText(new SimpleDateFormat("EEE hh:mm aa", Locale.getDefault()).format(calendar.getTime()));
        holder.mTemp.setText(context.getString(R.string.n_degree,data.main.temp));

        holder.mWeather.setText(data.weather.get(0).main);
        switch (data.weather.get(0).main)
        {
            case "Clear":
                holder.mIcon.setImageResource(R.mipmap.sun);
                break;
            case "Clouds":
                holder.mIcon.setImageResource(R.mipmap.clouds);
                break;
            case "Thunderstorm":
                holder.mIcon.setImageResource(R.mipmap.storm);
                break;
            case "Rain":
            case "Drizzle":
                holder.mIcon.setImageResource(R.mipmap.rain);
                break;
            default:
                holder.mIcon.setImageResource(R.mipmap.clear);

        }
    }

    @Override
    public int getItemCount() {
        return forecastData==null?0:forecastData.list.size();
    }

    public void swapData(ForecastData forecastData)
    {
        this.forecastData = forecastData;
        notifyDataSetChanged();
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder {
        final TextView mDate, mWeather, mTemp;
        final ImageView mIcon;

        ForecastViewHolder(View view) {
            super(view);
            mDate = view.findViewById(R.id.itemForecastDate);
            mWeather = view.findViewById(R.id.itemForecastWeather);
            mTemp = view.findViewById(R.id.itemForecastTemp);
            mIcon = view.findViewById(R.id.itemForecastIcon);
        }
    }
}
