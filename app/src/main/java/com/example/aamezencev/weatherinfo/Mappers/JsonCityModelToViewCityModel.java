package com.example.aamezencev.weatherinfo.Mappers;

import android.os.AsyncTask;

import com.example.aamezencev.weatherinfo.JsonModels.JsonCityModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewCityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class JsonCityModelToViewCityModel extends AsyncTask<Void, Void, List<ViewCityModel>> {

    private List<JsonCityModel> jsonCityModelList;

    public JsonCityModelToViewCityModel(List<JsonCityModel> jsonCityModelList) {
        this.jsonCityModelList = jsonCityModelList;
    }

    public ViewCityModel map(JsonCityModel jsonCityModel) {
        if (jsonCityModel != null) {
            ViewCityModel viewCityModel = new ViewCityModel();
            viewCityModel.setId(jsonCityModel.getId().toString());
            viewCityModel.setName(jsonCityModel.getName());
            viewCityModel.setCountry(jsonCityModel.getCountry());

            return viewCityModel;

            //viewCityModel.setLon(jsonCityModel.getCoord()[0].toString());
            //viewCityModel.setLat(jsonCityModel.getCoord()[1].toString());
        } else {
            return null;
        }

    }

    @Override
    protected List<ViewCityModel> doInBackground(Void... voids) {

        List<ViewCityModel> viewCityModelList = new ArrayList<>();

        for (JsonCityModel jsonCityModel : jsonCityModelList) {
            ViewCityModel viewCityModel = map(jsonCityModel);
            if (viewCityModel != null) viewCityModelList.add(viewCityModel);
        }

        return viewCityModelList;
    }

}
