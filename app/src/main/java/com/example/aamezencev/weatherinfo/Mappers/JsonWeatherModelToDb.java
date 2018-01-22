package com.example.aamezencev.weatherinfo.Mappers;

import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.JsonModels.OWMApi.JsonWeatherInfo;
import com.example.aamezencev.weatherinfo.JsonModels.OWMApi.JsonWeatherModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 19.01.2018.
 */

public class JsonWeatherModelToDb {
    private List<JsonWeatherModel> jsonWeatherModelList;
    private List<PromptCityDbModel> promptCityDbModelList;

    public JsonWeatherModelToDb(List<JsonWeatherModel> jsonWeatherModelList, List<PromptCityDbModel> promptCityDbModelList) {
        this.jsonWeatherModelList = jsonWeatherModelList;
        this.promptCityDbModelList = promptCityDbModelList;
    }

    public List<CurrentWeatherDbModel> map() {
        List<CurrentWeatherDbModel> currentWeatherDbModelList = new ArrayList<>();
        int i = 0;
        for (JsonWeatherModel jsonModel : jsonWeatherModelList) {
            CurrentWeatherDbModel currentWeatherDbModel = new CurrentWeatherDbModel();
            JsonWeatherInfo jsonWeatherInfo = jsonModel.getJsonWeatherInfoList().get(0);
            currentWeatherDbModel.setKey(promptCityDbModelList.get(i).getKey());
            currentWeatherDbModel.setCod(jsonModel.getCod());
            currentWeatherDbModel.setId(jsonModel.getId());
            currentWeatherDbModel.setDescription(jsonWeatherInfo.getDescription());
            currentWeatherDbModel.setMain(jsonWeatherInfo.getMain());
            currentWeatherDbModel.setIcon(jsonWeatherInfo.getIcon());
            currentWeatherDbModel.setLon(jsonModel.getJsonCoordInfo().getLon());
            currentWeatherDbModel.setLat(jsonModel.getJsonCoordInfo().getLat());
            currentWeatherDbModel.setPressure(jsonModel.getJsonMainInfo().getPressure());
            currentWeatherDbModel.setTemp(jsonModel.getJsonMainInfo().getTemp());
            currentWeatherDbModel.setHumidity(jsonModel.getJsonMainInfo().getHumidity());
            currentWeatherDbModel.setTempMin(jsonModel.getJsonMainInfo().getTempMin());
            currentWeatherDbModel.setTempMax(jsonModel.getJsonMainInfo().getTempMax());
            currentWeatherDbModel.setSpeed(jsonModel.getJsonWindInfo().getSpeed());
            currentWeatherDbModel.setDeg(jsonModel.getJsonWindInfo().getDeg());
            currentWeatherDbModel.setAll(jsonModel.getJsonCloudsInfo().getAll());
            currentWeatherDbModelList.add(currentWeatherDbModel);
            i++;
        }

        return currentWeatherDbModelList;
    }
}
