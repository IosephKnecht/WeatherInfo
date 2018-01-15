package com.example.aamezencev.weatherinfo.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aamezencev.weatherinfo.Adapters.WeatherInfoAdapter;
import com.example.aamezencev.weatherinfo.Adapters.WeatherListAdapter;
import com.example.aamezencev.weatherinfo.JsonModels.JsonResultsGeo;
import com.example.aamezencev.weatherinfo.JsonModels.OWMApi.JsonWeatherModel;
import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.Requests.CurrentWeatherByCityId;
import com.example.aamezencev.weatherinfo.Requests.GetCurrentWeather;
import com.example.aamezencev.weatherinfo.Requests.GetGeoToPlaceId;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by aa.mezencev on 15.01.2018.
 */

public class WeatherInfoRetainFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private JsonWeatherModel jsonWeatherModel=new JsonWeatherModel();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        String placeId = getArguments().getString("placeId");
        GetGeoToPlaceId getGeoToPlaceId = new GetGeoToPlaceId(placeId);
        getGeoToPlaceId.execute();
        JsonResultsGeo jsonResultsGeo = new JsonResultsGeo();
        try {
            jsonResultsGeo = getGeoToPlaceId.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String lat = jsonResultsGeo.getJsonLocationModel().getLat();
        String lng = jsonResultsGeo.getJsonLocationModel().getLng();

        GetCurrentWeather getCurrentWeather = new GetCurrentWeather(lat, lng);
        getCurrentWeather.execute();

        try {
            jsonWeatherModel = getCurrentWeather.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        paint();

    }

    public void paint() {
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.weatherInfoRecycler);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new WeatherInfoAdapter(jsonWeatherModel.getJsonWeatherInfoList());

        mRecyclerView.setAdapter(mAdapter);
    }
}
