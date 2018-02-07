package com.example.aamezencev.weatherinfo.data.owmApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aa.mezencev on 15.01.2018.
 */

public class JsonWeatherModel {
    @SerializedName("weather")
    private List<JsonWeatherInfo> jsonWeatherInfoList;
    @SerializedName("main")
    private JsonMainInfo jsonMainInfo;
    @SerializedName("wind")
    private JsonWindInfo jsonWindInfo;
    @SerializedName("clouds")
    private JsonCloudsInfo jsonCloudsInfo;
    @SerializedName("dt_txt")
    private String date;

    public JsonWeatherModel() {

    }

    public List<JsonWeatherInfo> getJsonWeatherInfoList() {
        return jsonWeatherInfoList;
    }

    public JsonMainInfo getJsonMainInfo() {
        return jsonMainInfo;
    }

    public JsonWindInfo getJsonWindInfo() {
        return jsonWindInfo;
    }

    public JsonCloudsInfo getJsonCloudsInfo() {
        return jsonCloudsInfo;
    }
}
