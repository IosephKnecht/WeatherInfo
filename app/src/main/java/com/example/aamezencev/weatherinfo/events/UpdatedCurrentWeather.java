package com.example.aamezencev.weatherinfo.events;

import java.util.List;

/**
 * Created by aa.mezencev on 31.01.2018.
 */

public class UpdatedCurrentWeather<T> {
    private List<T> viewModelList;

    public UpdatedCurrentWeather(List<T> viewModelList) {
        this.viewModelList = viewModelList;
    }


    public List<T> getViewModelList() {
        return viewModelList;
    }
}
