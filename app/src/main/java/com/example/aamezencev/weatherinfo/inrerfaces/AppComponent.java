package com.example.aamezencev.weatherinfo.inrerfaces;

import com.example.aamezencev.weatherinfo.AppModule;
import com.example.aamezencev.weatherinfo.InitDb;
import com.example.aamezencev.weatherinfo.UpdateService;
import com.example.aamezencev.weatherinfo.domain.RxDbManager;
import com.example.aamezencev.weatherinfo.domain.RxGoogleApiManager;
import com.example.aamezencev.weatherinfo.domain.RxOWMApiManager;
import com.example.aamezencev.weatherinfo.domain.interactors.MainActivityInteractor;
import com.example.aamezencev.weatherinfo.domain.interactors.WeatherInfoInteractor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by aa.mezencev on 30.01.2018.
 */
@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(RxDbManager dbManager);

    void inject(MainActivityInteractor mainInteractor);

    void inject(WeatherInfoInteractor weatherInfoInteractor);

    void inject(UpdateService updateService);
}
