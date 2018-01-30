package com.example.aamezencev.weatherinfo.data.googleApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class JsonGeometryModel {
    @SerializedName("location")
    private JsonLocationModel jsonLocationModel;

    public JsonGeometryModel(JsonLocationModel jsonLocationModel) {
        this.jsonLocationModel = jsonLocationModel;
    }

    public JsonLocationModel getJsonLocationModel() {
        return jsonLocationModel;
    }

    public void setJsonLocationModel(JsonLocationModel jsonLocationModel) {
        this.jsonLocationModel = jsonLocationModel;
    }
}
