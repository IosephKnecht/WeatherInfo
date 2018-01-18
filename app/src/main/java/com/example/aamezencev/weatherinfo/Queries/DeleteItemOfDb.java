package com.example.aamezencev.weatherinfo.Queries;

import android.os.AsyncTask;

import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModelDao;
import com.example.aamezencev.weatherinfo.Events.WeatherDeleteItemEvent;
import com.example.aamezencev.weatherinfo.Mappers.PromptCityDbModelToViewPromptCityModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class DeleteItemOfDb extends AsyncTask<Void, Void, Void> {
    private Long structureFormattingId;
    private DaoSession daoSession;

    public DeleteItemOfDb(Long structureFormattingId, DaoSession daoSession) {
        this.structureFormattingId = structureFormattingId;
        this.daoSession = daoSession;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        PromptCityDbModelDao promptCityDbModelDao = daoSession.getPromptCityDbModelDao();
        QueryBuilder<PromptCityDbModel> queryBuilder = promptCityDbModelDao.queryBuilder();
        queryBuilder.where(PromptCityDbModelDao.Properties.Key.eq(structureFormattingId));
        if (queryBuilder.list().size() != 0)
            promptCityDbModelDao.delete(queryBuilder.list().get(0));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        AllItemQuery allItemQuery=new AllItemQuery(daoSession);
        allItemQuery.execute();
        List<PromptCityDbModel> promptCityDbModelList=new ArrayList<>();
        try {
            promptCityDbModelList=allItemQuery.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        PromptCityDbModelToViewPromptCityModel mapper=new PromptCityDbModelToViewPromptCityModel(promptCityDbModelList);
        WeatherDeleteItemEvent weatherDeleteItemEvent=new WeatherDeleteItemEvent(mapper.map(),promptCityDbModelList);
        EventBus.getDefault().post(weatherDeleteItemEvent);

    }
}
