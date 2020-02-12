package com.rekhansh.weather;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rekhansh.weather.adapters.ForecastAdapter;
import com.rekhansh.weather.data.model.ForecastData;
import com.rekhansh.weather.data.model.WeatherData;
import com.rekhansh.weather.viewmodel.WeatherViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView temperature,weather,location;
    ImageView imgWeather;
    private WeatherViewModel model;
    private ForecastAdapter forecastAdapter;
    private double Lat = 51.507351,Lon=-0.127758;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find IDs
        temperature = findViewById(R.id.txtTemp);
        weather = findViewById(R.id.txtWeather);
        imgWeather = findViewById(R.id.imgWeather);
        location = findViewById(R.id.txtLocation);

        ImageView loader = findViewById(R.id.imgLoader);
        RecyclerView recyclerView = findViewById(R.id.rvForecast);

        //Setup Adapter
        forecastAdapter = new ForecastAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(forecastAdapter);

        //Set Animation
        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(500);
        rotate.setRepeatCount(Animation.INFINITE);
        loader.startAnimation(rotate);
        findViewById(R.id.btnRetry).setOnClickListener(view -> Retry());
        model = new ViewModelProvider(this).get(WeatherViewModel.class);
        onLoad();

    }

    private void Retry()
    {
        onLoad();
    }
    private void loadError()
    {
        findViewById(R.id.dataView).setVisibility(View.GONE);
        findViewById(R.id.loadingView).setVisibility(View.GONE);
        findViewById(R.id.errorView).setVisibility(View.VISIBLE);
    }
    private void onLoad()
    {
        findViewById(R.id.dataView).setVisibility(View.GONE);
        findViewById(R.id.errorView).setVisibility(View.GONE);
        findViewById(R.id.loadingView).setVisibility(View.VISIBLE);
        model.getWeatherData(String.valueOf(Lat), String.valueOf(Lon), new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                finishLoad();
                if(response.code()==200) {
                    WeatherData weatherData = response.body();
                    if (weatherData != null) {
                        temperature.setText(String.valueOf(weatherData.main.feels_like));
                        weather.setText(weatherData.weather.get(0).main);
                        location.setText(weatherData.name);
                        switch (weatherData.weather.get(0).main)
                        {
                            case "Clear":
                                imgWeather.setImageResource(R.mipmap.sun);
                                break;
                            case "Clouds":
                                imgWeather.setImageResource(R.mipmap.clouds);
                                break;
                            case "Thunderstorm":
                                imgWeather.setImageResource(R.mipmap.storm);
                                break;
                            case "Rain":
                            case "Drizzle":
                                imgWeather.setImageResource(R.mipmap.rain);
                                break;
                            default:
                                imgWeather.setImageResource(R.mipmap.clear);

                        }
                        loadForecastData();
                    }
                }
                else
                {
                    loadError();
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                finishLoad();
                loadError();
            }
        });

    }
    private void finishLoad()
    {
        findViewById(R.id.loadingView).setVisibility(View.GONE);
        findViewById(R.id.dataView).setVisibility(View.VISIBLE);
    }

    private void loadForecastData()
    {
        model.getForecastData(String.valueOf(Lat), String.valueOf(Lon), new Callback<ForecastData>() {
            @Override
            public void onResponse(Call<ForecastData> call, Response<ForecastData> response) {
                finishLoad();
                if(response.code()==200) {
                    ForecastData forecastData = response.body();
                    if (forecastData != null) {
                        forecastAdapter.swapData(forecastData);
                    }
                }
                else
                {
                    loadError();
                }
            }

            @Override
            public void onFailure(Call<ForecastData> call, Throwable t) {
                finishLoad();
                loadError();
            }
        });
    }
}
