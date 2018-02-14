package com.example.aamezencev.weatherinfo.view.handlers;

import android.view.View;

import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.interfaces.IWeatherInfoActivity;
import com.example.aamezencev.weatherinfo.view.presenters.IWeatherInfoPresenter;

/**
 * Created by aa.mezencev on 14.02.2018.
 */

public class WeatherInfoHandlers {

    private IWeatherInfoPresenter presenter;
    private IBaseRouter baseRouter;

    public WeatherInfoHandlers(IWeatherInfoPresenter presenter, IBaseRouter baseRouter) {
        this.presenter = presenter;
        this.baseRouter = baseRouter;
    }

    public void btnDeleteClick(View view, Long key) {
        presenter.deleteCurrentWeather(key);
    }

    public void onDestroy() {
        presenter = null;
        baseRouter = null;
    }
}
