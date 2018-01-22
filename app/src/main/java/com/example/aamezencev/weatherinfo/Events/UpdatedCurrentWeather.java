package com.example.aamezencev.weatherinfo.Events;

import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModel;

import java.util.List;

/**
 * Created by aa.mezencev on 22.01.2018.
 */

public class UpdatedCurrentWeather {
    private List<CurrentWeatherDbModel> currentWeatherDbModelList;

    public UpdatedCurrentWeather(List<CurrentWeatherDbModel> currentWeatherDbModelList) {
        this.currentWeatherDbModelList = currentWeatherDbModelList;
    }

    public List<CurrentWeatherDbModel> getCurrentWeatherDbModelList() {
        return currentWeatherDbModelList;
    }
}
