package com.example.aamezencev.weatherinfo.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aamezencev.weatherinfo.Adapters.MainAdapter;
import com.example.aamezencev.weatherinfo.Events.CityEvent;
import com.example.aamezencev.weatherinfo.JsonModels.JsonPromptModel;
import com.example.aamezencev.weatherinfo.Mappers.JsonPromptModelToViewPromptModel;
import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.Requests.GetCytyList;
import com.example.aamezencev.weatherinfo.Requests.PromptRequest;
import com.example.aamezencev.weatherinfo.ViewModels.ViewCityModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class MainRetainFragment extends Fragment {

    private List<ViewPromptCityModel> viewCityModelList = new ArrayList<>();
    private PromptRequest promptRequest;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        PromptRequest.destroySingleton();
        promptRequest = PromptRequest.instance(getActivity().getApplicationContext());
        promptRequest.setCity(getArguments().get("city").toString());
        promptRequest.execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paint(CityEvent cityEvent) {

        View view=getActivity().findViewById(R.id.spinner_view);
        view.setVisibility(View.INVISIBLE);

        viewCityModelList=cityEvent.getViewPromptCityModelList();

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.mainRecyclerView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MainAdapter(cityEvent.getViewPromptCityModelList());

        mRecyclerView.setAdapter(mAdapter);
    }

    public void paint() {
        View view=getActivity().findViewById(R.id.spinner_view);
        view.setVisibility(View.INVISIBLE);

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.mainRecyclerView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MainAdapter(viewCityModelList);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
