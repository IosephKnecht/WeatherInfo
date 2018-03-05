package com.example.aamezencev.weatherinfo.view.presenters;

import com.example.aamezencev.weatherinfo.view.interfaces.IBaseActivity;
import com.example.aamezencev.weatherinfo.view.interfaces.IBasePresenter;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;

import java.util.List;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public interface IMainPresenter<T> extends IBasePresenter {
    void getViewPromptCityModelList(String city);

    void addPromptListViewToDb(List<T> viewModelList);

    void getHashList();

    int isVisibleFloatingButton();

    List<T> selectIsCheckedItem();
}
