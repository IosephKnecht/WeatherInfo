package com.example.aamezencev.weatherinfo.view.adapters;

import android.support.v7.util.DiffUtil;

import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

import java.util.List;

/**
 * Created by aa.mezencev on 26.01.2018.
 */

public class DiffUtilMainAdapter extends DiffUtil.Callback {

    private List<ViewPromptCityModel> oldViewPromptCityModelList;
    private List<ViewPromptCityModel> newViewPromptCityModelList;

    public DiffUtilMainAdapter(List<ViewPromptCityModel> oldViewPromptCityModelList,
                               List<ViewPromptCityModel> newViewPromptCityModelList) {
        this.oldViewPromptCityModelList = oldViewPromptCityModelList;
        this.newViewPromptCityModelList = newViewPromptCityModelList;
    }

    @Override
    public int getOldListSize() {
        return oldViewPromptCityModelList.size();
    }

    @Override
    public int getNewListSize() {
        return newViewPromptCityModelList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        ViewPromptCityModel oldView = oldViewPromptCityModelList.get(oldItemPosition);
        ViewPromptCityModel newView = newViewPromptCityModelList.get(newItemPosition);
        return oldView.getId().equals(newView.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldViewPromptCityModelList.get(oldItemPosition).equals(newViewPromptCityModelList.get(newItemPosition));
    }
}
