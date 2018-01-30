package com.example.aamezencev.weatherinfo.view.adapters;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

import java.util.List;

/**
 * Created by aa.mezencev on 26.01.2018.
 */

public class DiffUtilWeatherListAdapter extends DiffUtil.Callback {

    private List<ViewPromptCityModel> oldViewPromptCityModelList;
    private List<ViewPromptCityModel> newViewPromptCityModelList;

    public DiffUtilWeatherListAdapter(List<ViewPromptCityModel> oldViewPromptCityModelList,
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
        return newViewPromptCityModelList.get(newItemPosition).getKey().equals(oldViewPromptCityModelList.get(oldItemPosition).getKey());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldViewPromptCityModelList.get(oldItemPosition).equals(newViewPromptCityModelList.get(newItemPosition));
    }
}
