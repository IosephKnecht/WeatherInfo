package com.example.aamezencev.weatherinfo.view.interfaces;

import java.util.List;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public interface IWeatherInfoActivity<T> {
    void paintWeather(List<T> weatherModels);
}
