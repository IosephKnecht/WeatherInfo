package com.example.aamezencev.weatherinfo.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import com.example.aamezencev.weatherinfo.Router;
import com.example.aamezencev.weatherinfo.data.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.inrerfaces.view.IBaseActivity;
import com.example.aamezencev.weatherinfo.inrerfaces.view.IBaseRouter;
import com.example.aamezencev.weatherinfo.inrerfaces.view.IWeatherInfoActivity;
import com.example.aamezencev.weatherinfo.inrerfaces.view.IWeatherInfoPresenter;
import com.example.aamezencev.weatherinfo.view.adapters.DiffUtilWeatherInfoAdapter;
import com.example.aamezencev.weatherinfo.view.adapters.WeatherInfoAdapter;
import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.events.WeatherDeleteItemEvent;
import com.example.aamezencev.weatherinfo.view.mappers.CurrentWeatherDbModelToView;
import com.example.aamezencev.weatherinfo.view.mappers.PromptCityDbModelToViewPromptCityModel;
import com.example.aamezencev.weatherinfo.domain.RxDbManager;
import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.view.presenters.WeatherInfoPresenter;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewCurrentWeatherModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherInfoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ViewCurrentWeatherModel>,
        IWeatherInfoActivity {

    private ImageButton btnDeleteWeather;

    private RecyclerView mRecyclerView;
    private WeatherInfoAdapter mAdapter;
    private CompositeDisposable compositeDisposable;

    private IWeatherInfoPresenter weatherInfoPresenter;
    private IBaseRouter baseRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        baseRouter = new Router(this);
        weatherInfoPresenter = new WeatherInfoPresenter(this, baseRouter);

        compositeDisposable = new CompositeDisposable();

        mRecyclerView = (RecyclerView) findViewById(R.id.weatherInfoRecycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        setTitle(getIntent().getStringExtra("actionTitle"));


        btnDeleteWeather = (ImageButton) findViewById(R.id.btnDeleteWeather);
        btnDeleteWeather.setOnClickListener(btn -> {
            weatherInfoPresenter.deleteCurrentWeather(getIntent().getLongExtra("promptKey", 0));
        });
        paint(new ViewCurrentWeatherModel());

        getLoaderManager().initLoader(1234, null, this);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        weatherInfoPresenter.onDestroy();
        weatherInfoPresenter = null;
        baseRouter = null;
        super.onDestroy();
    }

    public void paint(ViewCurrentWeatherModel viewCurrentWeatherModel) {
        mAdapter = new WeatherInfoAdapter(viewCurrentWeatherModel);

        mRecyclerView.setAdapter(mAdapter);
    }

    public void updateRecyclerView(ViewCurrentWeatherModel currentWeatherModel) {
        DiffUtilWeatherInfoAdapter diffUtilWeatherInfoAdapter = new DiffUtilWeatherInfoAdapter(mAdapter.getViewCurrentWeatherModel(), currentWeatherModel);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilWeatherInfoAdapter);
        mAdapter.setViewCurrentWeatherModel(currentWeatherModel);
        diffResult.dispatchUpdatesTo(mAdapter);
    }

    @Override
    public Loader<ViewCurrentWeatherModel> onCreateLoader(int i, Bundle bundle) {
        Loader<ViewCurrentWeatherModel> loader = null;
        if (i == 1234) {
            loader = new InfoLoader(this, getIntent().getLongExtra("promptKey", 0), weatherInfoPresenter);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ViewCurrentWeatherModel> loader, ViewCurrentWeatherModel viewCurrentWeatherModel) {
        updateRecyclerView(viewCurrentWeatherModel);
    }

    @Override
    public void onLoaderReset(Loader<ViewCurrentWeatherModel> loader) {

    }

    @Override
    public void paintWeather(Object viewModel) {
        getLoaderManager().getLoader(1234).deliverResult(viewModel);
    }

    private static class InfoLoader extends Loader<ViewCurrentWeatherModel> {

        private ViewCurrentWeatherModel viewCurrentWeatherModel;
        private Long key;
        private IWeatherInfoPresenter weatherInfoPresenter;
        private CompositeDisposable compositeDisposable;

        public InfoLoader(Context context, Long key,
                          IWeatherInfoPresenter weatherInfoPresenter) {
            super(context);
            this.key = key;
            this.weatherInfoPresenter = weatherInfoPresenter;
            compositeDisposable = new CompositeDisposable();
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (viewCurrentWeatherModel == null) forceLoad();
        }

        @Override
        public void forceLoad() {
            super.forceLoad();
            weatherInfoPresenter.getCurrentWeather(key);
        }

        @Override
        protected void onReset() {
            super.onReset();
            compositeDisposable.dispose();
            viewCurrentWeatherModel = null;
        }
    }
}
