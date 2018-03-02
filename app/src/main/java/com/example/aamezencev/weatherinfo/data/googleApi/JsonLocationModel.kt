package com.example.aamezencev.weatherinfo.data.googleApi

import com.google.gson.annotations.SerializedName

/**
 * Created by aa.mezencev on 12.01.2018.
 */

data class JsonLocationModel(@field:SerializedName("lat") val lat: String?,
                        @field:SerializedName("lng") val lng: String?)
