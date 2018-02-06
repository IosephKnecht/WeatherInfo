package com.example.aamezencev.weatherinfo.domain;


import android.support.annotation.NonNull;

import com.example.aamezencev.weatherinfo.data.owmApi.JsonWeatherModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

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

    private final String tempMode = "&units=metric";

    public RxOWMApiManager() {

    }

    public Observable<List<JsonWeatherModel>> currentWeatherRequest(String lat, String lon) {
        return Observable.<Response>create(aVoid -> {
            Request.Builder builder = new Request.Builder();
            builder.url(String.format("http://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s%s&APPID=%s&cnt=7", lat, lon, tempMode, appId));
            Request request = builder.build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                aVoid.onNext(response);
            } catch (Exception ex) {
                aVoid.onError(new Throwable("error in getting weather"));
            }
            aVoid.onComplete();
        })
                .map(response -> {
                    Gson gson = new Gson();
                    String jsonString = response.body().string();
                    Type type = new TypeToken<List<JsonWeatherModel>>() {
                    }.getType();
                    List<JsonWeatherModel> jsonWeatherModelList = gson.fromJson(jsonString, type);
                    return jsonWeatherModelList;
                });
    }

    @Provides
    @NonNull
    @Singleton
    public RxOWMApiManager getOWMApiManager() {
        return new RxOWMApiManager();
    }
}
