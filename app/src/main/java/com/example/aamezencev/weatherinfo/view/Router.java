package com.example.aamezencev.weatherinfo.view;

import android.content.Context;
import android.content.Intent;

import com.example.aamezencev.weatherinfo.UpdateService;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;

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

    @Override
    public void openWeatherInfoActivity(Long key, String actionTitle) {
        Intent intent = new Intent(context, WeatherInfoActivity.class);
        intent.putExtra("promptKey", key);
        intent.putExtra("actionTitle", actionTitle);
        context.startActivity(intent);
    }

    @Override
    public void closeWeatherInfoActivity() {
        ((WeatherInfoActivity)context).finish();
    }
}
