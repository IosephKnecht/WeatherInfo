package com.example.aamezencev.weatherinfo.Requests;

import android.os.AsyncTask;

import com.example.aamezencev.weatherinfo.JsonModels.JsonResultsGeo;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class GetGeoToPlaceId extends AsyncTask<Void, Void, JsonResultsGeo> {

    private String link = "https://maps.googleapis.com/maps/api/geocode/json";
    private String placeId = "?place_id=";
    private String appId = "&key=AIzaSyD14lhUaBFiw8ZWPNXvSy3DszB7sPJfX3Y";
    private String inputPlaceId;

    private OkHttpClient okHttpClient = new OkHttpClient();

    public GetGeoToPlaceId(String inputPlaceId) {
        this.inputPlaceId = inputPlaceId;
    }

    @Override
    protected JsonResultsGeo doInBackground(Void... voids) {
        Request.Builder builder = new Request.Builder();
        builder.url(link + placeId+inputPlaceId+ appId);
        Request request = builder.build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            response.close();
        }
        String jsonString = null;
        try {
            jsonString = response.body().string().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JsonResultsGeo jsonReverseGeoModel = new JsonResultsGeo();
        jsonReverseGeoModel = gson.fromJson(jsonString, JsonResultsGeo.class);
        return jsonReverseGeoModel;
    }
}
