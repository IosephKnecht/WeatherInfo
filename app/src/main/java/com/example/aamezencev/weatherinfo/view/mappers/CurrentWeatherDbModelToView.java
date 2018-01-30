package com.example.aamezencev.weatherinfo.view.mappers;

import com.example.aamezencev.weatherinfo.data.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewCurrentWeatherModel;

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
            viewCurrentWeatherModel.setLon(currentWeatherDbModel.getLon());
            viewCurrentWeatherModel.setLat(currentWeatherDbModel.getLat());
            viewCurrentWeatherModel.setPressure(currentWeatherDbModel.getPressure());
            viewCurrentWeatherModel.setTemp(currentWeatherDbModel.getTemp());
            viewCurrentWeatherModel.setHumidity(currentWeatherDbModel.getHumidity());
            viewCurrentWeatherModel.setTempMin(currentWeatherDbModel.getTempMin());
            viewCurrentWeatherModel.setTempMax(currentWeatherDbModel.getTempMax());
            viewCurrentWeatherModel.setSpeed(currentWeatherDbModel.getSpeed());
            viewCurrentWeatherModel.setDeg(currentWeatherDbModel.getDeg());
            viewCurrentWeatherModel.setAll(currentWeatherDbModel.getAll());

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
