package com.example.aamezencev.weatherinfo.data.googleApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aa.mezencev on 11.01.2018.
 */

public class JsonPromptCityStructureFormatting {
    @SerializedName("main_text")
    private String mainText;
    @SerializedName("secondary_text")
    private String secondaryText;

    public JsonPromptCityStructureFormatting(String mainText, String secondaryText) {
        this.mainText = mainText;
        this.secondaryText = secondaryText;
    }

    public String getMainText() {
        return mainText;
    }

    public String getSecondaryText() {
        return secondaryText;
    }
}
