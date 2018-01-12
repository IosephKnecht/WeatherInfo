package com.example.aamezencev.weatherinfo.Queries;

import android.os.AsyncTask;

import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModelDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class AllItemQuery extends AsyncTask<Void, Void, List<PromptCityDbModel>> {

    private DaoSession daoSession;

    public AllItemQuery(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    protected List<PromptCityDbModel> doInBackground(Void... voids) {
        PromptCityDbModelDao promptCityDbModelDao = daoSession.getPromptCityDbModelDao();
        List<PromptCityDbModel> promptCityDbModelList = new ArrayList<>();
        QueryBuilder<PromptCityDbModel> queryBuilder = promptCityDbModelDao.queryBuilder();
        promptCityDbModelList = queryBuilder.list();
        return promptCityDbModelList;
    }
}
