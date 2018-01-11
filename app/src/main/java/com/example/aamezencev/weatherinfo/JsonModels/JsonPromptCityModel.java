package com.example.aamezencev.weatherinfo.JsonModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aa.mezencev on 11.01.2018.
 */

public class JsonPromptCityModel {
    @SerializedName("description")
    private String description;
    @SerializedName("id")
    private String id;
    @SerializedName("structured_formatting")
    private JsonPromptCityStructureFormatting structuredFormatting;

    public JsonPromptCityModel(String description, String id,
                               JsonPromptCityStructureFormatting structuredFormatting) {
        this.description = description;
        this.id = id;
        this.structuredFormatting = structuredFormatting;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public JsonPromptCityStructureFormatting getStructuredFormatting() {
        return structuredFormatting;
    }
}
