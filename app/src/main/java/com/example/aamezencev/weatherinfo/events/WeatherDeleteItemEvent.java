package com.example.aamezencev.weatherinfo.events;

import com.example.aamezencev.weatherinfo.data.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

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
