package com.example.aamezencev.weatherinfo;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.example.aamezencev.weatherinfo.Fragments.WeatherInfoRetainFragment;
import com.example.aamezencev.weatherinfo.Queries.DeleteItemOfDb;

public class WeatherInfoActivity extends AppCompatActivity {

    private WeatherInfoRetainFragment weatherInfoRetainFragment;
    private ImageButton btnDeleteWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        btnDeleteWeather = (ImageButton) findViewById(R.id.btnDeleteWeather);
        btnDeleteWeather.setOnClickListener(btn -> {
            DeleteItemOfDb deleteItemOfDb = new DeleteItemOfDb(getIntent().getLongExtra("promptKey", 0),
                    ((App) getApplicationContext()).getDaoSession());
            deleteItemOfDb.execute();
            finish();
        });

        Fragment fragment = getFragmentManager().findFragmentById(R.id.weatherInfoRetainFragment);

        if (fragment == null) {
            weatherInfoRetainFragment = new WeatherInfoRetainFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("promptKey", getIntent().getLongExtra("promptKey", 0));
            bundle.putString("actionTitle", getIntent().getStringExtra("actionTitle"));
            weatherInfoRetainFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.weatherInfoRetainFragment,
                    weatherInfoRetainFragment).commit();
        } else {
            ((WeatherInfoRetainFragment) fragment).paint();
        }

    }
}
