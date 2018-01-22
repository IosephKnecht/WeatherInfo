package com.example.aamezencev.weatherinfo.ViewModels;

/**
 * Created by aa.mezencev on 19.01.2018.
 */

public class ViewCurrentWeatherModel {
    private Long key;
    private String main;
    private String description;
    private String icon;
    private String cod;
    private Long id;
    private String lon;
    private String lat;
    private String temp;
    private String pressure;
    private String humidity;
    private String tempMin;
    private String tempMax;
    private String seaLevel;
    private String grndLevel;
    private String speed;
    private String deg;
    private Long all;

    public ViewCurrentWeatherModel() {
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBriefInformation() {
        String lineSep = System.lineSeparator();
        return "weather: " + main + " " + description + lineSep;
    }

    public String getFullInfotmation() {
        String lineSep = System.lineSeparator();
        return "coordinates: " + lat + " " + lon + lineSep +
                "weather: " + main + " " + description + lineSep +
                "temp: " + temp + lineSep +
                "pressure: " + pressure + lineSep +
                "humidity: " + humidity + lineSep +
                "temp_min: " + tempMin + lineSep +
                "temp_max: " + tempMax + lineSep +
                "wind_speed" + speed + lineSep +
                "wind_deg" + deg + lineSep +
                "clouds" + all + lineSep;
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

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(String seaLevel) {
        this.seaLevel = seaLevel;
    }

    public String getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(String grndLevel) {
        this.grndLevel = grndLevel;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public Long getAll() {
        return all;
    }

    public void setAll(Long all) {
        this.all = all;
    }

}
