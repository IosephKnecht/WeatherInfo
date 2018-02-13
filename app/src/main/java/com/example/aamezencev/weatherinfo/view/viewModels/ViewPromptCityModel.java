package com.example.aamezencev.weatherinfo.view.viewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.util.ObjectsCompat;

import com.example.aamezencev.weatherinfo.BR;

/**
 * Created by aa.mezencev on 11.01.2018.
 */

public class ViewPromptCityModel extends BaseObservable {

    private String description;
    private String id;
    private ViewPromptCityStructureFormatting structuredFormatting;
    private boolean isChecked = false;
    private String placeId;
    private String key;

    private String briefInformation;

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

    @Bindable
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        notifyPropertyChanged(BR.key);
    }

    @Bindable
    public String getBriefInformation() {
        return briefInformation;
    }

    public void setBriefInformation(String briefInformation) {
        this.briefInformation = briefInformation;
        notifyPropertyChanged(BR.briefInformation);
    }

    @Override
    public boolean equals(Object obj) {
        ViewPromptCityModel viewModel = (ViewPromptCityModel) obj;
        return ObjectsCompat.equals(this.description, viewModel.getDescription()) &&
                ObjectsCompat.equals(this.id, viewModel.getId()) &&
                ObjectsCompat.equals(this.isChecked, viewModel.isChecked()) &&
                ObjectsCompat.equals(this.placeId, viewModel.getPlaceId()) &&
                ObjectsCompat.equals(this.key, viewModel.getKey()) &&
                ObjectsCompat.equals(this.getBriefInformation(), viewModel.getBriefInformation());
    }
}
