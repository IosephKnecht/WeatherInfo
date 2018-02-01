package com.example.aamezencev.weatherinfo.view.viewModels;

import java.util.Objects;

/**
 * Created by aa.mezencev on 11.01.2018.
 */

public class ViewPromptCityModel {

    private String description;
    private String id;
    private ViewPromptCityStructureFormatting structuredFormatting;
    private boolean isChecked = false;
    private String placeId;
    private String key;

    private String briefInformation;

    public ViewPromptCityModel() {

    }

    public ViewPromptCityModel(String description, String id,
                               ViewPromptCityStructureFormatting structuredFormatting,
                               String placeId, boolean isChecked) {
        this.description = description;
        this.id = id;
        this.structuredFormatting = structuredFormatting;
        this.placeId = placeId;
        this.isChecked = isChecked;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ViewPromptCityStructureFormatting getStructuredFormatting() {
        return structuredFormatting;
    }

    public void setStructuredFormatting(ViewPromptCityStructureFormatting structuredFormatting) {
        this.structuredFormatting = structuredFormatting;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBriefInformation() {
        return briefInformation;
    }

    public void setBriefInformation(String briefInformation) {
        this.briefInformation = briefInformation;
    }

    @Override
    public boolean equals(Object obj) {
        ViewPromptCityModel viewModel = (ViewPromptCityModel) obj;
        return Objects.equals(this.description, viewModel.getDescription()) &&
                Objects.equals(this.id, viewModel.getId()) &&
                Objects.equals(this.isChecked, viewModel.isChecked()) &&
                Objects.equals(this.placeId, viewModel.getPlaceId()) &&
                Objects.equals(this.key, viewModel.getKey()) &&
                Objects.equals(this.getBriefInformation(), viewModel.getBriefInformation());
    }
}
