package com.example.aamezencev.weatherinfo.data.googleApi

import com.google.gson.annotations.SerializedName

/**
 * Created by aa.mezencev on 12.01.2018.
 */

class JsonResultsGeo {
    @SerializedName("results")
    var jsonReverseGeoModelList: List<JsonReverseGeoModel>? = null

    val jsonLocationModel: JsonLocationModel?
        get() = if (jsonReverseGeoModelList!!.size != 0) {
            jsonReverseGeoModelList!![0].jsonGeometryModel!!.jsonLocationModel
        } else null

    constructor(jsonReverseGeoModelList: List<JsonReverseGeoModel>) {
        this.jsonReverseGeoModelList = jsonReverseGeoModelList
    }

    constructor() {

    }
}
