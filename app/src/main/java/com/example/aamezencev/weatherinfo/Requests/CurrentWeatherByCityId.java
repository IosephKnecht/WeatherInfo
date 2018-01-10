package com.example.aamezencev.weatherinfo.Requests;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class CurrentWeatherByCityId extends AsyncTask<String, Void, String> {
    private final String link = "api.openweathermap.org/data/2.5/weather?id=";
    private String cityId;
    private OkHttpClient okHttpClient = new OkHttpClient();

    public CurrentWeatherByCityId(String cityId) {
        this.cityId = cityId;
    }


    @Override
    protected String doInBackground(String... strings) {
        Request.Builder builder = new Request.Builder();
        builder.url(link + cityId);

        Request request = builder.build();
        Response response = null;

        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            response.close();
        }

        return null;
    }
}
