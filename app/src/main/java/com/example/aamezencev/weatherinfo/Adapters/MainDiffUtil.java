package com.example.aamezencev.weatherinfo.Adapters;

import android.support.v7.util.DiffUtil;

import com.example.aamezencev.weatherinfo.ViewModels.ViewCityModel;

import java.util.List;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class MainDiffUtil extends DiffUtil.Callback {

    private final List<ViewCityModel> oldViewCityModelList;
    private final List<ViewCityModel> newViewCityModelList;

    public MainDiffUtil(List<ViewCityModel> oldViewCityModelList, List<ViewCityModel> newViewCityModelList) {
        this.oldViewCityModelList = oldViewCityModelList;
        this.newViewCityModelList = newViewCityModelList;
    }

    @Override
    public int getOldListSize() {
        return oldViewCityModelList.size();
    }

    @Override
    public int getNewListSize() {
        return newViewCityModelList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        ViewCityModel newViewCityModel = newViewCityModelList.get(newItemPosition);
        ViewCityModel oldViewCityModel = oldViewCityModelList.get(oldItemPosition);

        return newViewCityModel.getId() == oldViewCityModel.getId() &&
                newViewCityModel.getCountry() == oldViewCityModel.getCountry() &&
                newViewCityModel.getName() == oldViewCityModel.getName();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        ViewCityModel newViewCityModel = newViewCityModelList.get(newItemPosition);
        ViewCityModel oldViewCityModel = oldViewCityModelList.get(oldItemPosition);

        return newViewCityModel.getId().equals(oldViewCityModel.getId()) &&
                newViewCityModel.getCountry().equals(oldViewCityModel.getCountry()) &&
                newViewCityModel.getName().equals(oldViewCityModel.getName());
    }
}
