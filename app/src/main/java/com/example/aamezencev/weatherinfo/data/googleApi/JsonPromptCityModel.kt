package com.example.aamezencev.weatherinfo.data.googleApi

import com.google.gson.annotations.SerializedName

/**
 * Created by aa.mezencev on 11.01.2018.
 */

data class JsonPromptCityModel(@field:SerializedName("description") val description: String,
                               @field:SerializedName("id") val id: String,
                               @field:SerializedName("structured_formatting") val structuredFormatting: JsonPromptCityStructureFormatting,
                               @field:SerializedName("place_id") val placeId: String? = null) {
}
