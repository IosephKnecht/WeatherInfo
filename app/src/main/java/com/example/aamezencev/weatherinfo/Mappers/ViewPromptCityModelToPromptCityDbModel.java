package com.example.aamezencev.weatherinfo.Mappers;

import com.example.aamezencev.weatherinfo.DaoModels.DaoMaster;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityStructureFormattingDbModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityStructureFormatting;

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
            PromptCityStructureFormattingDbModel structureFormattingDbModel = mapStructureFormatting(viewPromptCityModel.getStructuredFormatting());
            promptCityDbModel.setStructuredFormatting(structureFormattingDbModel);
            promptCityDbModel.setPlaceId(viewPromptCityModel.getPlaceId());
            promptCityDbModel.setStructureFormattingId((long)promptCityDbModel.getId().hashCode());
            promptCityDbModelList.add(promptCityDbModel);
        }
        return promptCityDbModelList;
    }

    private PromptCityStructureFormattingDbModel mapStructureFormatting(ViewPromptCityStructureFormatting structureFormatting) {
        PromptCityStructureFormattingDbModel structureFormattingDbModel = new PromptCityStructureFormattingDbModel();
        structureFormattingDbModel.setMainText(structureFormatting.getMainText());
        structureFormattingDbModel.setSecondaryText(structureFormatting.getSecondaryText());
        return structureFormattingDbModel;
    }
}
