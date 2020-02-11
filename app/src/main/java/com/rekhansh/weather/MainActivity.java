package com.rekhansh.weather;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.rekhansh.weather.data.model.WeatherData;
import com.rekhansh.weather.viewmodel.WeatherViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView temperature,weather;
    ImageView imgWeather;
    private WeatherViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperature = findViewById(R.id.txtTemp);
        weather = findViewById(R.id.txtWeather);
        imgWeather = findViewById(R.id.imgWeather);

        ImageView loader = findViewById(R.id.imgLoader);
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
        model.getWeatherData("51.507351", "-0.127758", new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                finishLoad();
                if(response.code()==200) {
                    WeatherData weatherData = response.body();
                    if (weatherData != null) {
                        temperature.setText(getString(R.string.degree,weatherData.main.temp));
                        weather.setText(weatherData.weather.get(0).main);
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
}
