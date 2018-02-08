package com.example.aamezencev.weatherinfo.domain;

import android.util.Log;

import com.example.aamezencev.weatherinfo.data.CurrentWeatherDbModel;
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

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aa.mezencev on 08.02.2018.
 */

public class FacadeManager {

    private RxDbManager dbManager;
    private RxGoogleApiManager googleApiManager;
    private RxOWMApiManager owmApiManager;

    public FacadeManager(RxDbManager dbManager, RxGoogleApiManager googleApiManager,
                         RxOWMApiManager owmApiManager) {
        this.dbManager = dbManager;
        this.googleApiManager = googleApiManager;
        this.owmApiManager = owmApiManager;
    }

    public Observable<List<List<CurrentWeatherDbModel>>> getUpdateObservable() {
        return dbManager.clearWeatherTable()
                .flatMap(aBoolean -> dbManager.allItemQuery())
                .flatMap(cities -> io.reactivex.Observable.fromIterable(cities)
                        .flatMap(city -> googleApiManager.geoRequest(city.getPlaceId())
                                .flatMap(geo -> owmApiManager.currentWeatherRequest(geo.getJsonLocationModel().getLat(), geo.getJsonLocationModel().getLng()))
                                .map(jsonModelList -> new JsonWeatherModelToDb(jsonModelList).map())
                                .map(currentWeatherDbModels -> new CreateRealation(city, currentWeatherDbModels).map())
                                .flatMap(currentWeatherDbModels -> dbManager.addListToDbQuery(currentWeatherDbModels))
                                .retryWhen(throwableObservable -> throwableObservable.flatMap(throwable -> {
                                    Log.d("myLog", "retry");
                                    return io.reactivex.Observable.just(cities).delay(120_000, TimeUnit.MILLISECONDS);
                                }))
                        )
                )
                .toList()
                .toObservable()
                .repeatWhen(completed -> completed.delay(60_000, TimeUnit.MILLISECONDS));
    }

    public Observable<List<ViewPromptCityModel>> getPromptAroundCity(String city) {
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

    public Observable insertViewModelsToDb(List viewModelList) {
        return dbManager.addPromptListViewToDb(viewModelList);
    }

    public Observable<List<ViewPromptCityModel>> getAllPrompt() {
        return dbManager.allItemQuery()
                .map(promptCityDbModels -> {
                    PromptCityDbModelToViewPromptCityModel mapper = new PromptCityDbModelToViewPromptCityModel(promptCityDbModels);
                    return mapper.map();
                });
    }

    public Observable<List<PromptCityDbModel>> deletePrompt(Long key) {
        return dbManager.deleteItemOdDbQuery(key);
    }

    public Observable<List<ViewPromptCityModel>> deletePromptAndMap(Long key) {
        return dbManager.deleteItemOdDbQuery(key)
                .map(promptCityDbModels -> new PromptCityDbModelToViewPromptCityModel(promptCityDbModels).map());
    }

    public Observable<List<CurrentWeatherDbModel>> getWeatherListByCity(Long key) {
        return dbManager.findWeatherByKey(key);
    }
}
