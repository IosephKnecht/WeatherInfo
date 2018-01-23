package com.example.aamezencev.weatherinfo.Requests;

import android.os.AsyncTask;

import com.example.aamezencev.weatherinfo.JsonModels.JsonPromptModel;
import com.example.aamezencev.weatherinfo.Mappers.JsonPromptModelToViewPromptModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by aa.mezencev on 11.01.2018.
 */

public class PromptRequest extends AsyncTask<Void, Void, List<ViewPromptCityModel>> {
    private final String link = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=";
    public static final String apiKey = "&key=AIzaSyClYMjC6UsJ4KaB1EjUCTgVQR9-qh0VnP8";
    private String city;
    private final String language = "&language=en";
    private final String types = "&types=(regions)";

    private OkHttpClient okHttpClient = new OkHttpClient();

    public PromptRequest(String city) {
        this.city = city;
    }

    @Override
    protected List<ViewPromptCityModel> doInBackground(Void... voids) {
        Request request = new Request.Builder().url(compileLink()).build();
        Response response = null;

        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            response.close();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<JsonPromptModel>() {
        }.getType();

        JsonPromptModel jsonPromptModel = new JsonPromptModel();
        String jsonString = null;

        try {
            jsonString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            response.close();
        }

        if (jsonString != null) {
            jsonPromptModel = gson.fromJson(jsonString, JsonPromptModel.class);
        }

        JsonPromptModelToViewPromptModel jsonPromptModelToViewPromptModel = new JsonPromptModelToViewPromptModel(jsonPromptModel);
        ViewPromptModel viewPromptModel = new ViewPromptModel();
        viewPromptModel = jsonPromptModelToViewPromptModel.map();

        return viewPromptModel.getViewPromptCityModelList();
    }

    private String compileLink() {
        String currentLink = link + city + types + language + apiKey;
        return currentLink;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
