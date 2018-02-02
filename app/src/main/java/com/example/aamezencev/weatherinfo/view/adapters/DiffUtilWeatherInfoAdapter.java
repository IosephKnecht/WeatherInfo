package com.example.aamezencev.weatherinfo.view.adapters;

import android.support.v4.util.ObjectsCompat;
import android.support.v7.util.DiffUtil;

import com.example.aamezencev.weatherinfo.view.viewModels.ViewCurrentWeatherModel;

/**
 * Created by aa.mezencev on 26.01.2018.
 */

public class DiffUtilWeatherInfoAdapter extends DiffUtil.Callback {

    private ViewCurrentWeatherModel oldViewCurrentWeatherModel;
    private ViewCurrentWeatherModel newViewCurrentWeatherModel;

    public DiffUtilWeatherInfoAdapter(ViewCurrentWeatherModel oldViewCurrentWeatherModel,
                                      ViewCurrentWeatherModel newViewCurrentWeatherModel) {
        this.oldViewCurrentWeatherModel = oldViewCurrentWeatherModel;
        this.newViewCurrentWeatherModel = newViewCurrentWeatherModel;
    }

    @Override
    public int getOldListSize() {
        return 1;
    }

    @Override
    public int getNewListSize() {
        return 1;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return ObjectsCompat.equals(oldViewCurrentWeatherModel.getId(), newViewCurrentWeatherModel.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldViewCurrentWeatherModel.equals(newViewCurrentWeatherModel);
    }
}
