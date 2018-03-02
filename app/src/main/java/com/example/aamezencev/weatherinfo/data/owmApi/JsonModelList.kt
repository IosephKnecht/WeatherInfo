package com.example.aamezencev.weatherinfo.data.owmApi

import com.google.gson.annotations.SerializedName

/**
 * Created by aa.mezencev on 07.02.2018.
 */

data class JsonModelList(@field:SerializedName("list") val jsonWeatherModelList: List<JsonWeatherModel>? = null,
                    @field:SerializedName("city") val jsonCityInfo: JsonCityInfo? = null) {
    val jsonCoord: JsonCoordInfo?
        get() = jsonCityInfo!!.jsonCoordInfo
}

