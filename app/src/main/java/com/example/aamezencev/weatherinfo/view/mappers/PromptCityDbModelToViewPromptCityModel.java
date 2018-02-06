package com.example.aamezencev.weatherinfo.view.mappers;

import com.example.aamezencev.weatherinfo.data.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.data.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityStructureFormatting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class PromptCityDbModelToViewPromptCityModel {
    private List<PromptCityDbModel> promptCityDbModelList;

    public PromptCityDbModelToViewPromptCityModel(List<PromptCityDbModel> promptCityDbModelList) {
        this.promptCityDbModelList = promptCityDbModelList;
    }

    public List<ViewPromptCityModel> map() {
        List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();
        for (PromptCityDbModel promptCityDbModel : promptCityDbModelList) {
            ViewPromptCityModel viewPromptCityModel = new ViewPromptCityModel();
            viewPromptCityModel.setId(promptCityDbModel.getId());
            viewPromptCityModel.setDescription(promptCityDbModel.getDescription());
            viewPromptCityModel.setStructuredFormatting(mapStructureFormatting(promptCityDbModel));
            viewPromptCityModel.setPlaceId(promptCityDbModel.getPlaceId());
            viewPromptCityModel.setKey(promptCityDbModel.getKey().toString());

            if (promptCityDbModel.getWeatherDbModelList() != null && promptCityDbModel.getWeatherDbModelList().size() != 0) {
                CurrentWeatherDbModel weatherDbModel = promptCityDbModel.getWeatherDbModelList().get(0);
                viewPromptCityModel.setBriefInformation(weatherDbModel.getDescription() + " " + weatherDbModel.getMain());
            }
            viewPromptCityModelList.add(viewPromptCityModel);
        }
        return viewPromptCityModelList;
    }

    private ViewPromptCityStructureFormatting mapStructureFormatting(PromptCityDbModel promptCityDbModel) {
        ViewPromptCityStructureFormatting structureFormatting = new ViewPromptCityStructureFormatting();
        structureFormatting.setMainText(promptCityDbModel.getMainText());
        structureFormatting.setSecondaryText(promptCityDbModel.getSecondaryText());
        return structureFormatting;
    }
}
