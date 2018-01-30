package com.example.aamezencev.weatherinfo.data.googleApi;

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

    public JsonResultsGeo() {

    }

    public List<JsonReverseGeoModel> getJsonReverseGeoModelList() {
        return jsonReverseGeoModelList;
    }

    public void setJsonReverseGeoModelList(List<JsonReverseGeoModel> jsonReverseGeoModelList) {
        this.jsonReverseGeoModelList = jsonReverseGeoModelList;
    }

    public JsonLocationModel getJsonLocationModel() {
        if (jsonReverseGeoModelList.size() != 0) {
            return jsonReverseGeoModelList.get(0).getJsonGeometryModel().getJsonLocationModel();
        }
        return null;
    }
}
