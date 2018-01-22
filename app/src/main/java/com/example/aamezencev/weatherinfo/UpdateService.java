package com.example.aamezencev.weatherinfo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;
import com.example.aamezencev.weatherinfo.Mappers.JsonWeatherModelToDb;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.JsonModels.JsonResultsGeo;
import com.example.aamezencev.weatherinfo.JsonModels.OWMApi.JsonWeatherModel;
import com.example.aamezencev.weatherinfo.Queries.AddListToDb;
import com.example.aamezencev.weatherinfo.Queries.AllItemQuery;
import com.example.aamezencev.weatherinfo.Requests.GetCurrentWeather;
import com.example.aamezencev.weatherinfo.Requests.GetGeoToPlaceId;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

/**
 * Created by aa.mezencev on 19.01.2018.
 */

public class UpdateService extends Service {

    private Timer timer;

    public UpdateService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer = new Timer();
        String prefString = PreferenceManager.getDefaultSharedPreferences(this).getString("etDelayService", null);
        int periodValue = 0;
        if (prefString != null) {
            periodValue = Integer.valueOf(prefString);
            if (periodValue < 60_000) periodValue = 60_000;
        } else {
            periodValue = 60_000;
        }
        DaoSession daoSession = ((App) getApplicationContext()).getDaoSession();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                AllItemQuery allItemQuery = new AllItemQuery(daoSession);
                allItemQuery.execute();
                List<PromptCityDbModel> promptCityDbModelList = new ArrayList<>();
                try {
                    promptCityDbModelList = allItemQuery.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                List<JsonWeatherModel> jsonWeatherModelList = new ArrayList<>();
                for (PromptCityDbModel dbModel : promptCityDbModelList) {
                    GetGeoToPlaceId getGeoToPlaceId = new GetGeoToPlaceId(dbModel.getPlaceId());
                    getGeoToPlaceId.execute();
                    JsonResultsGeo jsonResultsGeo = new JsonResultsGeo();
                    try {
                        jsonResultsGeo = getGeoToPlaceId.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    JsonWeatherModel jsonWeatherModel = new JsonWeatherModel();
                    if (jsonResultsGeo != null) {
                        GetCurrentWeather getCurrentWeather = new GetCurrentWeather(jsonResultsGeo.getJsonLocationModel().getLat(),
                                jsonResultsGeo.getJsonLocationModel().getLng());
                        getCurrentWeather.execute();
                        try {
                            jsonWeatherModel = getCurrentWeather.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        jsonWeatherModelList.add(jsonWeatherModel);
                    }
                }
                JsonWeatherModelToDb mapper = new JsonWeatherModelToDb(jsonWeatherModelList);
                List<CurrentWeatherDbModel> currentWeatherDbModelList = mapper.map();
                AddListToDb addListToDb = new AddListToDb(currentWeatherDbModelList, daoSession);
                if (currentWeatherDbModelList.size() != 0) addListToDb.execute();

            }
        };
        timer.schedule(timerTask, 0, periodValue);
        return super.onStartCommand(intent, flags, startId);
    }
}
