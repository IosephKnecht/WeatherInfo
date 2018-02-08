package com.example.aamezencev.weatherinfo;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.aamezencev.weatherinfo.data.DaoSession;
import com.example.aamezencev.weatherinfo.domain.FacadeManager;
import com.example.aamezencev.weatherinfo.domain.RxDbManager;
import com.example.aamezencev.weatherinfo.domain.RxGoogleApiManager;
import com.example.aamezencev.weatherinfo.domain.RxOWMApiManager;

import org.greenrobot.greendao.annotation.NotNull;

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
    @NotNull
    @Singleton
    public FacadeManager getFacadeManager() {
        DaoSession daoSession = new InitDb(context).getDaoSession();
        return new FacadeManager(new RxDbManager(daoSession), new RxGoogleApiManager(), new RxOWMApiManager());
    }
}
