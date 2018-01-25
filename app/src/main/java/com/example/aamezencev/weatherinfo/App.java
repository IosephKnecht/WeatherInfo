package com.example.aamezencev.weatherinfo;

import android.app.Application;

import com.example.aamezencev.weatherinfo.DaoModels.DaoMaster;
import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;
import com.example.aamezencev.weatherinfo.Queries.RxDbManager;
import com.example.aamezencev.weatherinfo.Requests.RxGoogleApiManager;
import com.example.aamezencev.weatherinfo.Requests.RxOWMApiManager;

import org.greenrobot.greendao.database.Database;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class App extends Application {
    private DaoSession daoSession;
    private RxDbManager dbManager;
    private RxGoogleApiManager googleApiManager;
    private RxOWMApiManager owmApiManager;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "weatherInfoDb-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        dbManager = new RxDbManager(daoSession);
        googleApiManager = new RxGoogleApiManager();
        owmApiManager = new RxOWMApiManager();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public RxDbManager getDbManager() {
        return dbManager;
    }

    public RxGoogleApiManager getGoogleApiManager() {
        return googleApiManager;
    }

    public RxOWMApiManager getOwmApiManager() {
        return owmApiManager;
    }
}
