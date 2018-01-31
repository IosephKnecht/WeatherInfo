package com.example.aamezencev.weatherinfo.events;

import com.example.aamezencev.weatherinfo.data.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

import java.util.List;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class WeatherDeleteItemEvent {
    private List<ViewPromptCityModel> viewPromptCityModelList;

    public WeatherDeleteItemEvent(List<ViewPromptCityModel> viewPromptCityModelList) {
        this.viewPromptCityModelList = viewPromptCityModelList;
    }

    public List<ViewPromptCityModel> getViewCityModelList() {
        return viewPromptCityModelList;
    }

}
