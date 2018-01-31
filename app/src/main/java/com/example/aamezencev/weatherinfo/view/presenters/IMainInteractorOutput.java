package com.example.aamezencev.weatherinfo.view.presenters;

import java.util.List;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public interface IMainInteractorOutput<T> {
    void OnSucces(List<T> viewModelList);

    void onError(Exception ex);
}
