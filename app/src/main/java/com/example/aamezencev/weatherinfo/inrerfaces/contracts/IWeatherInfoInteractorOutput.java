package com.example.aamezencev.weatherinfo.inrerfaces.contracts;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public interface IWeatherInfoInteractorOutput<T> {
    void onSucces(T viewModel);

    void onError(Exception ex);
}
