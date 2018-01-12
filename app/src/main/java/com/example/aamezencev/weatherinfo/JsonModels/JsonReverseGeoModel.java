package com.example.aamezencev.weatherinfo.JsonModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class JsonReverseGeoModel {
    @SerializedName("geometry")
    private JsonGeometryModel jsonGeometryModel;

    public JsonReverseGeoModel(JsonGeometryModel jsonGeometryModel) {
        this.jsonGeometryModel = jsonGeometryModel;
    }

    public JsonReverseGeoModel() {
    }

    public JsonGeometryModel getJsonGeometryModel() {
        return jsonGeometryModel;
    }

    public void setJsonGeometryModel(JsonGeometryModel jsonGeometryModel) {
        this.jsonGeometryModel = jsonGeometryModel;
    }
}
