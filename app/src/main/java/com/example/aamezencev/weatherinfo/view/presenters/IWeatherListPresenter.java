package com.example.aamezencev.weatherinfo.view.presenters;

import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.interfaces.IWeatherListActivity;

/**
 * Created by aa.mezencev on 01.02.2018.
 */

public interface IWeatherListPresenter {
    void deleteItemAsDb(Long key);

    void getPromptCityDbModelList();

    void getHashList();

    void onAttachView(IWeatherListActivity weatherListActivity, IBaseRouter baseRouter);

    void onDetachView();

    void onDestroy();
}
