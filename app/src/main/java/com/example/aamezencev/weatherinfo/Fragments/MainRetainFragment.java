package com.example.aamezencev.weatherinfo.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aamezencev.weatherinfo.Requests.GetCytyList;
import com.example.aamezencev.weatherinfo.ViewModels.ViewCityModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class MainRetainFragment extends Fragment {

    private List<ViewCityModel> viewCityModelList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        GetCytyList.instance(getActivity().getApplicationContext()).execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
