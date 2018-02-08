package com.example.aamezencev.weatherinfo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.aamezencev.weatherinfo.data.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.domain.FacadeManager;
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
    FacadeManager facadeManager;

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
        compositeDisposable.add(facadeManager
                .getUpdateObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(lists -> lists.size() != 0)
                .subscribe(currentWeatherDbModels -> {
                    EventBus.getDefault().post(new UpdatedCurrentWeather());
                    Log.d("myLog", "connect");
                }, error -> {
                })
        );

        return super.onStartCommand(intent, flags, startId);
    }

}
