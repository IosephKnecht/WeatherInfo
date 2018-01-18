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
            bundle.putString("placeId", getIntent().getStringExtra("placeId"));
            weatherInfoRetainFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.weatherInfoRetainFragment,
                    weatherInfoRetainFragment).commit();
        } else {
            ((WeatherInfoRetainFragment) fragment).paint();
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
