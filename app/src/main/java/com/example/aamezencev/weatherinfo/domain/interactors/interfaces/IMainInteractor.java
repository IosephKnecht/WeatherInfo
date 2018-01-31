package com.example.aamezencev.weatherinfo.domain.interactors.interfaces;

import java.util.List;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public interface IMainInteractor<T> {
    void execute(String city);

    void executeList(List<T> viewModelList);

    void executeDbList();

    void executeDelete(Long key);

    void unRegister();
}
