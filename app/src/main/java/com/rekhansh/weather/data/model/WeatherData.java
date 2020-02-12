package com.rekhansh.weather.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherData {
        @SerializedName("coord")
        public Coordinate coord;
        @SerializedName("sys")
        public Sys sys;
        @SerializedName("weather")
        public ArrayList<Weather> weather = new ArrayList<Weather>();
        @SerializedName("main")
        public Main main;
        @SerializedName("wind")
        public Wind wind;
        @SerializedName("rain")
        public Rain rain;
        @SerializedName("clouds")
        public Clouds clouds;
        @SerializedName("dt")
        public float dt;
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("cod")
        public float cod;
}

