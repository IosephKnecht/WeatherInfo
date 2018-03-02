package com.example.aamezencev.weatherinfo.data.owmApi

import com.google.gson.annotations.SerializedName

/**
 * Created by aa.mezencev on 08.02.2018.
 */

data class JsonCityInfo(@field:SerializedName("id") val id: String? = null,
                   @field:SerializedName("name") val name: String? = null,
                   @field:SerializedName("coord") val jsonCoordInfo: JsonCoordInfo? = null) {
}
