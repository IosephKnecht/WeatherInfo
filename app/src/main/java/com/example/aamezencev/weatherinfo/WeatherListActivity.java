package com.example.aamezencev.weatherinfo;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.aamezencev.weatherinfo.Fragments.SettingsFragment;
import com.example.aamezencev.weatherinfo.Fragments.WeatherListRetainFragment;

public class WeatherListActivity extends AppCompatActivity {

    private WeatherListRetainFragment weatherListRetainFragment;
    private final String prefTag = "settingsPreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        Intent intent = new Intent(this, UpdateService.class);

        boolean state = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("serviceSwitch", true);
        if (state) {
            startService(intent);
        } else {
            this.stopService(intent);
        }

        createWeatherRetainFragment();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, UpdateService.class));
        super.onDestroy();
    }

    private void createWeatherRetainFragment() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.weatherRetainFragment);

        if (fragment == null) {
            weatherListRetainFragment = new WeatherListRetainFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.weatherRetainFragment, weatherListRetainFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.returnHomeItem:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.settingsItem:
                Fragment fragment = getFragmentManager().findFragmentByTag(prefTag);
                if (fragment == null) {
                    fragment = new SettingsFragment();
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.weatherRetainFragment, fragment, prefTag).commit();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_info_menu, menu);
        return true;
    }
}
