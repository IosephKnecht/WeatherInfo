package com.example.aamezencev.weatherinfo.view.mappers;

import com.example.aamezencev.weatherinfo.data.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewCurrentWeatherModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 19.01.2018.
 */

public class CurrentWeatherDbModelToView {
    private List<CurrentWeatherDbModel> currentWeatherDbModelList;

    public CurrentWeatherDbModelToView(List<CurrentWeatherDbModel> currentWeatherDbModelList) {
        this.currentWeatherDbModelList = currentWeatherDbModelList;
    }


    public List<ViewCurrentWeatherModel> map() {
        List<ViewCurrentWeatherModel> viewCurrentWeatherModelList = new ArrayList<>();
        for (CurrentWeatherDbModel currentWeatherDbModel : currentWeatherDbModelList) {
            ViewCurrentWeatherModel viewCurrentWeatherModel = new ViewCurrentWeatherModel();
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
            viewCurrentWeatherModel.setDate(currentWeatherDbModel.getDate());
            viewCurrentWeatherModelList.add(viewCurrentWeatherModel);
        }
        return viewCurrentWeatherModelList;
    }
}
