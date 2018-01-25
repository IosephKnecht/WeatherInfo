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

    private static RxDbManager rxDbManager;
    private static DaoSession daoSession;

    private RxDbManager() {
    }

    public static void setDaoSession(DaoSession inputDaoSession) {
        daoSession = inputDaoSession;
    }

    public static RxDbManager instance() {
        if (rxDbManager == null) rxDbManager = new RxDbManager();
        return rxDbManager;
    }

    public Observable<List<PromptCityDbModel>> allItemQuery() {
        return Observable.<List<PromptCityDbModel>>create(aVoid -> {
            PromptCityDbModelDao promptCityDbModelDao = daoSession.getPromptCityDbModelDao();
            List<PromptCityDbModel> promptCityDbModelList = new ArrayList<>();
            QueryBuilder<PromptCityDbModel> queryBuilder = promptCityDbModelDao.queryBuilder();
            promptCityDbModelList = queryBuilder.list();
            aVoid.onNext(promptCityDbModelList);
            aVoid.onComplete();
        }).subscribeOn(Schedulers.io());
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
                .flatMap(aVoid -> allItemQuery())
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<CurrentWeatherDbModel>> addListToDbQuery(List<CurrentWeatherDbModel> currentWeatherDbModelList) {
        return Observable.<List<CurrentWeatherDbModel>>create(e -> {
            CurrentWeatherDbModelDao currentWeatherDbModelDao = daoSession.getCurrentWeatherDbModelDao();
            currentWeatherDbModelDao.deleteAll();
            currentWeatherDbModelDao.insertInTx(currentWeatherDbModelList);
            e.onNext(currentWeatherDbModelList);
            e.onComplete();
        }).subscribeOn(Schedulers.io());
    }

    public Observable<CurrentWeatherDbModel> findWeatherByKey(Long key) {
        return Observable.<CurrentWeatherDbModel>create(aVoid -> {
            CurrentWeatherDbModelDao currentWeatherDbModelDao = daoSession.getCurrentWeatherDbModelDao();
            QueryBuilder<CurrentWeatherDbModel> queryBuilder = currentWeatherDbModelDao.queryBuilder();
            queryBuilder.where(CurrentWeatherDbModelDao.Properties.Key.eq(key));
            if (queryBuilder.list().size() == 0 || queryBuilder.list() == null) {
                aVoid.onNext(null);
            } else {
                aVoid.onNext(queryBuilder.list().get(0));
            }
            aVoid.onComplete();
        });
    }

    public Observable addPromptListToDb(List<ViewPromptCityModel> viewPromptCityModelList){
        return Observable.create(e->{
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

}
