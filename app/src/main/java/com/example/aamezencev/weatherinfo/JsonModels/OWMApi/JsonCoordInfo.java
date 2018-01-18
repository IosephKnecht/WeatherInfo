package com.example.aamezencev.weatherinfo.JsonModels.OWMApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aa.mezencev on 18.01.2018.
 */

public class JsonCoordInfo {
    @SerializedName("lon")
    private Long lon;
    @SerializedName("lat")
    private Long lat;

    public JsonCoordInfo(Long lon, Long lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public Long getLat() {
        return lat;
    }

    public Long getLon() {
        return lon;
    }
}
