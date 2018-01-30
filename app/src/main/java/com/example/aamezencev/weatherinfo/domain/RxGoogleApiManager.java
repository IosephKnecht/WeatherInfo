package com.example.aamezencev.weatherinfo.domain;

import android.support.annotation.NonNull;

import com.example.aamezencev.weatherinfo.data.googleApi.JsonResultsGeo;
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
public class RxGoogleApiManager {
    private OkHttpClient okHttpClient = new OkHttpClient();

    private final String promptApiKey = "AIzaSyClYMjC6UsJ4KaB1EjUCTgVQR9-qh0VnP8";

    private final String geoApiKey = "AIzaSyD14lhUaBFiw8ZWPNXvSy3DszB7sPJfX3Y";

    public RxGoogleApiManager() {

    }

    public Observable<Response> promptRequest(String city) {
        return Observable.<Response>create(aVoid -> {
            Request.Builder builder = new Request.Builder();
            builder.url(String.format("https://maps.googleapis.com/maps/api/place/autocomplete/json?input=%s&types=(regions)&language=en&key=%s", city, promptApiKey));
            Request request = builder.build();
            Response response = okHttpClient.newCall(request).execute();
            aVoid.onNext(response);
            aVoid.onComplete();
        });
    }

    public Observable<JsonResultsGeo> geoRequest(String placeId) {
        return Observable.<Response>create(aVoid -> {
            Request.Builder builder = new Request.Builder();
            builder.url(String.format("https://maps.googleapis.com/maps/api/geocode/json?place_id=%s&key=%s", placeId, geoApiKey));
            Request request = builder.build();
            Response response = okHttpClient.newCall(request).execute();
            aVoid.onNext(response);
            aVoid.onComplete();
        })
                .map(response -> {
                    Gson gson = new Gson();
                    String jsonString = response.body().string();
                    JsonResultsGeo jsonResultsGeo = gson.fromJson(jsonString, JsonResultsGeo.class);
                    return jsonResultsGeo;
                });
    }

    @Provides
    @NonNull
    @Singleton
    public RxGoogleApiManager getGoogleApiManager(){
        return new RxGoogleApiManager();
    }
}
