package com.example.aamezencev.weatherinfo.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.ViewModels.ViewCityModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<ViewPromptCityModel> viewPromptCityModelList;

    public MainAdapter(List<ViewPromptCityModel> viewPromptCityModelList) {
        this.viewPromptCityModelList = viewPromptCityModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ViewPromptCityModel viewPromptCityModel = viewPromptCityModelList.get(position);
        holder.cbCity.setText(stringConcatenation(viewPromptCityModelList.get(position)));
        holder.cbCity.setChecked(viewPromptCityModel.isChecked());

        holder.cbCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean state = viewPromptCityModel.isChecked();
                viewPromptCityModel.setChecked(!state);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewPromptCityModelList.size();
    }

    private String stringConcatenation(ViewPromptCityModel viewPromptCityModel) {
        String separator = System.lineSeparator();
        String output = viewPromptCityModel.getId() + separator + viewPromptCityModel.getDescription() + separator +
                viewPromptCityModel.getStructuredFormatting().toString();

        return output;
    }

    public List<ViewPromptCityModel> getViewPromptCityModelList() {
        return viewPromptCityModelList;
    }

    public void setViewPromptCityModelList(List<ViewPromptCityModel> viewPromptCityModelList) {
        this.viewPromptCityModelList = viewPromptCityModelList;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cbCity;

        public ViewHolder(View itemView) {
            super(itemView);
            cbCity = itemView.findViewById(R.id.cbCity);
        }
    }
}
