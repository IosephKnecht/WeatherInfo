package com.example.aamezencev.weatherinfo.Requests;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.aamezencev.weatherinfo.JsonModels.JsonPromptModel;
import com.example.aamezencev.weatherinfo.Recievers.CityReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by aa.mezencev on 11.01.2018.
 */

public class PromptRequest extends AsyncTask<Void, Void, JsonPromptModel> {
    private final String link = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=";
    public static final String apiKey = "&key=AIzaSyClYMjC6UsJ4KaB1EjUCTgVQR9-qh0VnP8";
    private String city;
    private final String language = "&language=en";
    private final String types = "&types=(regions)";

    private static PromptRequest promptRequest;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Context context;

    private PromptRequest(Context context) {
        this.context = context;
    }

    public static PromptRequest instance(Context context) {
        if (promptRequest != null) return promptRequest;
        return promptRequest = new PromptRequest(context);
    }

    @Override
    protected JsonPromptModel doInBackground(Void... voids) {
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

        return jsonPromptModel;
    }

    private String compileLink() {
        String currentLink = link + city + types + language + apiKey;
        return currentLink;
    }

    @Override
    protected void onPostExecute(JsonPromptModel jsonPromptModel) {
        super.onPostExecute(jsonPromptModel);
        context.sendBroadcast(new Intent(CityReceiver.CITY_RECEIVER_ID));
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static void destroySingleton() {
        promptRequest = null;
    }
}
