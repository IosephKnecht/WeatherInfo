package com.example.aamezencev.weatherinfo.data.owmApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aa.mezencev on 18.01.2018.
 */

public class JsonMainInfo {
    @SerializedName("temp")
    private String temp;
    @SerializedName("pressure")
    private String pressure;
    @SerializedName("humidity")
    private String humidity;
    @SerializedName("temp_min")
    private String tempMin;
    @SerializedName("temp_max")
    private String tempMax;
    @SerializedName("sea_level")
    private String seaLevel;
    @SerializedName("grnd_level")
    private String grndLevel;

    public JsonMainInfo(String temp, String pressure, String humidity,
                        String tempMin, String tempMax, String seaLevel,
                        String grndLevel) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.seaLevel = seaLevel;
        this.grndLevel = grndLevel;
    }

    public String getTemp() {
        return temp;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getSeaLevel() {
        return seaLevel;
    }

    public String getGrndLevel() {
        return grndLevel;
    }

    public String getInfMain() {
        String lineSep = System.lineSeparator();
        return lineSep + temp + lineSep +
                tempMax + lineSep +
                tempMin + lineSep;
    }
}
