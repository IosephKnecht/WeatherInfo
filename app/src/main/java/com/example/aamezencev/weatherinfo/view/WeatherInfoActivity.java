package com.example.aamezencev.weatherinfo.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;

import com.example.aamezencev.weatherinfo.fragments.PageFragment;
import com.example.aamezencev.weatherinfo.view.adapters.PagerAdapter;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.interfaces.IWeatherInfoActivity;
import com.example.aamezencev.weatherinfo.view.presenters.IWeatherInfoPresenter;
import com.example.aamezencev.weatherinfo.view.adapters.DiffUtilWeatherInfoAdapter;
import com.example.aamezencev.weatherinfo.view.adapters.WeatherInfoAdapter;
import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.view.presenters.WeatherInfoPresenter;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewCurrentWeatherModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class WeatherInfoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<IWeatherInfoPresenter>,
        IWeatherInfoActivity {

    private ImageButton btnDeleteWeather;

    //    private RecyclerView mRecyclerView;
//    private WeatherInfoAdapter mAdapter;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private CompositeDisposable compositeDisposable;

    private IWeatherInfoPresenter weatherInfoPresenter;
    private IBaseRouter baseRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        setTitle(getIntent().getStringExtra("actionTitle"));

        baseRouter = new Router(this);
        compositeDisposable = new CompositeDisposable();

//        mRecyclerView = (RecyclerView) findViewById(R.id.weatherInfoRecycler);
//        mRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mAdapter = new WeatherInfoAdapter(new ViewCurrentWeatherModel());
//        mRecyclerView.setAdapter(mAdapter);

        btnDeleteWeather = (ImageButton) findViewById(R.id.btnDeleteWeather);
        btnDeleteWeather.setOnClickListener(btn -> {
            weatherInfoPresenter.deleteCurrentWeather(getIntent().getLongExtra("promptKey", 0));
        });

        weatherInfoPresenter = ((SaveInfoPresenter) getLoaderManager().initLoader(1234, null, this)).getWeatherInfoPresenter();
        weatherInfoPresenter.onAttachView(this, baseRouter);

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pagerAdapter.setViewCurrentWeatherModelList(weatherInfoPresenter.getViewModelList());
        pagerAdapter.notifyDataSetChanged();

        if (savedInstanceState != null) {
            pager.setCurrentItem(savedInstanceState.getInt("currentPage"));
        }
    }

    @Override
    protected void onDestroy() {
        weatherInfoPresenter.onDetachView();
        compositeDisposable.dispose();
        baseRouter = null;
        weatherInfoPresenter = null;
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentPage", pager.getCurrentItem());
    }

    public void updateRecyclerView(ViewCurrentWeatherModel currentWeatherModel) {
//        DiffUtilWeatherInfoAdapter diffUtilWeatherInfoAdapter = new DiffUtilWeatherInfoAdapter(mAdapter.getViewCurrentWeatherModel(), currentWeatherModel);
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilWeatherInfoAdapter);
//        mAdapter.setViewCurrentWeatherModel(currentWeatherModel);
//        diffResult.dispatchUpdatesTo(mAdapter);
    }

    @Override
    public Loader<IWeatherInfoPresenter> onCreateLoader(int i, Bundle bundle) {
        Loader loader = null;
        if (i == 1234) {
            weatherInfoPresenter = new WeatherInfoPresenter();
            loader = new SaveInfoPresenter(this, weatherInfoPresenter);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<IWeatherInfoPresenter> loader, IWeatherInfoPresenter weatherInfoPresenter) {
        this.weatherInfoPresenter = weatherInfoPresenter;
        //Спамим в БД каждый раз, но получаем "свежую погоду" погоду...
        this.weatherInfoPresenter.getCurrentWeather(getIntent().getLongExtra("promptKey", 0));
    }

    @Override
    public void onLoaderReset(Loader<IWeatherInfoPresenter> loader) {

    }

    @Override
    public void paintWeather(List weatherModels) {
        //updateRecyclerView((ViewCurrentWeatherModel) weatherModels.get(0));
        pagerAdapter.setViewCurrentWeatherModelList(weatherModels);
        pagerAdapter.notifyDataSetChanged();
    }

    private static class SaveInfoPresenter extends Loader<IWeatherInfoPresenter> {

        private IWeatherInfoPresenter weatherInfoPresenter;

        public SaveInfoPresenter(Context context, IWeatherInfoPresenter weatherInfoPresenter) {
            super(context);
            this.weatherInfoPresenter = weatherInfoPresenter;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            deliverResult(weatherInfoPresenter);
        }

        @Override
        protected void onReset() {
            super.onReset();
            weatherInfoPresenter.onDestroy();
            weatherInfoPresenter = null;
        }

        private IWeatherInfoPresenter getWeatherInfoPresenter() {
            return weatherInfoPresenter;
        }
    }
}
