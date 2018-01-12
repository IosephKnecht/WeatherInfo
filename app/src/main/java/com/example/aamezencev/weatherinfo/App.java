package com.example.aamezencev.weatherinfo;

import android.app.Application;

import com.example.aamezencev.weatherinfo.DaoModels.DaoMaster;
import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class App extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "weatherInfoDb-db");
        Database db = helper.getWritableDb();
        daoSession=new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }
}
