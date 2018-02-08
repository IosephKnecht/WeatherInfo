package com.example.aamezencev.weatherinfo.view.presenters;

import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.interfaces.IWeatherInfoActivity;

import java.util.List;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public interface IWeatherInfoPresenter<T> {
    void getCurrentWeather(Long key);

    void deleteCurrentWeather(Long key);

    void onAttachView(IWeatherInfoActivity weatherInfoActivity, IBaseRouter baseRouter);

    void onDetachView();

    List<T> getViewModelList();

    void onDestroy();
}
