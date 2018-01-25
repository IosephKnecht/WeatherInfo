package com.example.aamezencev.weatherinfo;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import com.example.aamezencev.weatherinfo.Adapters.WeatherInfoAdapter;
import com.example.aamezencev.weatherinfo.Adapters.WeatherListAdapter;
import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.Events.WeatherDeleteItemEvent;
import com.example.aamezencev.weatherinfo.Mappers.CurrentWeatherDbModelToView;
import com.example.aamezencev.weatherinfo.Mappers.PromptCityDbModelToViewPromptCityModel;
import com.example.aamezencev.weatherinfo.Queries.RxDbManager;
import com.example.aamezencev.weatherinfo.ViewModels.ViewCurrentWeatherModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherInfoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ViewCurrentWeatherModel> {

    private ImageButton btnDeleteWeather;

    private RecyclerView mRecyclerView;
    private WeatherInfoAdapter mAdapter;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);


        compositeDisposable = new CompositeDisposable();

        mRecyclerView = (RecyclerView) findViewById(R.id.weatherInfoRecycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        setTitle(getIntent().getStringExtra("actionTitle"));


        btnDeleteWeather = (ImageButton) findViewById(R.id.btnDeleteWeather);
        btnDeleteWeather.setOnClickListener(btn -> {
            RxDbManager dbManager = ((App) getApplicationContext()).getDbManager();
            compositeDisposable.add(dbManager.deleteItemOdDbQuery(getIntent().getLongExtra("promptKey", 0))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subs -> {
                        PromptCityDbModelToViewPromptCityModel mapper = new PromptCityDbModelToViewPromptCityModel((List<PromptCityDbModel>) subs);
                        WeatherDeleteItemEvent weatherDeleteItemEvent = new WeatherDeleteItemEvent(mapper.map(), (List<PromptCityDbModel>) subs);
                        EventBus.getDefault().post(weatherDeleteItemEvent);
                        finish();
                    }));
        });
        getLoaderManager().initLoader(1234, null, this);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    public void paint(ViewCurrentWeatherModel viewCurrentWeatherModel) {
        mAdapter = new WeatherInfoAdapter(viewCurrentWeatherModel);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public Loader<ViewCurrentWeatherModel> onCreateLoader(int i, Bundle bundle) {
        Loader<ViewCurrentWeatherModel> loader = null;
        if (i == 1234) {
            loader = new InfoLoader(this, getIntent().getLongExtra("promptKey", 0));
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ViewCurrentWeatherModel> loader, ViewCurrentWeatherModel viewCurrentWeatherModel) {
        paint(viewCurrentWeatherModel);
    }

    @Override
    public void onLoaderReset(Loader<ViewCurrentWeatherModel> loader) {

    }

    private static class InfoLoader extends Loader<ViewCurrentWeatherModel> {

        private ViewCurrentWeatherModel viewCurrentWeatherModel;
        private Long key;
        private Context context;

        public InfoLoader(Context context, Long key) {
            super(context);
            this.key = key;
            this.context = context;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (viewCurrentWeatherModel == null) forceLoad();
        }

        @Override
        public void forceLoad() {
            super.forceLoad();
            RxDbManager dbManager = ((App) context.getApplicationContext()).getDbManager();
            dbManager.findWeatherByKey(key)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber -> {
                        CurrentWeatherDbModelToView mapper = new CurrentWeatherDbModelToView(subscriber);
                        deliverResult(mapper.map());
                    });
        }

        @Override
        protected void onReset() {
            super.onReset();
            viewCurrentWeatherModel = null;
        }
    }
}
