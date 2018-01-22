package com.example.aamezencev.weatherinfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityController extends AppCompatActivity {
    private boolean isFirstRun;
    private final String SETTINGS = "isFirstRun";
    private SharedPreferences sharedPreferences;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        readPreferences();
        writePreferences();
        finish();
    }

    private void readPreferences() {
        sharedPreferences = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);

        isFirstRun = sharedPreferences.getBoolean("state", true);

        if (isFirstRun) {
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            isFirstRun = !isFirstRun;
        } else {
            intent = new Intent(this, WeatherListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void writePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("state", isFirstRun);
        editor.commit();
    }
}
