package com.example.aamezencev.weatherinfo.view.presenters;

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
}
