package com.example.aamezencev.weatherinfo.Mappers;

import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewCurrentWeatherModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;

import java.util.List;

/**
 * Created by aa.mezencev on 19.01.2018.
 */

public class CurrentWeatherDbModelToView {
    private CurrentWeatherDbModel currentWeatherDbModel;

    public CurrentWeatherDbModelToView(CurrentWeatherDbModel currentWeatherDbModel) {
        this.currentWeatherDbModel = currentWeatherDbModel;
    }

    public ViewCurrentWeatherModel map() {
        ViewCurrentWeatherModel viewCurrentWeatherModel = new ViewCurrentWeatherModel();
        if (currentWeatherDbModel != null) {
            viewCurrentWeatherModel.setCod(currentWeatherDbModel.getCod());
            viewCurrentWeatherModel.setDescription(currentWeatherDbModel.getDescription());
            viewCurrentWeatherModel.setIcon(currentWeatherDbModel.getIcon());
            viewCurrentWeatherModel.setId(currentWeatherDbModel.getId());
            viewCurrentWeatherModel.setMain(currentWeatherDbModel.getMain());
        }
        else {
            viewCurrentWeatherModel.setCod("1");
            viewCurrentWeatherModel.setDescription("1");
            viewCurrentWeatherModel.setIcon("02d");
            viewCurrentWeatherModel.setId((long)1);
            viewCurrentWeatherModel.setMain("1");
        }
        return viewCurrentWeatherModel;
    }
}
