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

import java.util.List;

/**
 * Created by aa.mezencev on 15.01.2018.
 */

public class WeatherInfoAdapter extends RecyclerView.Adapter<WeatherInfoAdapter.ViewHolder> {

    private List<JsonWeatherInfo> jsonWeatherInfoList;

    public WeatherInfoAdapter(List<JsonWeatherInfo> jsonWeatherInfoList) {
        this.jsonWeatherInfoList = jsonWeatherInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_info_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JsonWeatherInfo jsonWeatherInfo = jsonWeatherInfoList.get(position);
        holder.tvWeatherInfo.setText(jsonWeatherInfo.toString());
        Glide.with(holder.ivIconWeather.getContext())
                .load("http://openweathermap.org/img/w/" + jsonWeatherInfo.getIcon()+".png")
                .into(holder.ivIconWeather);
    }

    @Override
    public int getItemCount() {
        return jsonWeatherInfoList.size();
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
