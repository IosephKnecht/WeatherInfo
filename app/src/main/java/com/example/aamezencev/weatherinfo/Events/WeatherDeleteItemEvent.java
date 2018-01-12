package com.example.aamezencev.weatherinfo.Events;

import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewCityModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;

import java.util.List;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class WeatherDeleteItemEvent {
    private List<ViewPromptCityModel> viewCityModelList;
    private List<PromptCityDbModel> promptCityDbModelList;

    public WeatherDeleteItemEvent(List<ViewPromptCityModel> viewCityModelList,
                                  List<PromptCityDbModel> promptCityDbModelList) {
        this.viewCityModelList = viewCityModelList;
        this.promptCityDbModelList = promptCityDbModelList;
    }

    public List<ViewPromptCityModel> getViewCityModelList() {
        return viewCityModelList;
    }

    public List<PromptCityDbModel> getPromptCityDbModelList() {
        return promptCityDbModelList;
    }
}
