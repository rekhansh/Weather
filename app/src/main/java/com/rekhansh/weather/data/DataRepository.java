package com.rekhansh.weather.data;

import com.rekhansh.weather.data.model.ForecastData;
import com.rekhansh.weather.data.model.WeatherData;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRepository
{
    private static WeatherService weatherService;
    private static String BaseURL = "http://api.openweathermap.org/";
    private static String KEY = "2d644e5df5cbe8a254d12cda888e8a50";
    private static String UNIT = "metric";
    public DataRepository()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherService = retrofit.create(WeatherService.class);
    }
    public WeatherService getWeatherService()
    {
        return weatherService;
    }
    public Call<WeatherData> getWeatherData(String lat,String lon)
    {
        return weatherService.getWeatherData(lat, lon,KEY,UNIT);
    }
    public Call<ForecastData> getForecastData(String lat, String lon)
    {
        return weatherService.getForecastData(lat, lon,KEY,UNIT);
    }
}
