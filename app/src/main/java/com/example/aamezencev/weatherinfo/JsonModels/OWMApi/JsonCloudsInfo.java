package com.example.aamezencev.weatherinfo.JsonModels.OWMApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aa.mezencev on 18.01.2018.
 */

public class JsonCloudsInfo {
    @SerializedName("all")
    private Long all;

    public JsonCloudsInfo(Long all) {
        this.all = all;
    }

    public Long getAll() {
        return all;
    }
}
