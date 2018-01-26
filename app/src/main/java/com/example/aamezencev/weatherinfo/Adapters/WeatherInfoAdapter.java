package com.example.aamezencev.weatherinfo.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aamezencev.weatherinfo.JsonModels.OWMApi.JsonWeatherInfo;
import com.example.aamezencev.weatherinfo.JsonModels.OWMApi.JsonWeatherModel;
import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.ViewModels.ViewCurrentWeatherModel;

import java.util.List;

/**
 * Created by aa.mezencev on 15.01.2018.
 */

public class WeatherInfoAdapter extends RecyclerView.Adapter<WeatherInfoAdapter.ViewHolder> {

    private ViewCurrentWeatherModel viewCurrentWeatherModel;

    public WeatherInfoAdapter(ViewCurrentWeatherModel viewCurrentWeatherModel) {
        this.viewCurrentWeatherModel = viewCurrentWeatherModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_info_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public ViewCurrentWeatherModel getViewCurrentWeatherModel() {
        return viewCurrentWeatherModel;
    }

    public void setViewCurrentWeatherModel(ViewCurrentWeatherModel currentWeatherModel) {
        this.viewCurrentWeatherModel = currentWeatherModel;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvWeatherInfo.setText(viewCurrentWeatherModel.getFullInfotmation());
        Glide.with(holder.ivIconWeather.getContext())
                .load("http://openweathermap.org/img/w/" + viewCurrentWeatherModel.getIcon() + ".png")
                .into(holder.ivIconWeather);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvWeatherInfo;
        private ImageView ivIconWeather;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvWeatherInfo = itemView.findViewById(R.id.tvWeatherInfo);
            this.ivIconWeather = itemView.findViewById(R.id.ivIconWeather);
        }
    }
}
