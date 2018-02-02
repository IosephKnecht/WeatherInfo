package com.example.aamezencev.weatherinfo.view.presenters;

import android.content.Context;
import android.content.Loader;
import android.view.View;

import com.example.aamezencev.weatherinfo.domain.interactors.MainActivityInteractor;
import com.example.aamezencev.weatherinfo.view.Router;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseActivity;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.domain.interactors.interfaces.IMainInteractor;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public class MainActivityPresenter implements IMainInteractorOutput, IMainPresenter {

    private IBaseActivity baseActivity;
    private IMainInteractor mainInteractor;
    private IBaseRouter baseRouter;

    public MainActivityPresenter() {
        mainInteractor = new MainActivityInteractor(this);
    }

    private List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();

    @Override
    public void getViewPromptCityModelList(String city) {
        mainInteractor.onGetViewPromptCityModelList(city);
    }

    @Override
    public void addPromptListViewToDb(List viewModelList) {
        mainInteractor.onAddPromptListViewToDb(viewModelList);
    }

    @Override
    public void onDestroy() {
        baseActivity = null;
        mainInteractor.unRegister();
        mainInteractor = null;
        baseRouter = null;
    }

    @Override
    public void getHashList() {
        baseActivity.paintList(viewPromptCityModelList);
    }

    @Override
    public void onViewAttach(IBaseActivity baseActivity, IBaseRouter baseRouter) {
        this.baseActivity = baseActivity;
        this.baseRouter = baseRouter;
    }

    @Override
    public void onViewDetach() {
        this.baseActivity = null;
        this.baseRouter = null;
    }

    @Override
    public int isVisibleFloatingButton() {
        for (ViewPromptCityModel viewPromptCityModel : viewPromptCityModelList) {
            if (viewPromptCityModel.isChecked()) return View.VISIBLE;
        }
        return View.INVISIBLE;
    }

    @Override
    public void OnSucces(List viewModelList) {
        this.viewPromptCityModelList = viewModelList;
        baseActivity.paintList(viewModelList);
    }

    @Override
    public void onError(Throwable ex) {
        baseRouter.showError(ex);
    }


    @Override
    public List selectIsCheckedItem() {
        List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();
        for (ViewPromptCityModel viewPromptCityModel : this.viewPromptCityModelList) {
            if (viewPromptCityModel.isChecked()) viewPromptCityModelList.add(viewPromptCityModel);
        }
        return viewPromptCityModelList;
    }

}
