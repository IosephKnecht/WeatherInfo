package com.example.aamezencev.weatherinfo.data.owmApi

import com.google.gson.annotations.SerializedName

/**
 * Created by aa.mezencev on 18.01.2018.
 */

data class JsonWindInfo(@field:SerializedName("speed") val speed: String,
                        @field:SerializedName("deg") val deg: String)
