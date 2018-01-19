package com.example.aamezencev.weatherinfo.Mappers;

import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.JsonModels.OWMApi.JsonWeatherInfo;
import com.example.aamezencev.weatherinfo.JsonModels.OWMApi.JsonWeatherModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 19.01.2018.
 */

public class JsonWeatherModelToDb {
    private List<JsonWeatherModel> jsonWeatherModelList;

    public JsonWeatherModelToDb(List<JsonWeatherModel> jsonWeatherModelList) {
        this.jsonWeatherModelList = jsonWeatherModelList;
    }

    public List<CurrentWeatherDbModel> map() {
        List<CurrentWeatherDbModel> currentWeatherDbModelList = new ArrayList<>();
        for (JsonWeatherModel jsonModel : jsonWeatherModelList) {
            CurrentWeatherDbModel currentWeatherDbModel = new CurrentWeatherDbModel();
            JsonWeatherInfo jsonWeatherInfo = jsonModel.getJsonWeatherInfoList().get(0);
            currentWeatherDbModel.setCod(jsonModel.getCod());
            currentWeatherDbModel.setId(jsonModel.getId());
            currentWeatherDbModel.setDescription(jsonWeatherInfo.getDescription());
            currentWeatherDbModel.setMain(jsonWeatherInfo.getMain());
            currentWeatherDbModel.setIcon(jsonWeatherInfo.getIcon());
            currentWeatherDbModelList.add(currentWeatherDbModel);
        }

        return currentWeatherDbModelList;
    }
}
