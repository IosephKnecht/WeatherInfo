package com.example.aamezencev.weatherinfo.data.googleApi

import com.google.gson.annotations.SerializedName

/**
 * Created by aa.mezencev on 11.01.2018.
 */
data class JsonPromptModel(@field:SerializedName("predictions") val jsonPromptCityModels: MutableList<JsonPromptCityModel>? = null,
                           @field:SerializedName("status") val status: String? = null) {
}
