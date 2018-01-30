package com.example.aamezencev.weatherinfo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;

import io.reactivex.disposables.CompositeDisposable;

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
//        compositeDisposable.dispose();
    }
}
