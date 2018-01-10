package com.example.aamezencev.weatherinfo.Requests;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.aamezencev.weatherinfo.JsonModels.JsonCityModel;
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
 * Created by aa.mezencev on 10.01.2018.
 */

public class GetCytyList extends AsyncTask<List<JsonCityModel>, Void, List<JsonCityModel>> {

    private final String link = "https://raw.githubusercontent.com/IosephKnecht/WeatherInfo/master/city.json";
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Context context;

    private static GetCytyList getCytyList;

    private GetCytyList(Context context) {
        this.context = context;
    }

    public static GetCytyList instance(Context context) {
        if (getCytyList == null) return getCytyList = new GetCytyList(context);
        return getCytyList;
    }

    @Override
    protected List<JsonCityModel> doInBackground(List<JsonCityModel>[] lists) {
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

        Type type = new TypeToken<List<JsonCityModel>>() {
        }.getType();

        List<JsonCityModel> jsonCityModelList = new ArrayList<>();
        String jsonString = null;

        try {
            jsonString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jsonString != null) {
            jsonString = jsonString.replaceAll("\n|\r\n", " ");
            jsonString = jsonString.trim();
            jsonCityModelList = gson.fromJson(jsonString, type);
        }

        return jsonCityModelList;
    }

    @Override
    protected void onPostExecute(List<JsonCityModel> jsonCityModels) {
        super.onPostExecute(jsonCityModels);
        context.sendBroadcast(new Intent(CityReceiver.CITY_RECEIVER_ID));
    }
}
