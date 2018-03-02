package com.example.aamezencev.weatherinfo.data.googleApi

import com.google.gson.annotations.SerializedName

/**
 * Created by aa.mezencev on 12.01.2018.
 */

data class JsonReverseGeoModel(@field:SerializedName("geometry") val jsonGeometryModel: JsonGeometryModel? = null) {
}
