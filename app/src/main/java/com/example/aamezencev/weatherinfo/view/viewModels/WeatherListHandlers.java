package com.example.aamezencev.weatherinfo.view.viewModels;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.aamezencev.weatherinfo.view.adapters.RecyclerItemTouchHelper;
import com.example.aamezencev.weatherinfo.view.adapters.WeatherListAdapter;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.presenters.IWeatherInfoPresenter;
import com.example.aamezencev.weatherinfo.view.presenters.IWeatherListPresenter;

/**
 * Created by aa.mezencev on 13.02.2018.
 */

public class WeatherListHandlers {
    private IWeatherListPresenter presenter;
    private IBaseRouter baseRouter;

    public WeatherListHandlers(IWeatherListPresenter presenter, IBaseRouter baseRouter) {
        this.baseRouter = baseRouter;
        this.presenter = presenter;
    }

    public void weatherItemClick(View view, Long key, String actionTitle) {
        baseRouter.openWeatherInfoActivity(key, actionTitle);
    }
}
