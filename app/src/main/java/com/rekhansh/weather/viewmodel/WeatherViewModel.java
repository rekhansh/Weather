package com.rekhansh.weather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.rekhansh.weather.data.DataRepository;
import com.rekhansh.weather.data.model.WeatherData;

import retrofit2.Callback;

public class WeatherViewModel extends AndroidViewModel {

    public DataRepository dataRepository;
    public WeatherViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository();
    }
    public void getWeatherData(String lat, String lon, Callback<WeatherData> callback)
    {
        dataRepository.getWeatherData(lat,lon).enqueue(callback);
    }
}
