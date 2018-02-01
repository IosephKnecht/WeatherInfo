package com.example.aamezencev.weatherinfo.domain.interactors.interfaces;

import java.util.List;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public interface IMainInteractor<T> {
    void onGetViewPromptCityModelList(String city);

    void onAddPromptListViewToDb(List<T> viewModelList);

    void onGetPromptCityDbModelList();

    void onDeleteItemAsDb(Long key);

    void unRegister();
}
