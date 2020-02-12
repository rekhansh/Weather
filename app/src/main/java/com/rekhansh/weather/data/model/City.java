package com.rekhansh.weather.data.model;

import com.google.gson.annotations.SerializedName;

public class City
{
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("coord")
    public Coordinate coord;
    @SerializedName("country")
    public String country;
}
