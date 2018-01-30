package com.example.aamezencev.weatherinfo;

import android.app.Application;

import com.example.aamezencev.weatherinfo.data.DaoMaster;
import com.example.aamezencev.weatherinfo.data.DaoSession;
import com.example.aamezencev.weatherinfo.domain.RxDbManager;
import com.example.aamezencev.weatherinfo.domain.RxGoogleApiManager;
import com.example.aamezencev.weatherinfo.domain.RxOWMApiManager;
import com.example.aamezencev.weatherinfo.inrerfaces.AppComponent;
import com.example.aamezencev.weatherinfo.inrerfaces.DaggerAppComponent;

import org.greenrobot.greendao.database.Database;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = buildComponent();
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent
                .builder()
                .initDb(new InitDb(this))
                .rxDbManager(new RxDbManager())
                .rxGoogleApiManager(new RxGoogleApiManager())
                .rxOWMApiManager(new RxOWMApiManager())
                .build();
    }
}
