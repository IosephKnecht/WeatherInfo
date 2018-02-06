package com.example.aamezencev.weatherinfo.domain.interactors;

import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.data.googleApi.JsonPromptModel;
import com.example.aamezencev.weatherinfo.domain.FacadeManager;
import com.example.aamezencev.weatherinfo.domain.RxDbManager;
import com.example.aamezencev.weatherinfo.domain.RxGoogleApiManager;
import com.example.aamezencev.weatherinfo.domain.interactors.interfaces.IMainInteractor;
import com.example.aamezencev.weatherinfo.view.presenters.IMainInteractorOutput;
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
    @Inject FacadeManager facadeManager;
    @Inject RxDbManager dbManager;

    public MainActivityInteractor(IMainInteractorOutput mainInteractorOutput) {
        this.mainInteractorOutput = mainInteractorOutput;
        this.compositeDisposable = new CompositeDisposable();
        facadeManager = new FacadeManager();
        App.getAppComponent().inject(this);

    }

    @Override
    public void onGetViewPromptCityModelList(String city) {
        compositeDisposable.add(facadeManager.getWeatherAroundCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewPromptCityModelList -> mainInteractorOutput.OnSucces(viewPromptCityModelList),
                        error -> mainInteractorOutput.onError(error)));
    }

    @Override
    public void onAddPromptListViewToDb(List viewModelList) {
        compositeDisposable.add(dbManager.addPromptListViewToDb(viewModelList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    @Override
    public void onGetPromptCityDbModelList() {
        compositeDisposable.add(
                facadeManager.getViewModelList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(viewPromptCityModels -> mainInteractorOutput.OnSucces(viewPromptCityModels))
        );
    }

    @Override
    public void onDeleteItemAsDb(Long key) {
        compositeDisposable.add(dbManager.deleteItemOdDbQuery(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    PromptCityDbModelToViewPromptCityModel mapper = new PromptCityDbModelToViewPromptCityModel(aVoid);
                    mainInteractorOutput.OnSucces(mapper.map());
                }));
    }

    @Override
    public void unRegister() {
        mainInteractorOutput = null;
        compositeDisposable.dispose();
        compositeDisposable = null;
        dbManager = null;
        facadeManager = null;
    }

}
