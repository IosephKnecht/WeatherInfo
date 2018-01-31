package com.example.aamezencev.weatherinfo.domain.interactors.interfaces;

import com.example.aamezencev.weatherinfo.view.viewModels.ViewCurrentWeatherModel;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public interface IWeatherInfoInteractor<T> {
    void executeCurrentWeather(Long key);

    void executeDeleteCurrentWeather(Long key);

    void unRegister();
}
