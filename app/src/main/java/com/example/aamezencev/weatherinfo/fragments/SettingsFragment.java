package com.example.aamezencev.weatherinfo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;

import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.UpdateService;

/**
 * Created by aa.mezencev on 22.01.2018.
 */

public class SettingsFragment extends PreferenceFragment {
    private SwitchPreference serviceSwitch;
    private EditTextPreference etDelayService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);
        serviceSwitch = (SwitchPreference) findPreference("serviceSwitch");
        etDelayService = (EditTextPreference) findPreference("etDelayService");
        Intent intent = new Intent(getActivity(), UpdateService.class);

        serviceSwitch.setOnPreferenceClickListener(pref -> {
            boolean state = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("serviceSwitch", true);
            if (state) {
                getActivity().startService(intent);
            } else {
                getActivity().stopService(intent);
            }
            return state;
        });

        etDelayService.setOnPreferenceChangeListener(((preference, o) -> {
            getActivity().startService(intent);
            ((EditTextPreference) preference).setText(o.toString());
            return false;
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
