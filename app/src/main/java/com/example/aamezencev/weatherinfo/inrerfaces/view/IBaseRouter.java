package com.example.aamezencev.weatherinfo.inrerfaces.view;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public interface IBaseRouter {
    void openWeatherListActivity();

    void startUpdateService();

    void openWeatherInfoActivity(Long key,String actionTitle);
}
