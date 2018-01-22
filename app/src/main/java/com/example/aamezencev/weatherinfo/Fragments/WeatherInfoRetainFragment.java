package com.example.aamezencev.weatherinfo.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aamezencev.weatherinfo.Adapters.WeatherInfoAdapter;
import com.example.aamezencev.weatherinfo.Adapters.WeatherListAdapter;
import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;
import com.example.aamezencev.weatherinfo.JsonModels.JsonResultsGeo;
import com.example.aamezencev.weatherinfo.JsonModels.OWMApi.JsonWeatherModel;
import com.example.aamezencev.weatherinfo.Mappers.CurrentWeatherDbModelToView;
import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.Requests.CurrentWeatherByCityId;
import com.example.aamezencev.weatherinfo.Requests.FindWeatherByKey;
import com.example.aamezencev.weatherinfo.Requests.GetCurrentWeather;
import com.example.aamezencev.weatherinfo.Requests.GetGeoToPlaceId;
import com.example.aamezencev.weatherinfo.ViewModels.ViewCurrentWeatherModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by aa.mezencev on 15.01.2018.
 */

public class WeatherInfoRetainFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ViewCurrentWeatherModel viewCurrentWeatherModel = new ViewCurrentWeatherModel();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Long key = getArguments().getLong("promptKey");
        DaoSession daoSession = ((App) getActivity().getApplicationContext()).getDaoSession();
        FindWeatherByKey findWeatherByKey = new FindWeatherByKey(key, daoSession);
        findWeatherByKey.execute();
        CurrentWeatherDbModel currentWeatherDbModel = new CurrentWeatherDbModel();
        try {
            currentWeatherDbModel = findWeatherByKey.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        CurrentWeatherDbModelToView mapper = new CurrentWeatherDbModelToView(currentWeatherDbModel);
        viewCurrentWeatherModel = mapper.map();
//        GetGeoToPlaceId getGeoToPlaceId = new GetGeoToPlaceId(placeId);
//        getGeoToPlaceId.execute();
//        JsonResultsGeo jsonResultsGeo = new JsonResultsGeo();
//        try {
//            jsonResultsGeo = getGeoToPlaceId.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        String lat = jsonResultsGeo.getJsonLocationModel().getLat();
//        String lng = jsonResultsGeo.getJsonLocationModel().getLng();
//
//        GetCurrentWeather getCurrentWeather = new GetCurrentWeather(lat, lng);
//        getCurrentWeather.execute();
//
//        try {
//            jsonWeatherModel = getCurrentWeather.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        paint();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getArguments().getString("actionTitle"));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void paint() {
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.weatherInfoRecycler);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new WeatherInfoAdapter(viewCurrentWeatherModel);

        mRecyclerView.setAdapter(mAdapter);
    }
}
