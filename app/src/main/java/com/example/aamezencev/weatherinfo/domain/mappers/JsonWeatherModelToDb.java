package com.example.aamezencev.weatherinfo.domain.mappers;

import com.example.aamezencev.weatherinfo.data.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.data.owmApi.JsonWeatherInfo;
import com.example.aamezencev.weatherinfo.data.owmApi.JsonWeatherModel;
import com.example.aamezencev.weatherinfo.data.PromptCityDbModel;

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
            currentWeatherDbModel.setDescription(jsonWeatherInfo.getDescription());
            currentWeatherDbModel.setMain(jsonWeatherInfo.getMain());
            currentWeatherDbModel.setIcon(jsonWeatherInfo.getIcon());
            currentWeatherDbModel.setPressure(jsonModel.getJsonMainInfo().getPressure());
            currentWeatherDbModel.setTemp(jsonModel.getJsonMainInfo().getTemp());
            currentWeatherDbModel.setHumidity(jsonModel.getJsonMainInfo().getHumidity());
            currentWeatherDbModel.setTempMin(jsonModel.getJsonMainInfo().getTempMin());
            currentWeatherDbModel.setTempMax(jsonModel.getJsonMainInfo().getTempMax());
            currentWeatherDbModel.setSpeed(jsonModel.getJsonWindInfo().getSpeed());
            currentWeatherDbModel.setDeg(jsonModel.getJsonWindInfo().getDeg());
            currentWeatherDbModel.setAll(jsonModel.getJsonCloudsInfo().getAll());
            currentWeatherDbModel.setDate(jsonModel.getDate());
            currentWeatherDbModelList.add(currentWeatherDbModel);
        }

        return currentWeatherDbModelList;
    }
}
