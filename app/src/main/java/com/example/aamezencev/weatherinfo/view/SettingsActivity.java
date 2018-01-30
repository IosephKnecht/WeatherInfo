package com.example.aamezencev.weatherinfo.view;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aamezencev.weatherinfo.fragments.SettingsFragment;
import com.example.aamezencev.weatherinfo.R;

public class SettingsActivity extends AppCompatActivity {
    private final String prefTag = "settingsPreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Fragment fragment = getFragmentManager().findFragmentByTag(prefTag);
        if (fragment == null) {
            fragment = new SettingsFragment();
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.settingsFragment, fragment, prefTag).commit();
        }
    }
}
