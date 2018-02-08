package com.example.aamezencev.weatherinfo.data.owmApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aa.mezencev on 07.02.2018.
 */

public class JsonModelList {
    @SerializedName("list")
    private List<JsonWeatherModel> jsonWeatherModelList;

    @SerializedName("city")
    private JsonCityInfo jsonCityInfo;

    public List<JsonWeatherModel> getJsonWeatherModelList() {
        return jsonWeatherModelList;
    }

    public void setJsonWeatherModelList(List<JsonWeatherModel> jsonWeatherModelList) {
        this.jsonWeatherModelList = jsonWeatherModelList;
    }

    public void setJsonCityInfo(JsonCityInfo jsonCityInfo) {
        this.jsonCityInfo = jsonCityInfo;
    }

    public JsonCityInfo getJsonCityInfo() {
        return jsonCityInfo;
    }

    public JsonCoordInfo getJsonCoord(){
        return jsonCityInfo.getJsonCoordInfo();
    }
}
