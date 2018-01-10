package com.example.aamezencev.weatherinfo.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.ViewModels.ViewCityModel;

import java.util.List;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<ViewCityModel> viewCityModelList;

    public MainAdapter(List<ViewCityModel> viewCityModelList) {
        this.viewCityModelList = viewCityModelList;
    }

    public List<ViewCityModel> getViewCityModelList() {
        return viewCityModelList;
    }

    public void setViewCityModelList(List<ViewCityModel> viewCityModelList) {
        this.viewCityModelList = viewCityModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvCity.setText(stringConcatenation(viewCityModelList.get(position)));
    }

    @Override
    public int getItemCount() {
        return viewCityModelList.size();
    }

    private String stringConcatenation(ViewCityModel viewCityModel) {
        String output = viewCityModel.getId() + " " + viewCityModel.getName() + " " +
                viewCityModel.getCountry() + " " + viewCityModel.getLon() + " " +
                viewCityModel.getLat();

        return output;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCity;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tvCity);
        }
    }
}
