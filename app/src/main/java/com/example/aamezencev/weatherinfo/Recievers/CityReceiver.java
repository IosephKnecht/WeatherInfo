package com.example.aamezencev.weatherinfo.Recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.Events.CityEvent;
import com.example.aamezencev.weatherinfo.JsonModels.JsonCityModel;
import com.example.aamezencev.weatherinfo.JsonModels.JsonPromptCityModel;
import com.example.aamezencev.weatherinfo.JsonModels.JsonPromptModel;
import com.example.aamezencev.weatherinfo.MainActivity;
import com.example.aamezencev.weatherinfo.Mappers.JsonCityModelToCityDbModel;
import com.example.aamezencev.weatherinfo.Mappers.JsonCityModelToViewCityModel;
import com.example.aamezencev.weatherinfo.Mappers.JsonPromptModelToViewPromptModel;
import com.example.aamezencev.weatherinfo.Requests.GetCytyList;
import com.example.aamezencev.weatherinfo.Requests.PromptRequest;
import com.example.aamezencev.weatherinfo.ViewModels.ViewCityModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class CityReceiver extends BroadcastReceiver {

    public static final String CITY_RECEIVER_ID = "cityReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        JsonPromptModel jsonPromptModel= new JsonPromptModel();
        try {
            jsonPromptModel=PromptRequest.instance(context).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JsonPromptModelToViewPromptModel jsonPromptModelToViewPromptModel = new JsonPromptModelToViewPromptModel(jsonPromptModel);
        ViewPromptModel viewPromptModel = new ViewPromptModel();
        viewPromptModel = jsonPromptModelToViewPromptModel.map();

        EventBus.getDefault().post(new CityEvent(viewPromptModel.getViewPromptCityModelList()));
    }
}
