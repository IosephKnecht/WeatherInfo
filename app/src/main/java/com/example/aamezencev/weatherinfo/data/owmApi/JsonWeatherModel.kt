package com.example.aamezencev.weatherinfo.data.owmApi

import com.google.gson.annotations.SerializedName

/**
 * Created by aa.mezencev on 15.01.2018.
 */

data class JsonWeatherModel(@field:SerializedName("weather") val jsonWeatherInfoList: List<JsonWeatherInfo>? = null,
                       @field:SerializedName("main") val jsonMainInfo: JsonMainInfo? = null,
                       @field:SerializedName("wind") val jsonWindInfo: JsonWindInfo? = null,
                       @field:SerializedName("clouds") val jsonCloudsInfo: JsonCloudsInfo? = null,
                       @field:SerializedName("dt_txt") val date: String? = null)
