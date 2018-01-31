package com.example.aamezencev.weatherinfo.domain.mappers;

import com.example.aamezencev.weatherinfo.data.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.data.PromptCityDbModel;

import java.util.List;

/**
 * Created by aa.mezencev on 31.01.2018.
 */

public class CreateRealation {
    private List<PromptCityDbModel> promptCityDbModelList;
    private List<CurrentWeatherDbModel> currentWeatherDbModelList;

    public CreateRealation(List<PromptCityDbModel> promptCityDbModelList,
                           List<CurrentWeatherDbModel> currentWeatherDbModelList) {
        this.promptCityDbModelList = promptCityDbModelList;
        this.currentWeatherDbModelList = currentWeatherDbModelList;
    }

    public List<PromptCityDbModel> map() {
        for (int i = 0; i < promptCityDbModelList.size(); i++) {
            promptCityDbModelList.get(i).setWeatherDbModel(currentWeatherDbModelList.get(i));
            promptCityDbModelList.get(i).setRelationKey(currentWeatherDbModelList.get(i).getKey());
        }
        return promptCityDbModelList;
    }
}
