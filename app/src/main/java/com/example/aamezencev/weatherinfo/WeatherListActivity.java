package com.example.aamezencev.weatherinfo;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.aamezencev.weatherinfo.Adapters.DiffUtilWeatherListAdapter;
import com.example.aamezencev.weatherinfo.Adapters.WeatherListAdapter;
import com.example.aamezencev.weatherinfo.DaoModels.CurrentWeatherDbModel;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.Events.UpdatedCurrentWeather;
import com.example.aamezencev.weatherinfo.Events.WeatherDeleteItemEvent;
import com.example.aamezencev.weatherinfo.Fragments.SettingsFragment;
import com.example.aamezencev.weatherinfo.Inrerfaces.DeleteBtnClick;
import com.example.aamezencev.weatherinfo.Inrerfaces.WeatherItemClick;
import com.example.aamezencev.weatherinfo.Mappers.PromptCityDbModelToViewPromptCityModel;
import com.example.aamezencev.weatherinfo.Queries.RxDbManager;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ViewPromptCityModel>>,
        WeatherItemClick, DeleteBtnClick {
    //private final String prefTag = "settingsPreference";

    private RecyclerView mRecyclerView;
    private WeatherListAdapter mAdapter;

    private List<PromptCityDbModel> promptCityDbModelList = new ArrayList<>();
    private List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        compositeDisposable = new CompositeDisposable();

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

        paint();
        getLoaderManager().initLoader(123, null, this);

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, UpdateService.class));
        EventBus.getDefault().unregister(this);
        compositeDisposable.dispose();
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
        mAdapter = new WeatherListAdapter(viewPromptCityModelList, promptCityDbModelList, this, this);

        mRecyclerView.setAdapter(mAdapter);
    }

    public void updateRecyclerView(List<ViewPromptCityModel> newList) {
        DiffUtilWeatherListAdapter diffUtilWeatherListAdapter = new DiffUtilWeatherListAdapter(mAdapter.getViewPromptCityModelList(), newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilWeatherListAdapter);
        mAdapter.setViewPromptCityModelList(newList);
        diffResult.dispatchUpdatesTo(mAdapter);
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
        updateRecyclerView(viewPromptCityModelList);
    }

    @Override
    public void onLoaderReset(Loader<List<ViewPromptCityModel>> loader) {

    }

    @Override
    public void weatherItemClick(View view, Long key, String actionTitle) {
        Intent intent = new Intent(this, WeatherInfoActivity.class);
        intent.putExtra("promptKey", key);
        intent.putExtra("actionTitle", actionTitle);
        startActivity(intent);
    }

    @Override
    public void deleteBtnClick(View view, PromptCityDbModel promptCityDbModel) {
        RxDbManager dbManager = ((App) (getApplicationContext())).getDbManager();
        compositeDisposable.add(dbManager.deleteItemOdDbQuery(promptCityDbModel.getKey())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    PromptCityDbModelToViewPromptCityModel mapper = new PromptCityDbModelToViewPromptCityModel(aVoid);
                    WeatherDeleteItemEvent weatherDeleteItemEvent = new WeatherDeleteItemEvent(mapper.map(), aVoid);
                    EventBus.getDefault().post(weatherDeleteItemEvent);
                }));
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
            RxDbManager dbManager = ((App) context.getApplicationContext()).getDbManager();
            compositeDisposable.add(dbManager.allItemQuery()
                    .subscribeOn(Schedulers.io())
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
