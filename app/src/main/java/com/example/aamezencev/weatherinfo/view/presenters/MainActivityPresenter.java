package com.example.aamezencev.weatherinfo.view.presenters;

import com.example.aamezencev.weatherinfo.domain.interactors.MainActivityInteractor;
import com.example.aamezencev.weatherinfo.inrerfaces.view.IBaseActivity;
import com.example.aamezencev.weatherinfo.inrerfaces.view.IBaseRouter;
import com.example.aamezencev.weatherinfo.inrerfaces.interactors.IMainInteractor;
import com.example.aamezencev.weatherinfo.inrerfaces.contracts.IMainInteractorOutput;
import com.example.aamezencev.weatherinfo.inrerfaces.view.IMainPresenter;

import java.util.List;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public class MainActivityPresenter implements IMainInteractorOutput, IMainPresenter {

    private IBaseActivity baseActivity;
    private IMainInteractor mainInteractor;
    private IBaseRouter baseRouter;

    public MainActivityPresenter(IBaseActivity baseActivity, IBaseRouter baseRouter) {
        this.baseActivity = baseActivity;
        this.baseRouter = baseRouter;
        this.mainInteractor = new MainActivityInteractor(this);
    }

    public void getViewPromptCityModelList(String city) {
        mainInteractor.execute(city);
    }

    @Override
    public void addPromptListViewToDb(List viewModelList) {
        mainInteractor.executeList(viewModelList);
    }

    @Override
    public void getPromptCityDbModelList() {
        mainInteractor.executeDbList();
    }

    @Override
    public void deleteItemAsDb(Long key) {
        mainInteractor.executeDelete(key);
    }

    @Override
    public void onDestroy() {
        baseActivity = null;
        mainInteractor.unRegister();
        mainInteractor = null;
        baseRouter = null;
    }

    @Override
    public void OnSucces(List viewModelList) {
        baseActivity.paintList(viewModelList);
    }

    @Override
    public void onError(Exception ex) {

    }
}
