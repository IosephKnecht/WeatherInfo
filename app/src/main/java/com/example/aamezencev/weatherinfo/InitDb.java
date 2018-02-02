package com.example.aamezencev.weatherinfo;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.aamezencev.weatherinfo.data.DaoMaster;
import com.example.aamezencev.weatherinfo.data.DaoSession;
import com.example.aamezencev.weatherinfo.domain.RxDbManager;
import com.example.aamezencev.weatherinfo.domain.RxGoogleApiManager;
import com.example.aamezencev.weatherinfo.domain.RxOWMApiManager;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aa.mezencev on 30.01.2018.
 */
public class InitDb {
    private DaoSession daoSession;

    public InitDb(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "weatherInfoDb-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
