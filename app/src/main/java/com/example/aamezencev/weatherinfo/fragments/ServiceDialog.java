package com.example.aamezencev.weatherinfo.fragments;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.view.SettingsActivity;

/**
 * Created by aa.mezencev on 09.02.2018.
 */

public class ServiceDialog extends DialogFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_dialog, null);
        Button btnServiceStart = view.findViewById(R.id.btnDialogStart);
        Button btnServiceStop = view.findViewById(R.id.btnDialogStop);

        btnServiceStart.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            getDialog().dismiss();
            getActivity().startActivity(intent);
        });

        btnServiceStop.setOnClickListener(v -> {
            getDialog().dismiss();
        });

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
