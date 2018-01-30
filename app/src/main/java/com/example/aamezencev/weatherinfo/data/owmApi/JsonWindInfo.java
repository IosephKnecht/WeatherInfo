package com.example.aamezencev.weatherinfo.data.owmApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aa.mezencev on 18.01.2018.
 */

public class JsonWindInfo {
    @SerializedName("speed")
    private String speed;
    @SerializedName("deg")
    private String deg;

    public JsonWindInfo(String speed, String deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public String getSpeed() {
        return speed;
    }

    public String getDeg() {
        return deg;
    }
}
