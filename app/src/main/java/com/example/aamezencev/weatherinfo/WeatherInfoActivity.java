package com.example.aamezencev.weatherinfo;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.aamezencev.weatherinfo.Fragments.WeatherInfoRetainFragment;

public class WeatherInfoActivity extends AppCompatActivity {

    private WeatherInfoRetainFragment weatherInfoRetainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);
        Fragment fragment = getFragmentManager().findFragmentById(R.id.weatherInfoRetainFragment);

        if (fragment == null) {
            weatherInfoRetainFragment = new WeatherInfoRetainFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("key", getIntent().getLongExtra("key", 0));
            weatherInfoRetainFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.weatherInfoRetainFragment,
                    weatherInfoRetainFragment).commit();
        } else {
            ((WeatherInfoRetainFragment) fragment).paint();
        }

    }
}
