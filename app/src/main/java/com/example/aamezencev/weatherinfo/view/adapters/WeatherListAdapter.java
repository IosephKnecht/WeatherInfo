package com.example.aamezencev.weatherinfo.view.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.aamezencev.weatherinfo.databinding.WeatherItemBinding;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;
import com.example.aamezencev.weatherinfo.view.viewModels.WeatherListHandlers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> {

    private List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();
    private WeatherListHandlers weatherListHandlers;

    public WeatherListAdapter(WeatherListHandlers weatherListHandlers) {
        this.weatherListHandlers = weatherListHandlers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        WeatherItemBinding binding = WeatherItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ViewPromptCityModel city = viewPromptCityModelList.get(position);
        holder.binding.setCity(city);
        holder.binding.setHandlers(weatherListHandlers);
    }

    @Override
    public int getItemCount() {
        return viewPromptCityModelList.size();
    }

    public List<ViewPromptCityModel> getViewPromptCityModelList() {
        return viewPromptCityModelList;
    }

    public void setViewPromptCityModelList(List<ViewPromptCityModel> viewPromptCityModelList) {
        this.viewPromptCityModelList = viewPromptCityModelList;
    }

    public void removeItem(int position) {
        viewPromptCityModelList.remove(position);
        notifyItemRemoved(position);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private WeatherItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public LinearLayout getViewForeground() {
            return binding.viewForeground;
        }
    }
}
