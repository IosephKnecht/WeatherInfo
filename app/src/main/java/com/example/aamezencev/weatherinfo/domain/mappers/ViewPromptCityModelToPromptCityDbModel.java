package com.example.aamezencev.weatherinfo.domain.mappers;

import com.example.aamezencev.weatherinfo.data.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class ViewPromptCityModelToPromptCityDbModel {
    private List<ViewPromptCityModel> viewPromptCityModelList;

    public ViewPromptCityModelToPromptCityDbModel(List<ViewPromptCityModel> viewPromptCityModelList) {
        this.viewPromptCityModelList = viewPromptCityModelList;
    }

    public List<PromptCityDbModel> map() {
        List<PromptCityDbModel> promptCityDbModelList = new ArrayList<>();
        for (ViewPromptCityModel viewPromptCityModel : viewPromptCityModelList) {
            PromptCityDbModel promptCityDbModel = new PromptCityDbModel();
            promptCityDbModel.setId(viewPromptCityModel.getId());
            promptCityDbModel.setDescription(viewPromptCityModel.getDescription());
            promptCityDbModel.setMainText(viewPromptCityModel.getStructuredFormatting().getMainText());
            promptCityDbModel.setSecondaryText(viewPromptCityModel.getStructuredFormatting().getSecondaryText());
            promptCityDbModel.setPlaceId(viewPromptCityModel.getPlaceId());
            promptCityDbModel.setBriefInformation(viewPromptCityModel.getBriefInformation());
            promptCityDbModelList.add(promptCityDbModel);
        }
        return promptCityDbModelList;
    }

}
