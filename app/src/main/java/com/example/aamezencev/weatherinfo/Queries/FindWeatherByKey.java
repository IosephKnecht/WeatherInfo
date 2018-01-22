package com.example.aamezencev.weatherinfo.Queries;

import android.os.AsyncTask;

import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModelDao;
import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModelDao;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by aa.mezencev on 19.01.2018.
 */

public class FindWeatherByKey extends AsyncTask<Void, Void, CurrentWeatherDbModel> {
    private Long key;
    private DaoSession daoSession;

    public FindWeatherByKey(Long key, DaoSession daoSession) {
        this.key = key;
        this.daoSession = daoSession;
    }

    @Override
    protected CurrentWeatherDbModel doInBackground(Void... voids) {
        CurrentWeatherDbModelDao currentWeatherDbModelDao = daoSession.getCurrentWeatherDbModelDao();
        QueryBuilder < CurrentWeatherDbModel > queryBuilder = currentWeatherDbModelDao.queryBuilder();
        queryBuilder.where(CurrentWeatherDbModelDao.Properties.Key.eq(key));
        if (queryBuilder.list().size() == 0 || queryBuilder.list() == null) {
            return null;
        } else {
            return queryBuilder.list().get(0);
        }
    }
}
