package com.example.aamezencev.weatherinfo;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.aamezencev.weatherinfo.Adapters.MainAdapter;
import com.example.aamezencev.weatherinfo.Adapters.MainDiffUtil;
import com.example.aamezencev.weatherinfo.Events.CityEvent;
import com.example.aamezencev.weatherinfo.Fragments.MainRetainFragment;
import com.example.aamezencev.weatherinfo.Recievers.CityReceiver;
import com.example.aamezencev.weatherinfo.Requests.GetCytyList;
import com.example.aamezencev.weatherinfo.ViewModels.ViewCityModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver cityReceiver;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View spinner;
    private Fragment mainRetainFragment;


    public MainActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cityReceiver = new CityReceiver();

        IntentFilter intentFilter = new IntentFilter(CityReceiver.CITY_RECEIVER_ID);
        registerReceiver(cityReceiver, intentFilter);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(cityReceiver);
        EventBus.getDefault().unregister(this);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        cityReceiver = null;
        mRecyclerView = null;
        mAdapter = null;
        mLayoutManager = null;

        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paintSpinner();

        Fragment fragment = getFragmentManager().findFragmentById(R.id.mainRetainFragment);

        if (fragment == null) {
            mainRetainFragment = new MainRetainFragment();

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainRetainFragment, mainRetainFragment);
            fragmentTransaction.commit();
        } else {
            sendBroadcast(new Intent(CityReceiver.CITY_RECEIVER_ID));
        }
    }

    private void paintSpinner() {
        FrameLayout frameLayout = findViewById(R.id.flSpinner);
        LayoutInflater layoutInflater = getLayoutInflater();
        spinner = layoutInflater.inflate(R.layout.spinner_layout, frameLayout, false);
        spinner.setVisibility(View.VISIBLE);
        frameLayout.addView(spinner);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paint(CityEvent cityEvent) {
        spinner.setVisibility(View.INVISIBLE);
        mRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MainAdapter(cityEvent.getViewCityModelList());

        mRecyclerView.setAdapter(mAdapter);
    }

}
