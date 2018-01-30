package com.example.aamezencev.weatherinfo;

import android.content.Context;
import android.content.Intent;

import com.example.aamezencev.weatherinfo.inrerfaces.view.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.WeatherListActivity;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public class Router implements IBaseRouter {
    private Context context;

    public Router(Context context) {
        this.context = context;
    }

    @Override
    public void openWeatherListActivity() {
        Intent intent = new Intent(context, WeatherListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void startUpdateService() {
        Intent intent = new Intent(context, UpdateService.class);
        context.startService(intent);
    }
}
