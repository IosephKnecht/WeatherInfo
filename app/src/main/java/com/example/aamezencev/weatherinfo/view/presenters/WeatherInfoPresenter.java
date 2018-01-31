package com.example.aamezencev.weatherinfo.view.presenters;

import com.example.aamezencev.weatherinfo.domain.interactors.WeatherInfoInteractor;
import com.example.aamezencev.weatherinfo.domain.interactors.interfaces.IWeatherInfoInteractor;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.interfaces.IWeatherInfoActivity;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public class WeatherInfoPresenter implements IWeatherInfoPresenter, IWeatherInfoInteractorOutput {

    private IBaseRouter baseRouter;
    private IWeatherInfoInteractor weatherInfoInteractor;
    private IWeatherInfoActivity weatherInfoActivity;

    public WeatherInfoPresenter(IWeatherInfoActivity weatherInfoActivity, IBaseRouter baseRouter) {
        this.baseRouter = baseRouter;
        this.weatherInfoActivity = weatherInfoActivity;
        this.weatherInfoInteractor = new WeatherInfoInteractor(this);
    }

    @Override
    public void getCurrentWeather(Long key) {
        weatherInfoInteractor.executeCurrentWeather(key);
    }

    @Override
    public void deleteCurrentWeather(Long key) {
        weatherInfoInteractor.executeDeleteCurrentWeather(key);
    }

    @Override
    public void onDestroy() {
        baseRouter = null;
        weatherInfoInteractor.unRegister();
        weatherInfoActivity = null;
    }

    @Override
    public void onSucces(Object viewModel) {
        weatherInfoActivity.paintWeather(viewModel);
    }

    @Override
    public void onError(Exception ex) {

    }
}
