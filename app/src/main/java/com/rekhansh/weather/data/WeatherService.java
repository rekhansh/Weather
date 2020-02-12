package com.rekhansh.weather.data;

import com.rekhansh.weather.data.model.ForecastData;
import com.rekhansh.weather.data.model.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/weather?")
    Call<WeatherData> getWeatherData (@Query("lat") String latitude,
                                      @Query("lon") String longitude,
                                      @Query("appid") String appId,
                                      @Query("units") String units);

    @GET("data/2.5/forecast?")
    Call<ForecastData> getForecastData (@Query("lat") String latitude,
                                        @Query("lon") String longitude,
                                        @Query("appid") String appId,
                                        @Query("units") String units);
}
