package com.example.aamezencev.weatherinfo.view.presenters;

import java.util.List;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public interface IWeatherInfoInteractorOutput<T> {
    void onSucces(T viewModel);

    void onError(Throwable ex);

    void onSuccesDeleteItem(List<T> viewModelList);
}
