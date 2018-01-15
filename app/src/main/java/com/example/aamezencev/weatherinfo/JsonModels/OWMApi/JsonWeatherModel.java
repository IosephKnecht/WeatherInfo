package com.example.aamezencev.weatherinfo.JsonModels.OWMApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aa.mezencev on 15.01.2018.
 */

public class JsonWeatherModel {
    @SerializedName("weather")
    private List<JsonWeatherInfo> jsonWeatherInfoList;
    @SerializedName("cod")
    private String cod;

    public JsonWeatherModel(List<JsonWeatherInfo> jsonWeatherInfoList, String cod) {
        this.jsonWeatherInfoList = jsonWeatherInfoList;
        this.cod = cod;
    }

    public JsonWeatherModel(){

    }

    public List<JsonWeatherInfo> getJsonWeatherInfoList() {
        return jsonWeatherInfoList;
    }

    public String getCod() {
        return cod;
    }
}
