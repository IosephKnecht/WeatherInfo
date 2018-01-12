package com.example.aamezencev.weatherinfo.Events;

import android.content.Context;
import android.os.AsyncTask;

import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModelDao;
import com.example.aamezencev.weatherinfo.Mappers.ViewPromptCityModelToPromptCityDbModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class FloatingButtonEventDb extends AsyncTask<Void, Void, Void> {
    private List<ViewPromptCityModel> viewPromptCityModelList;
    private DaoSession daoSession;

    public FloatingButtonEventDb(List<ViewPromptCityModel> viewPromptCityModelList, Context context) {
        this.viewPromptCityModelList = viewPromptCityModelList;
        daoSession = ((App) context.getApplicationContext()).getDaoSession();
    }

    public List<ViewPromptCityModel> getViewPromptCityModelList() {
        return viewPromptCityModelList;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        List<PromptCityDbModel> promptCityDbModelList = new ArrayList<>();
        ViewPromptCityModelToPromptCityDbModel mapper = new ViewPromptCityModelToPromptCityDbModel(viewPromptCityModelList);
        promptCityDbModelList = mapper.map();
        PromptCityDbModelDao promptCityDbModelDao=daoSession.getPromptCityDbModelDao();
        promptCityDbModelDao.deleteAll();
        promptCityDbModelDao.insertInTx(promptCityDbModelList);

        return null;
    }
}
