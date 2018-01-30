package com.example.aamezencev.weatherinfo.data.googleApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aa.mezencev on 11.01.2018.
 */
public class JsonPromptModel {
   @SerializedName("predictions")
    private List<JsonPromptCityModel> jsonPromptCityModels;
    @SerializedName("status")
    private  String status;

    public JsonPromptModel(List<JsonPromptCityModel> jsonPromptModelList,String status){
        this.jsonPromptCityModels=jsonPromptModelList;
        this.status=status;
    }

    public JsonPromptModel(){

    }

    public List<JsonPromptCityModel> getJsonPromptCityModels() {
        return jsonPromptCityModels;
    }

    public String getStatus() {
        return status;
    }
}
