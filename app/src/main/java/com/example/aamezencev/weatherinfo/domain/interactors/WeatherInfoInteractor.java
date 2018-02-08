package com.example.aamezencev.weatherinfo.domain.interactors;

import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.domain.FacadeManager;
import com.example.aamezencev.weatherinfo.domain.RxDbManager;
import com.example.aamezencev.weatherinfo.domain.interactors.interfaces.IWeatherInfoInteractor;
import com.example.aamezencev.weatherinfo.view.mappers.CurrentWeatherDbModelToView;
import com.example.aamezencev.weatherinfo.view.mappers.PromptCityDbModelToViewPromptCityModel;
import com.example.aamezencev.weatherinfo.view.presenters.IWeatherInfoInteractorOutput;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public class WeatherInfoInteractor implements IWeatherInfoInteractor {

    @Inject
    FacadeManager facadeManager;
    private CompositeDisposable compositeDisposable;
    private IWeatherInfoInteractorOutput interactorOutput;

    public WeatherInfoInteractor(IWeatherInfoInteractorOutput interactorOutput) {
        this.interactorOutput = interactorOutput;
        compositeDisposable = new CompositeDisposable();
        this.interactorOutput = interactorOutput;
        App.getAppComponent().inject(this);
    }

    @Override
    public void onGetCurrentWeather(Long key) {
        compositeDisposable.add(facadeManager
                .getWeatherListByCity(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber -> {
                    CurrentWeatherDbModelToView mapper = new CurrentWeatherDbModelToView(subscriber);
                    interactorOutput.onSucces(mapper.map());
                }, error -> interactorOutput.onError(error)));
    }

    @Override
    public void onDeleteCurrentWeather(Long key) {
        compositeDisposable.add(facadeManager
                .deletePromptAndMap(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewPromptCityModels -> interactorOutput.onSuccesDeleteItem(viewPromptCityModels),
                        error -> interactorOutput.onError(error)));
    }

    @Override
    public void unRegister() {
        compositeDisposable.dispose();
        compositeDisposable = null;
        interactorOutput = null;
        facadeManager = null;
    }
}
