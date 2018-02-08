package com.example.aamezencev.weatherinfo.data.owmApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aa.mezencev on 08.02.2018.
 */

public class JsonCityInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("coord")
    private JsonCoordInfo jsonCoordInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonCoordInfo getJsonCoordInfo() {
        return jsonCoordInfo;
    }

    public void setJsonCoordInfo(JsonCoordInfo jsonCoordInfo) {
        this.jsonCoordInfo = jsonCoordInfo;
    }
}
