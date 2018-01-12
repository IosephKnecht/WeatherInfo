package com.example.aamezencev.weatherinfo;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.aamezencev.weatherinfo.Fragments.MainRetainFragment;
import com.example.aamezencev.weatherinfo.Fragments.WeatherListRetainFragment;

public class WeatherListActivity extends AppCompatActivity {

    private WeatherListRetainFragment weatherListRetainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);
        createWeatherRetainFragment();
    }

    private void createWeatherRetainFragment() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.weatherRetainFragment);

        if (fragment == null) {
            weatherListRetainFragment = new WeatherListRetainFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.weatherRetainFragment, weatherListRetainFragment);
            fragmentTransaction.commit();
        } else {
            ((WeatherListRetainFragment)fragment).paint();
        }
    }
}
