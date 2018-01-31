package com.example.aamezencev.weatherinfo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.aamezencev.weatherinfo.data.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.data.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.domain.RxDbManager;
import com.example.aamezencev.weatherinfo.domain.RxGoogleApiManager;
import com.example.aamezencev.weatherinfo.domain.RxOWMApiManager;
import com.example.aamezencev.weatherinfo.domain.mappers.JsonWeatherModelToDb;
import com.example.aamezencev.weatherinfo.events.UpdatedCurrentWeather;
import com.example.aamezencev.weatherinfo.view.mappers.CurrentWeatherDbModelToView;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewCurrentWeatherModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by aa.mezencev on 19.01.2018.
 */

public class UpdateService extends Service {

    private Timer timer;
    private CompositeDisposable compositeDisposable;
    @Inject
    RxDbManager dbManager;
    @Inject
    RxGoogleApiManager googleApiManager;
    @Inject
    RxOWMApiManager owmApiManager;

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        compositeDisposable = new CompositeDisposable();
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
        App.getAppComponent().inject(this);
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
                .repeatWhen(completed -> completed.delay(60_000, TimeUnit.MILLISECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbModelList -> EventBus.getDefault().post(new UpdatedCurrentWeather<CurrentWeatherDbModel>(dbModelList))));

        return super.onStartCommand(intent, flags, startId);
    }

}
