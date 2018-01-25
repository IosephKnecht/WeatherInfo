package com.example.aamezencev.weatherinfo;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.aamezencev.weatherinfo.Adapters.WeatherListAdapter;
import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.Events.UpdatedCurrentWeather;
import com.example.aamezencev.weatherinfo.Events.WeatherDeleteItemEvent;
import com.example.aamezencev.weatherinfo.Fragments.SettingsFragment;
import com.example.aamezencev.weatherinfo.Mappers.PromptCityDbModelToViewPromptCityModel;
import com.example.aamezencev.weatherinfo.Queries.RxDbManager;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class WeatherListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ViewPromptCityModel>> {
    private final String prefTag = "settingsPreference";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private List<PromptCityDbModel> promptCityDbModelList = new ArrayList<>();
    private List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        EventBus.getDefault().register(this);

        Intent intent = new Intent(this, UpdateService.class);

        boolean state = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("serviceSwitch", true);
        if (state) {
            startService(intent);
        } else {
            this.stopService(intent);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.weatherRecycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        getLoaderManager().initLoader(123, null, this);

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, UpdateService.class));
        EventBus.getDefault().unregister(this);
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
                Fragment fragment = getFragmentManager().findFragmentByTag(prefTag);
                if (fragment == null) {
                    fragment = new SettingsFragment();
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.weatherRetainFragment, fragment, prefTag).commit();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_info_menu, menu);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paint(WeatherDeleteItemEvent weatherDeleteItemEvent) {
        getLoaderManager().restartLoader(123, null, this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updatedCurrentWeather(UpdatedCurrentWeather updatedCurrentWeather) {
        int i = -1;
        for (CurrentWeatherDbModel dbModel : updatedCurrentWeather.getCurrentWeatherDbModelList()) {
            i++;
            String briefInformation = new String();
            briefInformation += "weather: " + dbModel.getMain() + " " + dbModel.getDescription();
            viewPromptCityModelList.get(i).setBriefInformation(briefInformation);
        }
        paint();
    }

    public void paint() {
        mAdapter = new WeatherListAdapter(viewPromptCityModelList, promptCityDbModelList);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public Loader<List<ViewPromptCityModel>> onCreateLoader(int i, Bundle bundle) {
        android.content.Loader loader = null;
        if (i == 123) {
            loader = new MyLoader(this);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<ViewPromptCityModel>> loader, List<ViewPromptCityModel> viewPromptCityModelList) {
        this.viewPromptCityModelList = viewPromptCityModelList;
        this.promptCityDbModelList = ((MyLoader) loader).getPromptCityDbModelList();
        paint();
    }

    @Override
    public void onLoaderReset(Loader<List<ViewPromptCityModel>> loader) {

    }


    private static class MyLoader extends android.content.Loader<List<ViewPromptCityModel>> {

        private Context context;
        private List<ViewPromptCityModel> viewPromptCityModelList;
        private List<PromptCityDbModel> promptCityDbModelList;
        private CompositeDisposable compositeDisposable;

        public MyLoader(Context context) {
            super(context);
            this.context = context;
            compositeDisposable = new CompositeDisposable();
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (viewPromptCityModelList == null) forceLoad();
        }

        @Override
        public void forceLoad() {
            super.forceLoad();
            promptCityDbModelList = new ArrayList<>();
            RxDbManager.setDaoSession(((App) context.getApplicationContext()).getDaoSession());
            compositeDisposable.add(RxDbManager.instance().allItemQuery()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> {
                        promptCityDbModelList.addAll(list);
                        PromptCityDbModelToViewPromptCityModel mapper = new PromptCityDbModelToViewPromptCityModel(promptCityDbModelList);
                        viewPromptCityModelList = new ArrayList<>();
                        viewPromptCityModelList = mapper.map();
                        deliverResult(viewPromptCityModelList);
                    }));
        }

        @Override
        protected void onReset() {
            super.onReset();
            viewPromptCityModelList = null;
            compositeDisposable.dispose();
        }

        public List<PromptCityDbModel> getPromptCityDbModelList() {
            return promptCityDbModelList;
        }
    }
}
