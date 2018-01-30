package com.example.aamezencev.weatherinfo.view.mappers;

import com.example.aamezencev.weatherinfo.data.googleApi.JsonPromptCityModel;
import com.example.aamezencev.weatherinfo.data.googleApi.JsonPromptCityStructureFormatting;
import com.example.aamezencev.weatherinfo.data.googleApi.JsonPromptModel;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityStructureFormatting;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptModel;

import java.util.ArrayList;
import java.util.List;

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
            viewPromptCityModel.setPlaceId(jsonPromptCityModel.getPlaceId());
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
