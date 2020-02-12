package com.rekhansh.weather.data.model;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ForecastData {

    @SerializedName("cod")
    public String cod;

    @SerializedName("message")
    public int message;

    @SerializedName("cnt")
    public int cnt;

    @SerializedName("list")
    public ArrayList<Forecast> list;

    @SerializedName("city")
    public City city;
}

