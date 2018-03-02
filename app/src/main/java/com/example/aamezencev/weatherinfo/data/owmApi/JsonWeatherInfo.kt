package com.example.aamezencev.weatherinfo.data.owmApi

import com.google.gson.annotations.SerializedName

/**
 * Created by aa.mezencev on 15.01.2018.
 */

data class JsonWeatherInfo(@field:SerializedName("id") val id: Long?,
                      @field:SerializedName("main") val main: String,
                      @field:SerializedName("description") val description: String,
                      @field:SerializedName("icon") val icon: String) {

    override fun toString(): String {
        val lineSep = String.format("%n")
        return "id:" + id + lineSep +
                "main:" + main + lineSep +
                "description:" + description
    }
}
