package com.example.aamezencev.weatherinfo;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.aamezencev.weatherinfo.data.DaoSession;
import com.example.aamezencev.weatherinfo.domain.RxDbManager;
import com.example.aamezencev.weatherinfo.domain.RxGoogleApiManager;
import com.example.aamezencev.weatherinfo.domain.RxOWMApiManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aa.mezencev on 01.02.2018.
 */
@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @NonNull
    @Singleton
    public RxGoogleApiManager getGoogleApiManager() {
        return new RxGoogleApiManager();
    }

    @Provides
    @NonNull
    @Singleton
    public RxOWMApiManager getOWMApiManager() {
        return new RxOWMApiManager();
    }

    @Provides
    @NonNull
    @Singleton
    public RxDbManager getDbManager(DaoSession daoSession) {
        return new RxDbManager(daoSession);
    }

    @Provides
    @NonNull
    @Singleton
    public DaoSession getDaoSession(Context context) {
        return new InitDb(context).getDaoSession();
    }

    @Provides
    @NonNull
    @Singleton
    public Context getContext() {
        return context;
    }
}
