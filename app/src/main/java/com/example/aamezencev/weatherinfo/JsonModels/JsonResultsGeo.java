package com.example.aamezencev.weatherinfo.JsonModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class JsonResultsGeo {
    @SerializedName("results")
    private List<JsonReverseGeoModel> jsonReverseGeoModelList;

    public JsonResultsGeo(List<JsonReverseGeoModel> jsonReverseGeoModelList) {
        this.jsonReverseGeoModelList = jsonReverseGeoModelList;
    }

    public JsonResultsGeo(){

    }

    public List<JsonReverseGeoModel> getJsonReverseGeoModelList() {
        return jsonReverseGeoModelList;
    }

    public void setJsonReverseGeoModelList(List<JsonReverseGeoModel> jsonReverseGeoModelList) {
        this.jsonReverseGeoModelList = jsonReverseGeoModelList;
    }
}
