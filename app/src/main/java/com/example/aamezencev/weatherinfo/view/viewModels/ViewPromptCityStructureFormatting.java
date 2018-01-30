package com.example.aamezencev.weatherinfo.view.viewModels;

/**
 * Created by aa.mezencev on 11.01.2018.
 */

public class ViewPromptCityStructureFormatting {
    private String mainText;
    private String secondaryText;

    public ViewPromptCityStructureFormatting() {

    }

    public ViewPromptCityStructureFormatting(String mainText, String secondaryText) {
        this.mainText = mainText;
        this.secondaryText = secondaryText;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    @Override
    public String toString() {
        return mainText + " " + secondaryText;
    }
}
