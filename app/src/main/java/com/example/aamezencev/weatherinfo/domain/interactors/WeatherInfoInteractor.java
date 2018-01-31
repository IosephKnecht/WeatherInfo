package com.example.aamezencev.weatherinfo.domain.interactors;

import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.domain.RxDbManager;
import com.example.aamezencev.weatherinfo.domain.interactors.interfaces.IWeatherInfoInteractor;
import com.example.aamezencev.weatherinfo.view.presenters.IWeatherInfoInteractorOutput;
import com.example.aamezencev.weatherinfo.view.mappers.CurrentWeatherDbModelToView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public class WeatherInfoInteractor implements IWeatherInfoInteractor {

    @Inject
    RxDbManager dbManager;
    private CompositeDisposable compositeDisposable;
    private IWeatherInfoInteractorOutput interactorOutput;

    public WeatherInfoInteractor(IWeatherInfoInteractorOutput interactorOutput) {
        this.interactorOutput = interactorOutput;
        compositeDisposable = new CompositeDisposable();
        this.interactorOutput = interactorOutput;
    }

    @Override
    public void executeCurrentWeather(Long key) {
        App.getAppComponent().inject(this);
        compositeDisposable.add(dbManager.findWeatherByKey(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber -> {
                    CurrentWeatherDbModelToView mapper = new CurrentWeatherDbModelToView(subscriber);
                    interactorOutput.onSucces(mapper.map());
                }));
    }

    @Override
    public void executeDeleteCurrentWeather(Long key) {
        App.getAppComponent().inject(this);
        compositeDisposable.add(dbManager.deleteItemOdDbQuery(key)
                .subscribe());
    }

    @Override
    public void unRegister() {
        dbManager = null;
        compositeDisposable.dispose();
        compositeDisposable = null;
        interactorOutput = null;
    }
}
