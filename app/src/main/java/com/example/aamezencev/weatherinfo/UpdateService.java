package com.example.aamezencev.weatherinfo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.aamezencev.weatherinfo.data.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.domain.RxDbManager;
import com.example.aamezencev.weatherinfo.domain.RxGoogleApiManager;
import com.example.aamezencev.weatherinfo.domain.RxOWMApiManager;
import com.example.aamezencev.weatherinfo.domain.mappers.CreateRealation;
import com.example.aamezencev.weatherinfo.domain.mappers.JsonWeatherModelToDb;
import com.example.aamezencev.weatherinfo.events.UpdatedCurrentWeather;
import com.example.aamezencev.weatherinfo.view.WeatherListActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
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

    private CompositeDisposable compositeDisposable;
    @Inject
    RxDbManager dbManager;
    @Inject
    RxGoogleApiManager googleApiManager;
    @Inject
    RxOWMApiManager owmApiManager;

    private final PublishSubject<Void> retrySubject = PublishSubject.create();

    @Override
    public void onCreate() {
        super.onCreate();
        compositeDisposable = new CompositeDisposable();
        App.getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        compositeDisposable = null;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        dbManager.allItemQuery()
                .subscribeOn(Schedulers.io())
                .flatMap(cities -> Observable.fromIterable(cities)
                        .flatMap(city -> googleApiManager.geoRequest(city.getPlaceId())
                                .flatMap(geo -> owmApiManager.currentWeatherRequest(geo.getJsonLocationModel().getLat(), geo.getJsonLocationModel().getLng()))
                                .map(jsonWeatherModels -> new JsonWeatherModelToDb(jsonWeatherModels).map())
                                .map(currentWeatherDbModels -> new CreateRealation(city, currentWeatherDbModels).map())
                                .flatMap(currentWeatherDbModels -> dbManager.addListToDbQuery(currentWeatherDbModels))
                        )
                )
                .subscribe(currentWeatherDbModels -> {
                    String s = null;
                });
//        compositeDisposable.add(dbManager.allItemQuery()
//                .subscribeOn(Schedulers.io())
//                .flatMap(cities -> Observable.fromIterable(cities)
//                        .flatMap(city -> googleApiManager.geoRequest(city.getPlaceId()))
//                        .flatMap(geo -> owmApiManager.currentWeatherRequest(geo.getJsonLocationModel().getLat(), geo.getJsonLocationModel().getLng()))
//                        .toList()
//                        .map(weatherModels -> new JsonWeatherModelToDb(weatherModels).map())
//                        .toObservable()
//                        .flatMap(aVoid -> dbManager.addListToDbQuery(aVoid))
//                        .map(currentWeatherDbModels -> new CreateRealation(cities, currentWeatherDbModels).map())
//                        .flatMap(promptCityDbModels -> dbManager.addPromptListToDb(promptCityDbModels))
//                        .retryWhen(throwableObservable -> throwableObservable.flatMap(error -> {
//                            Log.d("myLog", "retry");
//                            return Observable.just(cities).delay(120_000, TimeUnit.MILLISECONDS);
//                        }))
//                )
//                .repeatWhen(completed -> completed.delay(60_000, TimeUnit.MILLISECONDS))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        dbModelList -> {
//                            EventBus.getDefault().post(new UpdatedCurrentWeather());
//                            Log.d("myLog", "connect");
//                        },
//                        error -> {
//                        }
//                ));

        return super.onStartCommand(intent, flags, startId);
    }

}
