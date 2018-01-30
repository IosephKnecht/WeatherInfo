package com.example.aamezencev.weatherinfo.domain.interactors;

import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.data.googleApi.JsonPromptModel;
import com.example.aamezencev.weatherinfo.domain.RxDbManager;
import com.example.aamezencev.weatherinfo.domain.RxGoogleApiManager;
import com.example.aamezencev.weatherinfo.inrerfaces.interactors.IMainInteractor;
import com.example.aamezencev.weatherinfo.inrerfaces.contracts.IMainInteractorOutput;
import com.example.aamezencev.weatherinfo.view.mappers.JsonPromptModelToViewPromptModel;
import com.example.aamezencev.weatherinfo.view.mappers.PromptCityDbModelToViewPromptCityModel;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public class MainActivityInteractor implements IMainInteractor {

    private IMainInteractorOutput mainInteractorOutput;
    private CompositeDisposable compositeDisposable;
    @Inject
    RxDbManager dbManager;
    @Inject
    RxGoogleApiManager googleApiManager;

    public MainActivityInteractor(IMainInteractorOutput mainInteractorOutput) {
        this.mainInteractorOutput = mainInteractorOutput;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void execute(String city) {
        App.getAppComponent().inject(this);
        compositeDisposable.add(googleApiManager.promptRequest(city)
                .subscribeOn(Schedulers.io())
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
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewPromptCityModelList -> {
                    mainInteractorOutput.OnSucces(viewPromptCityModelList);
                }));
    }

    @Override
    public void executeList(List viewModelList) {
        App.getAppComponent().inject(this);
        compositeDisposable.add(dbManager.addPromptListViewToDb(viewModelList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    @Override
    public void executeDbList() {
        App.getAppComponent().inject(this);
        compositeDisposable.add(
                dbManager.allItemQuery()
                        .subscribeOn(Schedulers.io())
                        .map(promptCityDbModels -> {
                            PromptCityDbModelToViewPromptCityModel mapper = new PromptCityDbModelToViewPromptCityModel(promptCityDbModels);
                            List<ViewPromptCityModel> viewPromptCityModelList = mapper.map();
                            return viewPromptCityModelList;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(viewPromptCityModels -> {
                            mainInteractorOutput.OnSucces(viewPromptCityModels);
                        })
        );
    }

    @Override
    public void executeDelete(Long key) {
        App.getAppComponent().inject(this);
        compositeDisposable.add(dbManager.deleteItemOdDbQuery(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    PromptCityDbModelToViewPromptCityModel mapper = new PromptCityDbModelToViewPromptCityModel(aVoid);
                    //WeatherDeleteItemEvent weatherDeleteItemEvent = new WeatherDeleteItemEvent(mapper.map(), aVoid);
                    mainInteractorOutput.OnSucces(mapper.map());
                }));
    }

    @Override
    public void unRegister() {
        mainInteractorOutput = null;
        compositeDisposable.dispose();
        compositeDisposable = null;
        dbManager = null;
        googleApiManager = null;
    }

}
