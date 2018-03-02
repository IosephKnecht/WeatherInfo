package com.example.aamezencev.weatherinfo.data.owmApi

import com.google.gson.annotations.SerializedName

/**
 * Created by aa.mezencev on 18.01.2018.
 */

data class JsonCoordInfo(@field:SerializedName("lon") val lon: String? = "",
                    @field:SerializedName("lat") val lat: String? = "") {

}
