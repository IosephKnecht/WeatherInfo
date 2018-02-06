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
public class RxDbManager {

    DaoSession daoSession;

    @Inject
    public RxDbManager(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public Observable<List<PromptCityDbModel>> allItemQuery() {
        return Observable.<List<PromptCityDbModel>>create(aVoid -> {
            PromptCityDbModelDao promptCityDbModelDao = daoSession.getPromptCityDbModelDao();
            try {
                aVoid.onNext(promptCityDbModelDao.queryBuilder().list());
            } catch (Exception ex) {
                aVoid.onError(new Exception("could not get data from DB"));
            }
            aVoid.onComplete();
        });
    }

    public Observable<List<PromptCityDbModel>> deleteItemOdDbQuery(Long key) {
        return Observable.create(aVoid -> {
            PromptCityDbModelDao promptCityDbModelDao = daoSession.getPromptCityDbModelDao();
            CurrentWeatherDbModelDao currentWeatherDbModelDao = daoSession.getCurrentWeatherDbModelDao();
            currentWeatherDbModelDao.deleteByKey(key);
            promptCityDbModelDao.deleteByKey(key);
            try {
                aVoid.onNext(promptCityDbModelDao);
            } catch (Exception ex) {
                aVoid.onError(new Exception("could not delete record from DB"));
            }
            aVoid.onComplete();
        })
                .flatMap(aVoid -> allItemQuery());
    }

    public Observable<List<CurrentWeatherDbModel>> addListToDbQuery(List<CurrentWeatherDbModel> currentWeatherDbModelList) {
        return Observable.<List<CurrentWeatherDbModel>>create(e -> {
            CurrentWeatherDbModelDao currentWeatherDbModelDao = daoSession.getCurrentWeatherDbModelDao();
            PromptCityDbModelDao promptCityDbModelDao = daoSession.getPromptCityDbModelDao();
            currentWeatherDbModelDao.deleteAll();
            promptCityDbModelDao.deleteAll();
            try {
                currentWeatherDbModelDao.insertInTx(currentWeatherDbModelList);
                e.onNext(currentWeatherDbModelList);
            } catch (Exception ex) {
                e.onError(new Exception("do not write an array in DB"));
            }
            e.onComplete();
        });
    }

    public Observable<List<CurrentWeatherDbModel>> findWeatherByKey(Long key) {
        return Observable.<List<CurrentWeatherDbModel>>create(aVoid -> {
            PromptCityDbModelDao promptCityDbModelDao = daoSession.getPromptCityDbModelDao();
            PromptCityDbModel promptCityDbModel = promptCityDbModelDao.load(key);
            if (promptCityDbModel != null && promptCityDbModel.getWeatherDbModelList() != null) {
                aVoid.onNext(promptCityDbModel.getWeatherDbModelList());
            } else {
                aVoid.onNext(new ArrayList<>());
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
            promptCityDbModelDao.deleteAll();
            try {
                promptCityDbModelDao.insertInTx(promptCityDbModelList);
                e.onNext(e);
            } catch (Exception ex) {
                e.onError(new Exception("do not write an array in DB"));
            }
            e.onComplete();
        });
    }

    public Observable<List<PromptCityDbModel>> addPromptListToDb(List<PromptCityDbModel> promptCityDbModelList) {
        return Observable.<List<PromptCityDbModel>>create(emitter -> {
            PromptCityDbModelDao promptCityDbModelDao = daoSession.getPromptCityDbModelDao();
            promptCityDbModelDao.deleteAll();
            try {
                promptCityDbModelDao.insertInTx(promptCityDbModelList);
                emitter.onNext(promptCityDbModelDao.queryBuilder().list());
            } catch (Exception ex) {
                emitter.onError(new Exception("do not write an array in DB"));
            }
            emitter.onComplete();
        });
    }

}
