package com.example.aamezencev.weatherinfo.JsonModels;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aa.mezencev on 10.01.2018.
 */
public class JsonCityModel {
    @NonNull
    @SerializedName("id")
    private Long id;
    @NonNull
    @SerializedName("name")
    private String name;
    @NonNull
    @SerializedName("country")
    private String country;
    //@SerializedName("coord")
    //private Float[] coord;

    public JsonCityModel(Long id, String name, String country, Float[] coord) {
        this.id = id;
        this.name = name;
        this.country = country;
        //this.coord = coord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

//    public Float[] getCoord() {
//        return coord;
//    }
//
//    public void setCoord(Float[] coord) {
//        this.coord = coord;
//    }
}
