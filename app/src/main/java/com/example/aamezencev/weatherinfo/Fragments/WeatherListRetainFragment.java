package com.example.aamezencev.weatherinfo.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aamezencev.weatherinfo.Adapters.MainAdapter;
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

public class WeatherListRetainFragment extends Fragment {
    private List<PromptCityDbModel> promptCityDbModelList;
    private List<ViewPromptCityModel> viewPromptCityModelList;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onStart() {
        super.onStart();
    }

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
        setRetainInstance(true);
        EventBus.getDefault().register(this);
        AllItemQuery allItemQuery = new AllItemQuery(((App) getActivity().getApplicationContext()).getDaoSession());
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
        paint();
    }

    public void paint() {
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.weatherRecycler);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new WeatherListAdapter(viewPromptCityModelList, promptCityDbModelList);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paint(WeatherDeleteItemEvent weatherDeleteItemEvent) {
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.weatherRecycler);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

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


}
