package com.example.aamezencev.weatherinfo.view.presenters;

import com.example.aamezencev.weatherinfo.view.interfaces.IBaseActivity;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;

import java.util.List;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public interface IMainPresenter<T> {
    void getViewPromptCityModelList(String city);

    void addPromptListViewToDb(List<T> viewModelList);

    void getPromptCityDbModelList();

    void deleteItemAsDb(Long key);

    void onDestroy();

    void getHashList();

    void onViewAttach(IBaseActivity baseActivity, IBaseRouter baseRouter);

    void onViewDetach();

    int isVisibleFloatingButton();

    List<T> selectIsCheckedItem();
}
