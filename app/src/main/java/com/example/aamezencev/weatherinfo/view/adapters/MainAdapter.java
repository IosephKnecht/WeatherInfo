package com.example.aamezencev.weatherinfo.view.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aamezencev.weatherinfo.databinding.CityItemBinding;
import com.example.aamezencev.weatherinfo.view.handlers.ViewHandlers;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();
    private ViewHandlers handlers;

    public MainAdapter(ViewHandlers handlers) {
        this.handlers = handlers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CityItemBinding binding = CityItemBinding.inflate(inflater, parent, false);
        binding.setHandlers(handlers);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ViewPromptCityModel viewPromptCityModel = viewPromptCityModelList.get(position);
        viewPromptCityModel.setBriefInformation(stringConcatenation(viewPromptCityModel));
        holder.binding.setCity(viewPromptCityModel);
    }

    @Override
    public int getItemCount() {
        return viewPromptCityModelList.size();
    }

    private String stringConcatenation(ViewPromptCityModel viewPromptCityModel) {
        String separator = String.format("%n");
        String output = viewPromptCityModel.getId() + separator + viewPromptCityModel.getDescription() + separator +
                viewPromptCityModel.getStructuredFormatting().toString();
        return output;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private CityItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    public List<ViewPromptCityModel> getViewPromptCityModelList() {
        return viewPromptCityModelList;
    }

    public void setViewPromptCityModelList(List<ViewPromptCityModel> viewPromptCityModelList) {
        this.viewPromptCityModelList = viewPromptCityModelList;
    }
}
