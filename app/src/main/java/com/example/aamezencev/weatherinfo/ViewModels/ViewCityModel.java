package com.example.aamezencev.weatherinfo.ViewModels;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class ViewCityModel {
    private String id;
    private String name;
    private String country;
    private String lon;
    private String lat;

    public ViewCityModel(String id, String name, String country, String lon, String lat) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.lon = lon;
        this.lat = lat;
    }

    public ViewCityModel(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
