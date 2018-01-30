package com.example.aamezencev.weatherinfo.data.owmApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aa.mezencev on 15.01.2018.
 */

public class JsonWeatherInfo {
    @SerializedName("id")
    private Long id;
    @SerializedName("main")
    private String main;
    @SerializedName("description")
    private String description;
    @SerializedName("icon")
    private String icon;

    public JsonWeatherInfo(Long id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        String lineSep = System.lineSeparator();
        return "id:" + id + lineSep +
                "main:" + main + lineSep +
                "description:" + description;
    }
}
