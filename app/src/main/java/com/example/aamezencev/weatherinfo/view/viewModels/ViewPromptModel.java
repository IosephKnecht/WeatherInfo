package com.example.aamezencev.weatherinfo.view.viewModels;

import java.util.List;

/**
 * Created by aa.mezencev on 11.01.2018.
 */

public class ViewPromptModel {
    private List<ViewPromptCityModel> viewPromptCityModelList;
    private String status;

    public List<ViewPromptCityModel> getViewPromptCityModelList() {
        return viewPromptCityModelList;
    }

    public void setViewPromptCityModelList(List<ViewPromptCityModel> viewPromptCityModelList) {
        this.viewPromptCityModelList = viewPromptCityModelList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
