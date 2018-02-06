package com.example.aamezencev.weatherinfo.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.data.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.data.googleApi.JsonPromptModel;
import com.example.aamezencev.weatherinfo.domain.mappers.CreateRealation;
import com.example.aamezencev.weatherinfo.domain.mappers.JsonWeatherModelToDb;
import com.example.aamezencev.weatherinfo.view.mappers.JsonPromptModelToViewPromptModel;
import com.example.aamezencev.weatherinfo.view.mappers.PromptCityDbModelToViewPromptCityModel;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aa.mezencev on 06.02.2018.
 */

public class FacadeManager {
    @Inject
    RxDbManager dbManager;
    @Inject
    RxGoogleApiManager googleApiManager;
    @Inject
    RxOWMApiManager owmApiManager;
    @Inject
    Context context;

    public FacadeManager() {
        App.getAppComponent().inject(this);
    }

    public Observable<List<PromptCityDbModel>> getUpdateObservable() {
        String stringServiceDelay = PreferenceManager.getDefaultSharedPreferences(context).getString("etDelayService", "60_000");
        if (Long.valueOf(stringServiceDelay) < 60_000) stringServiceDelay = "60_000";
        long serviceDelay = Long.valueOf(stringServiceDelay);
        return dbManager.allItemQuery()
                .flatMap(cities -> io.reactivex.Observable.fromIterable(cities)
                        .flatMap(city -> googleApiManager.geoRequest(city.getPlaceId()))
                        .flatMap(geo -> owmApiManager.currentWeatherRequest(geo.getJsonLocationModel().getLat(), geo.getJsonLocationModel().getLng()))
                        .toList()
                        .map(weatherModels -> new JsonWeatherModelToDb(weatherModels).map())
                        .toObservable()
                        .flatMap(aVoid -> dbManager.addListToDbQuery(aVoid))
                        .map(currentWeatherDbModels -> new CreateRealation(cities, currentWeatherDbModels).map())
                        .flatMap(promptCityDbModels -> dbManager.addPromptListToDb(promptCityDbModels))
                        .retryWhen(throwableObservable -> throwableObservable.flatMap(error -> {
                            Log.d("myLog", "retry");
                            return io.reactivex.Observable.just(cities).delay(120_000, TimeUnit.MILLISECONDS);
                        }))
                )
                .repeatWhen(completed -> completed.delay(serviceDelay, TimeUnit.MILLISECONDS));
    }

    public Observable<List<ViewPromptCityModel>> getViewModelList() {
        return dbManager.allItemQuery()
                .map(promptCityDbModels -> {
                    PromptCityDbModelToViewPromptCityModel mapper = new PromptCityDbModelToViewPromptCityModel(promptCityDbModels);
                    List<ViewPromptCityModel> viewPromptCityModelList = mapper.map();
                    return viewPromptCityModelList;
                });
    }

    public Observable<List<ViewPromptCityModel>> getWeatherAroundCity(String city) {
        return googleApiManager.promptRequest(city)
                .map(response -> {
                    String jsonString = response.body().string();
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonPromptModel>() {
                    }.getType();
                    JsonPromptModel jsonPromptModel = gson.fromJson(jsonString, type);
                    return jsonPromptModel;
                })
                .map(jsonPromptModel -> {
                    JsonPromptModelToViewPromptModel mapper = new JsonPromptModelToViewPromptModel(jsonPromptModel);
                    ViewPromptModel viewPromptModel = mapper.map();
                    return viewPromptModel.getViewPromptCityModelList();
                });
    }
}
