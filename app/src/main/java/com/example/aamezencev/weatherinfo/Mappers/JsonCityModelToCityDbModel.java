package com.example.aamezencev.weatherinfo.Mappers;

import android.content.Context;
import android.os.AsyncTask;

import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.DaoModels.CityDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.CityDbModelDao;
import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;
import com.example.aamezencev.weatherinfo.JsonModels.JsonCityModel;

import java.util.List;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class JsonCityModelToCityDbModel extends AsyncTask<Void, Void, Void> {

    private List<JsonCityModel> jsonCityModelList;
    private DaoSession daoSession;

    public JsonCityModelToCityDbModel(List<JsonCityModel> jsonCityModelList, DaoSession daoSession) {
        this.jsonCityModelList = jsonCityModelList;
        this.daoSession = daoSession;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        CityDbModelDao cityDbModelDao = daoSession.getCityDbModelDao();
        cityDbModelDao.deleteAll();

        for (JsonCityModel jsonCityModel : jsonCityModelList) {
            CityDbModel cityDbModel = map(jsonCityModel);

            try {
                cityDbModelDao.insert(cityDbModel);
            }
            catch (Exception ex){

            }
        }

        return null;
    }

    public CityDbModel map(JsonCityModel jsonCityModel) {
        CityDbModel cityDbModel = new CityDbModel();

        if (jsonCityModel != null) {
            cityDbModel.setId(jsonCityModel.getId());
            cityDbModel.setName(jsonCityModel.getName());
            cityDbModel.setCountry(jsonCityModel.getCountry());
        }

        return cityDbModel;
    }
}
