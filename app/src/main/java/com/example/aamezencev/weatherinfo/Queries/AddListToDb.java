package com.example.aamezencev.weatherinfo.Queries;

import android.os.AsyncTask;

import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModelDao;
import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;

import java.util.List;

/**
 * Created by aa.mezencev on 19.01.2018.
 */

public class AddListToDb extends AsyncTask<Void, Void, Void> {
    private List<CurrentWeatherDbModel> currentWeatherDbModelList;
    private DaoSession daoSession;

    public AddListToDb(List<CurrentWeatherDbModel> currentWeatherDbModelList, DaoSession daoSession) {
        this.currentWeatherDbModelList = currentWeatherDbModelList;
        this.daoSession = daoSession;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        CurrentWeatherDbModelDao currentWeatherDbModelDao = daoSession.getCurrentWeatherDbModelDao();
        currentWeatherDbModelDao.deleteAll();
        currentWeatherDbModelDao.insertInTx(currentWeatherDbModelList);
        return null;
    }
}
