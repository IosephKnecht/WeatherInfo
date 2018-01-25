package com.example.aamezencev.weatherinfo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;
import com.example.aamezencev.weatherinfo.Events.UpdatedCurrentWeather;
import com.example.aamezencev.weatherinfo.Mappers.JsonWeatherModelToDb;
import com.example.aamezencev.weatherinfo.Queries.RxDbManager;
import com.example.aamezencev.weatherinfo.Requests.RxGoogleApiManager;
import com.example.aamezencev.weatherinfo.Requests.RxOWMApiManager;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aa.mezencev on 19.01.2018.
 */

public class UpdateService extends Service {

    private Timer timer;
    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
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
        compositeDisposable.dispose();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        compositeDisposable = new CompositeDisposable();
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
                RxDbManager dbManager = ((App) getApplicationContext()).getDbManager();
                RxGoogleApiManager googleApiManager = ((App) getApplication()).getGoogleApiManager();
                RxOWMApiManager owmApiManager = ((App) getApplicationContext()).getOwmApiManager();
                compositeDisposable.add(dbManager.allItemQuery()
                        //.flatMap(list->RxDbManager.instance().addPromptListToDb(list))
                        .subscribeOn(Schedulers.io())
                        .flatMap(cities -> Observable.fromIterable(cities)
                                .flatMap(city -> googleApiManager.geoRequest(city.getPlaceId()))
                                .flatMap(geo -> owmApiManager.currentWeatherRequest(geo.getJsonLocationModel().getLat(), geo.getJsonLocationModel().getLng()))
                                .toList()
                                .map(weatherModels -> new JsonWeatherModelToDb(weatherModels, cities).map())
                                .toObservable()
                                .flatMap(aVoid -> dbManager.addListToDbQuery(aVoid))
                        )
                        .subscribe(dbList -> {
                            EventBus.getDefault().post(new UpdatedCurrentWeather(dbList));
                        }));

            }
        };
        timer.schedule(timerTask, 0, periodValue);
        return super.onStartCommand(intent, flags, startId);
    }
}
