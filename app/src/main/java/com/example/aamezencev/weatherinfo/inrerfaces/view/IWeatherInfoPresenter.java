package com.example.aamezencev.weatherinfo.inrerfaces.view;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public interface IWeatherInfoPresenter {
    void getCurrentWeather(Long key);

    void deleteCurrentWeather(Long key);

    void onDestroy();
}
