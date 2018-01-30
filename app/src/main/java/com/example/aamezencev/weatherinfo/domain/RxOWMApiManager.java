package com.example.aamezencev.weatherinfo.domain;


import android.support.annotation.NonNull;

import com.example.aamezencev.weatherinfo.data.owmApi.JsonWeatherModel;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by aa.mezencev on 24.01.2018.
 */
@Module
public class RxOWMApiManager {
    private OkHttpClient okHttpClient = new OkHttpClient();

    private final String appId = "1929d22867616c7ad0d33873b6d1f32d";

    public RxOWMApiManager() {

    }

    public Observable<JsonWeatherModel> currentWeatherRequest(String lat, String lon) {
        return Observable.<Response>create(aVoid -> {
            Request.Builder builder = new Request.Builder();
            builder.url(String.format("http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&APPID=%s", lat, lon, appId));
            Request request = builder.build();
            Response response = okHttpClient.newCall(request).execute();
            aVoid.onNext(response);
            aVoid.onComplete();
        })
                .map(response -> {
                    Gson gson = new Gson();
                    String jsonString = response.body().string();
                    JsonWeatherModel jsonWeatherModel = gson.fromJson(jsonString, JsonWeatherModel.class);
                    return jsonWeatherModel;
                });
    }

    @Provides
    @NonNull
    @Singleton
    public RxOWMApiManager getOWMApiManager() {
        return new RxOWMApiManager();
    }
}
