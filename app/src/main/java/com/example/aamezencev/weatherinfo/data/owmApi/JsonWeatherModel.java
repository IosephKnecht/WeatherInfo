package com.example.aamezencev.weatherinfo.data.owmApi;

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
    @SerializedName("id")
    private Long id;
    @SerializedName("coord")
    private JsonCoordInfo jsonCoordInfo;
    @SerializedName("main")
    private JsonMainInfo jsonMainInfo;
    @SerializedName("wind")
    private JsonWindInfo jsonWindInfo;
    @SerializedName("clouds")
    private JsonCloudsInfo jsonCloudsInfo;

//    public JsonWeatherModel(List<JsonWeatherInfo> jsonWeatherInfoList, String cod, JsonCoordInfo jsonCoordInfo, JsonMainInfo jsonMainInfo, JsonWindInfo jsonWindInfo, JsonCloudsInfo jsonCloudsInfo) {
//        this.jsonWeatherInfoList = jsonWeatherInfoList;
//        this.cod = cod;
////        this.jsonCoordInfo = jsonCoordInfo;
////        this.jsonMainInfo = jsonMainInfo;
////        this.jsonWindInfo = jsonWindInfo;
////        this.jsonCloudsInfo = jsonCloudsInfo;
//    }

    public JsonWeatherModel() {

    }

    public List<JsonWeatherInfo> getJsonWeatherInfoList() {
        return jsonWeatherInfoList;
    }

    public String getCod() {
        return cod;
    }

    public Long getId() {
        return id;
    }

    public JsonCoordInfo getJsonCoordInfo() {
        return jsonCoordInfo;
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
