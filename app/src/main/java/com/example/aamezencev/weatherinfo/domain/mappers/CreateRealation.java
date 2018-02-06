package com.example.aamezencev.weatherinfo.domain.mappers;

import com.example.aamezencev.weatherinfo.data.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.data.PromptCityDbModel;

import java.util.List;

/**
 * Created by aa.mezencev on 31.01.2018.
 */

public class CreateRealation {
    private PromptCityDbModel promptCityDbModel;
    private List<CurrentWeatherDbModel> currentWeatherDbModelList;

    public CreateRealation(PromptCityDbModel promptCityDbModel,
                           List<CurrentWeatherDbModel> currentWeatherDbModelList) {
        this.promptCityDbModel = promptCityDbModel;
        this.currentWeatherDbModelList = currentWeatherDbModelList;
    }

    public List<CurrentWeatherDbModel> map() {
        for (CurrentWeatherDbModel weatherDbModel : currentWeatherDbModelList) {
            weatherDbModel.setForeignKey(promptCityDbModel.getKey());
        }
        return currentWeatherDbModelList;
    }
}
