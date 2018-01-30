package com.example.aamezencev.weatherinfo.data.owmApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aa.mezencev on 18.01.2018.
 */

public class JsonCoordInfo {
    @SerializedName("lon")
    private String lon;
    @SerializedName("lat")
    private String lat;

    public JsonCoordInfo(String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public JsonCoordInfo() {

    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
