package com.example.aamezencev.weatherinfo.data.owmApi

import com.google.gson.annotations.SerializedName

/**
 * Created by aa.mezencev on 18.01.2018.
 */

data class JsonMainInfo(@field:SerializedName("temp") val temp: String,
                   @field:SerializedName("pressure") val pressure: String,
                   @field:SerializedName("humidity") val humidity: String,
                   @field:SerializedName("temp_min") val tempMin: String,
                   @field:SerializedName("temp_max") val tempMax: String,
                   @field:SerializedName("sea_level") val seaLevel: String,
                   @field:SerializedName("grnd_level") val grndLevel: String) {

    val infMain: String
        get() {
            val lineSep = String.format("%n")
            return lineSep + temp + lineSep +
                    tempMax + lineSep +
                    tempMin + lineSep
        }
}
