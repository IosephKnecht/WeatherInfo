package com.example.aamezencev.weatherinfo.JsonModels.OWMApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aa.mezencev on 18.01.2018.
 */

public class JsonMainInfo {
    @SerializedName("temp")
    private Long temp;
    @SerializedName("pressure")
    private Long pressure;
    @SerializedName("humidity")
    private Long humidity;
    @SerializedName("temp_min")
    private Long tempMin;
    @SerializedName("temp_max")
    private Long tempMax;
    @SerializedName("sea_level")
    private Long seaLevel;
    @SerializedName("grnd_level")
    private Long grndLevel;

    public JsonMainInfo(Long temp, Long pressure, Long humidity,
                        Long tempMin, Long tempMax, Long seaLevel,
                        Long grndLevel) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.seaLevel = seaLevel;
        this.grndLevel = grndLevel;
    }

    public Long getTemp() {
        return temp;
    }

    public Long getPressure() {
        return pressure;
    }

    public Long getHumidity() {
        return humidity;
    }

    public Long getTempMin() {
        return tempMin;
    }

    public Long getTempMax() {
        return tempMax;
    }

    public Long getSeaLevel() {
        return seaLevel;
    }

    public Long getGrndLevel() {
        return grndLevel;
    }
}
