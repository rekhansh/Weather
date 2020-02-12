package com.rekhansh.weather.data.model;

import com.google.gson.annotations.SerializedName;

public class Coordinate {
    @SerializedName("lon")
    public float lon;
    @SerializedName("lat")
    public float lat;
}
