package com.example.aamezencev.weatherinfo.Events;

import com.example.aamezencev.weatherinfo.ViewModels.ViewCityModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;

import java.util.List;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class CityEvent {

    private List<ViewPromptCityModel> viewPromptCityModelList;

    public CityEvent(List<ViewPromptCityModel> viewPromptCityModelList) {
        this.viewPromptCityModelList = viewPromptCityModelList;
    }

    public List<ViewPromptCityModel> getViewPromptCityModelList() {
        return viewPromptCityModelList;
    }
}
