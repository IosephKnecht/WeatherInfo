package com.example.aamezencev.weatherinfo.Events;

import com.example.aamezencev.weatherinfo.ViewModels.ViewCityModel;

import java.util.List;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class CityEvent {

    private List<ViewCityModel> viewCityModelList;

    public CityEvent(List<ViewCityModel> viewCityModelList) {
        this.viewCityModelList = viewCityModelList;
    }

    public List<ViewCityModel> getViewCityModelList() {
        return viewCityModelList;
    }
}
