package com.example.aamezencev.weatherinfo.Mappers;

import android.os.AsyncTask;

import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityStructureFormattingDbModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityStructureFormatting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class PromptCityDbModelToViewPromptCityModel {
    List<PromptCityDbModel> promptCityDbModelList;

    public PromptCityDbModelToViewPromptCityModel(List<PromptCityDbModel> promptCityDbModelList) {
        this.promptCityDbModelList = promptCityDbModelList;
    }

    public List<ViewPromptCityModel> map() {
        List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();
        for (PromptCityDbModel promptCityDbModel:promptCityDbModelList) {
            ViewPromptCityModel viewPromptCityModel=new ViewPromptCityModel();
            viewPromptCityModel.setId(promptCityDbModel.getId());
            viewPromptCityModel.setDescription(promptCityDbModel.getDescription());
            viewPromptCityModel.setStructuredFormatting(mapStructureFormatting(promptCityDbModel.getStructuredFormatting()));
            viewPromptCityModel.setPlaceId(promptCityDbModel.getPlaceId());
            viewPromptCityModelList.add(viewPromptCityModel);
        }
        return viewPromptCityModelList;
    }

    private ViewPromptCityStructureFormatting mapStructureFormatting(PromptCityStructureFormattingDbModel structureFormattingDbModel){
        ViewPromptCityStructureFormatting structureFormatting=new ViewPromptCityStructureFormatting();
        structureFormatting.setMainText(structureFormattingDbModel.getMainText());
        structureFormatting.setSecondaryText(structureFormattingDbModel.getSecondaryText());
        return structureFormatting;
    }
}
