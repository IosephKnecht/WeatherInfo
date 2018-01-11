package com.example.aamezencev.weatherinfo.Mappers;

import android.os.AsyncTask;

import com.example.aamezencev.weatherinfo.JsonModels.JsonCityModel;
import com.example.aamezencev.weatherinfo.JsonModels.JsonPromptCityModel;
import com.example.aamezencev.weatherinfo.JsonModels.JsonPromptCityStructureFormatting;
import com.example.aamezencev.weatherinfo.JsonModels.JsonPromptModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityStructureFormatting;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by aa.mezencev on 11.01.2018.
 */

public class JsonPromptModelToViewPromptModel {
    private JsonPromptModel jsonPromptModel;

    public JsonPromptModelToViewPromptModel(JsonPromptModel jsonPromptModel) {
        this.jsonPromptModel = jsonPromptModel;
    }

    public ViewPromptModel map(){
        ViewPromptModel viewPromptModel=new ViewPromptModel();
        viewPromptModel.setViewPromptCityModelList(mapCity(jsonPromptModel.getJsonPromptCityModels()));
        viewPromptModel.setStatus(jsonPromptModel.getStatus());

        return viewPromptModel;
    }

    private List<ViewPromptCityModel> mapCity(List<JsonPromptCityModel> jsonPromptCityModelList){
        List<ViewPromptCityModel> viewPromptCityModelList=new ArrayList<>();
        for (JsonPromptCityModel jsonPromptCityModel:jsonPromptCityModelList) {
            ViewPromptCityModel viewPromptCityModel=new ViewPromptCityModel();
            viewPromptCityModel.setId(jsonPromptCityModel.getId());
            viewPromptCityModel.setDescription(jsonPromptCityModel.getDescription());
            viewPromptCityModel.setStructuredFormatting(mapStructureFormatting(jsonPromptCityModel.getStructuredFormatting()));
            viewPromptCityModelList.add(viewPromptCityModel);
        }

        return viewPromptCityModelList;
    }

    private ViewPromptCityStructureFormatting mapStructureFormatting(JsonPromptCityStructureFormatting jsonPromptCityStructureFormatting){
        ViewPromptCityStructureFormatting viewPromptCityStructureFormatting=new ViewPromptCityStructureFormatting();
        viewPromptCityStructureFormatting.setSecondaryText(jsonPromptCityStructureFormatting.getSecondaryText());
        viewPromptCityStructureFormatting.setMainText(jsonPromptCityStructureFormatting.getMainText());
        return viewPromptCityStructureFormatting;
    }
}
