package com.example.aamezencev.weatherinfo.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aamezencev.weatherinfo.R;

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
        finish();
    }

    private void readPreferences() {
        sharedPreferences = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);

        isFirstRun = sharedPreferences.getBoolean("state", true);

        if (isFirstRun) {
            isFirstRun = !isFirstRun;
            intent = new Intent(this, MainActivity.class);
            intent.putExtra("isFirstRun", isFirstRun);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            intent = new Intent(this, WeatherListActivity.class);
            intent.putExtra("isFirstRun", isFirstRun);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

//    public void writePreferences() {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("state", isFirstRun);
//        editor.commit();
//    }
}
