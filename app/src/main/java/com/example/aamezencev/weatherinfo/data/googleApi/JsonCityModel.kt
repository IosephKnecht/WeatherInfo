package com.example.aamezencev.weatherinfo.data.googleApi

import com.google.gson.annotations.SerializedName

/**
 * Created by aa.mezencev on 10.01.2018.
 */
class JsonCityModel
//@SerializedName("coord")
//private Float[] coord;

(@field:SerializedName("id")
 var id: Long?,
 @field:SerializedName("name")
 var name: String,
 @field:SerializedName("country")
 var country: String, coord: Array<Float>)//this.coord = coord;
//    public Float[] getCoord() {
//        return coord;
//    }
//
//    public void setCoord(Float[] coord) {
//        this.coord = coord;
//    }
