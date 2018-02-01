package com.example.aamezencev.weatherinfo.view.presenters;

import com.example.aamezencev.weatherinfo.domain.interactors.MainActivityInteractor;
import com.example.aamezencev.weatherinfo.domain.interactors.interfaces.IMainInteractor;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.interfaces.IWeatherListActivity;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 01.02.2018.
 */

public class WeatherListPresenter implements IWeatherListPresenter, IMainInteractorOutput {

    private IWeatherListActivity weatherListActivity;
    private IMainInteractor mainInteractor;
    private IBaseRouter baseRouter;

    private List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();

    public WeatherListPresenter(IWeatherListActivity weatherListActivity, IBaseRouter baseRouter) {
        this.weatherListActivity = weatherListActivity;
        this.baseRouter = baseRouter;
        mainInteractor = new MainActivityInteractor(this);
    }

    @Override
    public void deleteItemAsDb(Long key) {
        mainInteractor.onDeleteItemAsDb(key);
    }

    @Override
    public void getPromptCityDbModelList() {
        mainInteractor.onGetPromptCityDbModelList();
    }

    @Override
    public void getHashList() {
        weatherListActivity.paintList(viewPromptCityModelList);
    }

    @Override
    public void onAttachView(IWeatherListActivity weatherListActivity) {
        this.weatherListActivity = weatherListActivity;
        this.mainInteractor = new MainActivityInteractor(this);
    }

    @Override
    public void onDetachView() {
        this.weatherListActivity = null;
        this.mainInteractor = null;
    }

    @Override
    public void onDestroy() {
        weatherListActivity = null;
        mainInteractor.unRegister();
        baseRouter = null;
    }

    @Override
    public void OnSucces(List viewModelList) {
        viewPromptCityModelList = viewModelList;
        weatherListActivity.paintList(viewModelList);
    }

    @Override
    public void onError(Exception ex) {

    }
}
