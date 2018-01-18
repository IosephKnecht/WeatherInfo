package com.example.aamezencev.weatherinfo.JsonModels.OWMApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aa.mezencev on 18.01.2018.
 */

public class JsonWindInfo {
    @SerializedName("speed")
    private Long speed;
    @SerializedName("deg")
    private Long deg;

    public JsonWindInfo(Long speed, Long deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public Long getSpeed() {
        return speed;
    }

    public Long getDeg() {
        return deg;
    }
}
