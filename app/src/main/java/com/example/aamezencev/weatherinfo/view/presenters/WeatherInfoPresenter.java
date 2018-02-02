package com.example.aamezencev.weatherinfo.view.presenters;

import com.example.aamezencev.weatherinfo.domain.interactors.WeatherInfoInteractor;
import com.example.aamezencev.weatherinfo.domain.interactors.interfaces.IWeatherInfoInteractor;
import com.example.aamezencev.weatherinfo.events.WeatherDeleteItemEvent;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.interfaces.IWeatherInfoActivity;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewCurrentWeatherModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public class WeatherInfoPresenter implements IWeatherInfoPresenter, IWeatherInfoInteractorOutput {

    private IBaseRouter baseRouter;
    private IWeatherInfoInteractor weatherInfoInteractor;
    private IWeatherInfoActivity weatherInfoActivity;

    private ViewCurrentWeatherModel viewCurrentWeatherModel = new ViewCurrentWeatherModel();

    public WeatherInfoPresenter() {
        weatherInfoInteractor = new WeatherInfoInteractor(this);
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
    public void onAttachView(IWeatherInfoActivity weatherInfoActivity, IBaseRouter baseRouter) {
        this.weatherInfoActivity = weatherInfoActivity;
        this.baseRouter = baseRouter;
    }

    @Override
    public void onDetachView() {
        weatherInfoActivity = null;
        baseRouter = null;

    }

    @Override
    public void getHashModel() {
        weatherInfoActivity.paintWeather(viewCurrentWeatherModel);
    }

    @Override
    public void onDestroy() {
        baseRouter = null;
        weatherInfoInteractor.unRegister();
        weatherInfoInteractor = null;
        weatherInfoActivity = null;
    }

    @Override
    public void onSucces(Object viewModel) {
        viewCurrentWeatherModel = (ViewCurrentWeatherModel) viewModel;
        weatherInfoActivity.paintWeather(viewModel);
    }

    @Override
    public void onError(Exception ex) {

    }

    @Override
    public void onSuccesDeleteItem(List viewModelList) {
        EventBus.getDefault().post(new WeatherDeleteItemEvent(viewModelList));
        baseRouter.closeWeatherInfoActivity();
    }
}
