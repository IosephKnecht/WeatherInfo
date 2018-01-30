package com.example.aamezencev.weatherinfo.domain;

import android.support.annotation.NonNull;

import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.data.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.data.CurrentWeatherDbModelDao;
import com.example.aamezencev.weatherinfo.data.DaoSession;
import com.example.aamezencev.weatherinfo.data.PromptCityDbModelDao;
import com.example.aamezencev.weatherinfo.data.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.domain.mappers.ViewPromptCityModelToPromptCityDbModel;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;

/**
 * Created by aa.mezencev on 24.01.2018.
 */
@Module
public class RxDbManager {

    @Inject DaoSession daoSession;

    public RxDbManager() {

    }

    public Observable<List<PromptCityDbModel>> allItemQuery() {
        App.getAppComponent().inject(this);
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
        App.getAppComponent().inject(this);
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
        App.getAppComponent().inject(this);
        return Observable.<List<CurrentWeatherDbModel>>create(e -> {
            CurrentWeatherDbModelDao currentWeatherDbModelDao = daoSession.getCurrentWeatherDbModelDao();
            currentWeatherDbModelDao.deleteAll();
            currentWeatherDbModelDao.insertInTx(currentWeatherDbModelList);
            e.onNext(currentWeatherDbModelList);
            e.onComplete();
        });
    }

    public Observable<CurrentWeatherDbModel> findWeatherByKey(Long key) {
        App.getAppComponent().inject(this);
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
        App.getAppComponent().inject(this);
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
        App.getAppComponent().inject(this);
        return Observable.<List<PromptCityDbModel>>create(emitter -> {
            PromptCityDbModelDao promptCityDbModelDao = daoSession.getPromptCityDbModelDao();
            promptCityDbModelDao.insertInTx(promptCityDbModelList);
            emitter.onNext(promptCityDbModelDao.queryBuilder().list());
            emitter.onComplete();
        });
    }

    @Provides
    @NonNull
    @Singleton
    public RxDbManager getDbManager() {
        return new RxDbManager();
    }

}
