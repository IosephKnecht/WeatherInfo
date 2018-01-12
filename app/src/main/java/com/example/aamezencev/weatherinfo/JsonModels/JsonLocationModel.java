package com.example.aamezencev.weatherinfo.JsonModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class JsonLocationModel {
    @SerializedName("lat")
    private String lat;
    @SerializedName("lng")
    private String lng;

    public JsonLocationModel(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
