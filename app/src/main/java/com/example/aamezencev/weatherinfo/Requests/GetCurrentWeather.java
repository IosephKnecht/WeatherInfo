package com.example.aamezencev.weatherinfo.Requests;

import android.os.AsyncTask;

import com.example.aamezencev.weatherinfo.JsonModels.OWMApi.JsonWeatherModel;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by aa.mezencev on 15.01.2018.
 */

public class GetCurrentWeather extends AsyncTask<Void, Void, JsonWeatherModel> {
    private String link = "http://api.openweathermap.org/data/2.5/weather?";
    private String lat = "lat=";
    private String lng = "&lon=";
    private String appId = "&APPID=1929d22867616c7ad0d33873b6d1f32d";

    private String latValue;
    private String lngValue;

    private OkHttpClient okHttpClient = new OkHttpClient();

    public GetCurrentWeather(String latValue, String lngValue) {
        this.latValue = latValue;
        this.lngValue = lngValue;
        link += lat + latValue + lng + lngValue + appId;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    @Override
    protected JsonWeatherModel doInBackground(Void... voids) {
        Request.Builder builder = new Request.Builder();
        builder.url(link);
        Request request = builder.build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            response.close();
        }

        Gson gson = new Gson();

        String jsonString = null;
        try {
            jsonString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonWeatherModel jsonWeatherModel=new JsonWeatherModel();
        if (jsonString != null) {
            jsonWeatherModel = gson.fromJson(jsonString,JsonWeatherModel.class);
        }

        return jsonWeatherModel;
    }
}
