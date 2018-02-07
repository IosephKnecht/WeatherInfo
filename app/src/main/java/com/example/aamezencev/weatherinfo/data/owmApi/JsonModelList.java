package com.example.aamezencev.weatherinfo.data.owmApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aa.mezencev on 07.02.2018.
 */

public class JsonModelList {
    @SerializedName("list")
    private List<JsonWeatherModel> jsonWeatherModelList;

    public List<JsonWeatherModel> getJsonWeatherModelList() {
        return jsonWeatherModelList;
    }

    public void setJsonWeatherModelList(List<JsonWeatherModel> jsonWeatherModelList) {
        this.jsonWeatherModelList = jsonWeatherModelList;
    }
}
