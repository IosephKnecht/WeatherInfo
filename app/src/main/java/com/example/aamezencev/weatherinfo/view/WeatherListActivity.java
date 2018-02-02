package com.example.aamezencev.weatherinfo.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.UpdateService;
import com.example.aamezencev.weatherinfo.data.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.events.UpdatedCurrentWeather;
import com.example.aamezencev.weatherinfo.events.WeatherDeleteItemEvent;
import com.example.aamezencev.weatherinfo.view.interfaces.DeleteBtnClick;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseActivity;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.interfaces.IWeatherListActivity;
import com.example.aamezencev.weatherinfo.view.interfaces.WeatherItemClick;
import com.example.aamezencev.weatherinfo.view.presenters.IMainPresenter;
import com.example.aamezencev.weatherinfo.view.adapters.DiffUtilWeatherListAdapter;
import com.example.aamezencev.weatherinfo.view.adapters.WeatherListAdapter;
import com.example.aamezencev.weatherinfo.view.presenters.IWeatherListPresenter;
import com.example.aamezencev.weatherinfo.view.presenters.MainActivityPresenter;
import com.example.aamezencev.weatherinfo.view.presenters.WeatherListPresenter;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class WeatherListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<IWeatherListPresenter>,
        WeatherItemClick, DeleteBtnClick, IWeatherListActivity {
    private RecyclerView mRecyclerView;
    private WeatherListAdapter mAdapter;

    private CompositeDisposable compositeDisposable;

    private IWeatherListPresenter weatherListPresenter;
    private IBaseRouter baseRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        baseRouter = new Router(this);
        weatherListPresenter = ((SaveWeatherListPresenter) getLoaderManager().initLoader(123, null, this)).getPresenter();
        weatherListPresenter.onAttachView(this, baseRouter);

        compositeDisposable = new CompositeDisposable();
        //Intent intent = new Intent(this, UpdateService.class);

//        boolean state = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("serviceSwitch", true);
//        if (state) {
//            this.startService(intent);
//        } else {
//            this.stopService(intent);
//        }

//        baseRouter.startUpdateService();

        mRecyclerView = (RecyclerView) findViewById(R.id.weatherRecycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new WeatherListAdapter(new ArrayList<>(), this, this);
        mRecyclerView.setAdapter(mAdapter);

        EventBus.getDefault().register(this);
        baseRouter.startUpdateService();
    }

    @Override
    protected void onDestroy() {
        weatherListPresenter.onDetachView();
        EventBus.getDefault().unregister(this);
        stopService(new Intent(this, UpdateService.class));
        compositeDisposable.dispose();
        baseRouter = null;
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.returnHomeItem:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.settingsItem:
                Intent settIntent = new Intent(this, SettingsActivity.class);
                startActivity(settIntent);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_info_menu, menu);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paint(WeatherDeleteItemEvent weatherDeleteItemEvent) {
        weatherListPresenter.getPromptCityDbModelList();
    }

    public void updateRecyclerView(List<ViewPromptCityModel> newList) {
        DiffUtilWeatherListAdapter diffUtilWeatherListAdapter = new DiffUtilWeatherListAdapter(mAdapter.getViewPromptCityModelList(), newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilWeatherListAdapter);
        mAdapter.setViewPromptCityModelList(newList);
        diffResult.dispatchUpdatesTo(mAdapter);
    }

    @Override
    public Loader<IWeatherListPresenter> onCreateLoader(int i, Bundle bundle) {
        android.content.Loader loader = null;
        if (i == 123) {
            IWeatherListPresenter presenter = new WeatherListPresenter();
            loader = new SaveWeatherListPresenter(this, presenter);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<IWeatherListPresenter> loader, IWeatherListPresenter weatherListPresenter) {
        weatherListPresenter.getHashList();
        this.weatherListPresenter = weatherListPresenter;
    }

    @Override
    public void onLoaderReset(Loader<IWeatherListPresenter> loader) {

    }

    @Override
    public void weatherItemClick(View view, Long key, String actionTitle) {
        baseRouter.openWeatherInfoActivity(key, actionTitle);
    }

    @Override
    public void deleteBtnClick(View view, Long key) {
        weatherListPresenter.deleteItemAsDb(key);
    }

    @Override
    public void paintList(List viewModelList) {
        updateRecyclerView(viewModelList);
    }

    @Subscribe
    public void updatedCurrentWeather(UpdatedCurrentWeather updatedCurrentWeather) {
        if (weatherListPresenter != null) weatherListPresenter.getPromptCityDbModelList();
    }

    private static class SaveWeatherListPresenter extends Loader<IWeatherListPresenter> {

        private IWeatherListPresenter weatherListPresenter;
        private boolean isNewLoader;

        public SaveWeatherListPresenter(Context context, IWeatherListPresenter weatherListPresenter) {
            super(context);
            this.weatherListPresenter = weatherListPresenter;
            isNewLoader = true;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (isNewLoader) {
                weatherListPresenter.getPromptCityDbModelList();
                isNewLoader = false;
            }
            deliverResult(weatherListPresenter);
        }

        @Override
        protected void onReset() {
            super.onReset();
            weatherListPresenter.onDestroy();
            weatherListPresenter = null;
        }

        private IWeatherListPresenter getPresenter() {
            return weatherListPresenter;
        }
    }
}
