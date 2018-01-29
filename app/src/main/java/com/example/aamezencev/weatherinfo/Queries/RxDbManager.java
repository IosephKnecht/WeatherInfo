package com.example.aamezencev.weatherinfo.Queries;

import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModelDao;
import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModelDao;
import com.example.aamezencev.weatherinfo.Mappers.ViewPromptCityModelToPromptCityDbModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aa.mezencev on 24.01.2018.
 */

public class RxDbManager {

    private DaoSession daoSession;

    public RxDbManager(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public Observable<List<PromptCityDbModel>> allItemQuery() {
        return Observable.<List<PromptCityDbModel>>create(aVoid -> {
            PromptCityDbModelDao promptCityDbModelDao = daoSession.getPromptCityDbModelDao();
            List<PromptCityDbModel> promptCityDbModelList = new ArrayList<>();
            QueryBuilder<PromptCityDbModel> queryBuilder = promptCityDbModelDao.queryBuilder();
            promptCityDbModelList = queryBuilder.list();
            aVoid.onNext(promptCityDbModelList);
            aVoid.onComplete();
        });
    }

    public Observable<List<PromptCityDbModel>> deleteItemOdDbQuery(Long key) {
        return Observable.create(aVoid -> {
            PromptCityDbModelDao promptCityDbModelDao = daoSession.getPromptCityDbModelDao();
            CurrentWeatherDbModelDao currentWeatherDbModelDao = daoSession.getCurrentWeatherDbModelDao();
            currentWeatherDbModelDao.deleteByKey(key);
            promptCityDbModelDao.deleteByKey(key);
            aVoid.onNext(promptCityDbModelDao);
            aVoid.onComplete();
        })
                .flatMap(aVoid -> allItemQuery());
    }

    public Observable<List<CurrentWeatherDbModel>> addListToDbQuery(List<CurrentWeatherDbModel> currentWeatherDbModelList) {
        return Observable.<List<CurrentWeatherDbModel>>create(e -> {
            CurrentWeatherDbModelDao currentWeatherDbModelDao = daoSession.getCurrentWeatherDbModelDao();
            currentWeatherDbModelDao.deleteAll();
            currentWeatherDbModelDao.insertInTx(currentWeatherDbModelList);
            e.onNext(currentWeatherDbModelList);
            e.onComplete();
        });
    }

    public Observable<CurrentWeatherDbModel> findWeatherByKey(Long key) {
        return Observable.<CurrentWeatherDbModel>create(aVoid -> {
            CurrentWeatherDbModelDao currentWeatherDbModelDao = daoSession.getCurrentWeatherDbModelDao();
            QueryBuilder<CurrentWeatherDbModel> queryBuilder = currentWeatherDbModelDao.queryBuilder();
            queryBuilder.where(CurrentWeatherDbModelDao.Properties.Key.eq(key));
            if (queryBuilder.list().size() == 0 || queryBuilder.list() == null) {
                aVoid.onNext(new CurrentWeatherDbModel());
            } else {
                aVoid.onNext(queryBuilder.list().get(0));
            }
            aVoid.onComplete();
        });
    }

    public Observable addPromptListViewToDb(List<ViewPromptCityModel> viewPromptCityModelList) {
        return Observable.create(e -> {
            List<PromptCityDbModel> promptCityDbModelList = new ArrayList<>();
            ViewPromptCityModelToPromptCityDbModel mapper = new ViewPromptCityModelToPromptCityDbModel(viewPromptCityModelList);
            promptCityDbModelList = mapper.map();
            PromptCityDbModelDao promptCityDbModelDao = daoSession.getPromptCityDbModelDao();
            CurrentWeatherDbModelDao currentWeatherDbModelDao = daoSession.getCurrentWeatherDbModelDao();
            promptCityDbModelDao.deleteAll();
            currentWeatherDbModelDao.deleteAll();
            promptCityDbModelDao.insertInTx(promptCityDbModelList);
            e.onNext(e);
            e.onComplete();
        });
    }

    public Observable<List<PromptCityDbModel>> addPromptListToDb(List<PromptCityDbModel> promptCityDbModelList) {
        return Observable.<List<PromptCityDbModel>>create(emitter -> {
            PromptCityDbModelDao promptCityDbModelDao = daoSession.getPromptCityDbModelDao();
            promptCityDbModelDao.insertInTx(promptCityDbModelList);
            emitter.onNext(promptCityDbModelDao.queryBuilder().list());
            emitter.onComplete();
        });
    }

}
