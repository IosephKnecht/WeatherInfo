package com.example.aamezencev.weatherinfo.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aamezencev.weatherinfo.Adapters.WeatherListAdapter;
import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.Events.UpdatedCurrentWeather;
import com.example.aamezencev.weatherinfo.Events.WeatherDeleteItemEvent;
import com.example.aamezencev.weatherinfo.Mappers.PromptCityDbModelToViewPromptCityModel;
import com.example.aamezencev.weatherinfo.Queries.AllItemQuery;
import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class WeatherListRetainFragment extends Fragment implements android.app.LoaderManager.LoaderCallbacks<List<ViewPromptCityModel>> {
    private List<PromptCityDbModel> promptCityDbModelList = new ArrayList<>();
    private List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(123, null, this);

        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.weather_recycler, container, false);
        mRecyclerView = view.findViewById(R.id.weatherRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        paint();

        return view;
    }

    public void paint() {
        mAdapter = new WeatherListAdapter(viewPromptCityModelList, promptCityDbModelList);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paint(WeatherDeleteItemEvent weatherDeleteItemEvent) {
        this.promptCityDbModelList = weatherDeleteItemEvent.getPromptCityDbModelList();
        this.viewPromptCityModelList = weatherDeleteItemEvent.getViewCityModelList();

        mAdapter = new WeatherListAdapter(weatherDeleteItemEvent.getViewCityModelList(), weatherDeleteItemEvent.getPromptCityDbModelList());

        mRecyclerView.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updatedCurrentWeather(UpdatedCurrentWeather updatedCurrentWeather) {
        int i = -1;
        for (CurrentWeatherDbModel dbModel : updatedCurrentWeather.getCurrentWeatherDbModelList()) {
            i++;
            String briefInformation = new String();
            briefInformation += "weather: " + dbModel.getMain() + " " + dbModel.getDescription();
            viewPromptCityModelList.get(i).setBriefInformation(briefInformation);
            paint();
        }
    }

    @Override
    public android.content.Loader onCreateLoader(int i, Bundle bundle) {
        android.content.Loader loader = null;
        if (i == 123) {
            loader = new MyLoader(getActivity());
        }
        return loader;
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<ViewPromptCityModel>> loader, List<ViewPromptCityModel> viewPromptCityModels) {
        this.viewPromptCityModelList = viewPromptCityModels;
        this.promptCityDbModelList = ((MyLoader) loader).getPromptCityDbModelList();
        paint();
    }

    @Override
    public void onLoaderReset(android.content.Loader loader) {

    }

    public static class MyLoader extends android.content.AsyncTaskLoader<List<ViewPromptCityModel>> {

        private Context context;
        private List<ViewPromptCityModel> viewPromptCityModelList;
        private List<PromptCityDbModel> promptCityDbModelList;

        public MyLoader(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        public List<ViewPromptCityModel> loadInBackground() {
            AllItemQuery allItemQuery = new AllItemQuery(((App) context.getApplicationContext()).getDaoSession());
            allItemQuery.execute();
            promptCityDbModelList = new ArrayList<>();
            try {
                promptCityDbModelList = allItemQuery.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            PromptCityDbModelToViewPromptCityModel mapper = new PromptCityDbModelToViewPromptCityModel(promptCityDbModelList);
            viewPromptCityModelList = new ArrayList<>();
            viewPromptCityModelList = mapper.map();
            allItemQuery.cancel(true);
            return viewPromptCityModelList;
        }

        @Override
        protected void onStartLoading() {
            if (viewPromptCityModelList == null) forceLoad();
        }

        public List<PromptCityDbModel> getPromptCityDbModelList() {
            return promptCityDbModelList;
        }
    }


}
