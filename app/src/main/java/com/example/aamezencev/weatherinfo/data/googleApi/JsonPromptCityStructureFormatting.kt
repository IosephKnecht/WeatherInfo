package com.example.aamezencev.weatherinfo.data.googleApi

import com.google.gson.annotations.SerializedName

/**
 * Created by aa.mezencev on 11.01.2018.
 */

data class JsonPromptCityStructureFormatting(@field:SerializedName("main_text") val mainText: String,
                                             @field:SerializedName("secondary_text") val secondaryText: String)
