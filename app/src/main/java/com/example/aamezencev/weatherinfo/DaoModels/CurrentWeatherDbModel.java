package com.example.aamezencev.weatherinfo.DaoModels;

import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by aa.mezencev on 19.01.2018.
 */
@Entity
public class CurrentWeatherDbModel {
    @Id
    private Long key;
    @NotNull
    private String main;
    @NotNull
    private String description;
    @NotNull
    private String icon;
    @NotNull
    private String cod;
    @NotNull
    private Long id;
    @NotNull
    private String lon;
    @NotNull
    private String lat;
    @NotNull
    private String temp;
    @NotNull
    private String pressure;
    @NotNull
    private String humidity;
    @NotNull
    private String tempMin;
    @NotNull
    private String tempMax;
    @Nullable
    private String seaLevel;
    @Nullable
    private String grndLevel;
    @NotNull
    private String speed;
    @NotNull
    private String deg;
    @NotNull
    private Long all;
    @Generated(hash = 63842790)
    public CurrentWeatherDbModel(Long key, @NotNull String main,
            @NotNull String description, @NotNull String icon, @NotNull String cod,
            @NotNull Long id, @NotNull String lon, @NotNull String lat,
            @NotNull String temp, @NotNull String pressure,
            @NotNull String humidity, @NotNull String tempMin,
            @NotNull String tempMax, String seaLevel, String grndLevel,
            @NotNull String speed, @NotNull String deg, @NotNull Long all) {
        this.key = key;
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.cod = cod;
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.seaLevel = seaLevel;
        this.grndLevel = grndLevel;
        this.speed = speed;
        this.deg = deg;
        this.all = all;
    }
    @Generated(hash = 1721116696)
    public CurrentWeatherDbModel() {
    }
    public Long getKey() {
        return this.key;
    }
    public void setKey(Long key) {
        this.key = key;
    }
    public String getMain() {
        return this.main;
    }
    public void setMain(String main) {
        this.main = main;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getCod() {
        return this.cod;
    }
    public void setCod(String cod) {
        this.cod = cod;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLon() {
        return this.lon;
    }
    public void setLon(String lon) {
        this.lon = lon;
    }
    public String getLat() {
        return this.lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getTemp() {
        return this.temp;
    }
    public void setTemp(String temp) {
        this.temp = temp;
    }
    public String getPressure() {
        return this.pressure;
    }
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
    public String getHumidity() {
        return this.humidity;
    }
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
    public String getTempMin() {
        return this.tempMin;
    }
    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }
    public String getTempMax() {
        return this.tempMax;
    }
    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }
    public String getSeaLevel() {
        return this.seaLevel;
    }
    public void setSeaLevel(String seaLevel) {
        this.seaLevel = seaLevel;
    }
    public String getGrndLevel() {
        return this.grndLevel;
    }
    public void setGrndLevel(String grndLevel) {
        this.grndLevel = grndLevel;
    }
    public String getSpeed() {
        return this.speed;
    }
    public void setSpeed(String speed) {
        this.speed = speed;
    }
    public String getDeg() {
        return this.deg;
    }
    public void setDeg(String deg) {
        this.deg = deg;
    }
    public Long getAll() {
        return this.all;
    }
    public void setAll(Long all) {
        this.all = all;
    }
}
