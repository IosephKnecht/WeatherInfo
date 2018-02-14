package com.example.aamezencev.weatherinfo.view.viewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.aamezencev.weatherinfo.BR;

/**
 * Created by aa.mezencev on 19.01.2018.
 */

public class ViewCurrentWeatherModel extends BaseObservable {
    private Long key = (long) 0;
    private String main = new String();
    private String description = new String();
    private String icon = new String();
    private String cod = new String();
    private Long id = (long) 0;
    private String lon = new String();
    private String lat = new String();
    private String temp = new String();
    private String pressure = new String();
    private String humidity = new String();
    private String tempMin = new String();
    private String tempMax = new String();
    private String seaLevel = new String();
    private String grndLevel = new String();
    private String speed = new String();
    private String deg = new String();
    private Long all = (long) 0;
    private String date;
    private String name;

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

    @Bindable
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
        notifyPropertyChanged(BR.icon);
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
        String lineSep = String.format("%n");
        return "weather: " + main + " " + description + lineSep;
    }

    public String getFullInfotmation() {
        String lineSep = String.format("%n");
        return "coordinates: " + lat + " " + lon + lineSep +
                "weather: " + main + " " + description + lineSep +
                "temp: " + temp + lineSep +
                "pressure: " + pressure + lineSep +
                "humidity: " + humidity + lineSep +
                "temp_min: " + tempMin + lineSep +
                "temp_max: " + tempMax + lineSep +
                "wind_speed: " + speed + lineSep +
                "wind_deg: " + deg + lineSep +
                "clouds: " + all + lineSep;
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

    @Override
    public boolean equals(Object obj) {
        ViewCurrentWeatherModel weatherModel = (ViewCurrentWeatherModel) obj;
        return this.key.equals(weatherModel.getKey()) &&
                this.main.equals(weatherModel.getMain()) &&
                this.description.equals(weatherModel.getDescription()) &&
                this.icon.equals(weatherModel.getIcon()) &&
                this.cod.equals(weatherModel.getCod()) &&
                this.id.equals(weatherModel.getId()) &&
                this.lon.equals(weatherModel.getLon()) &&
                this.lat.equals(weatherModel.getLat()) &&
                this.temp.equals(weatherModel.getTemp()) &&
                this.pressure.equals(weatherModel.getPressure()) &&
                this.humidity.equals(weatherModel.getHumidity()) &&
                this.tempMin.equals(weatherModel.getTempMin()) &&
                this.tempMax.equals(weatherModel.getTempMax()) &&
                this.speed.equals(weatherModel.getSpeed()) &&
                this.deg.equals(weatherModel.getDeg()) &&
                this.all.equals(weatherModel.getAll());
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
