package com.example.aamezencev.weatherinfo.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.databinding.ActivityWeatherInfoBinding;
import com.example.aamezencev.weatherinfo.view.adapters.PagerAdapter;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.interfaces.IWeatherInfoActivity;
import com.example.aamezencev.weatherinfo.view.presenters.IWeatherInfoPresenter;
import com.example.aamezencev.weatherinfo.view.presenters.WeatherInfoPresenter;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class WeatherInfoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<IWeatherInfoPresenter>,
        IWeatherInfoActivity {

    private ImageButton btnDeleteWeather;

    private CompositeDisposable compositeDisposable;

    private IWeatherInfoPresenter weatherInfoPresenter;
    private IBaseRouter baseRouter;

    private ActivityWeatherInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_info);
        binding.setPagerAdapter(new PagerAdapter(getSupportFragmentManager()));

        baseRouter = new Router(this);
        compositeDisposable = new CompositeDisposable();

        btnDeleteWeather = (ImageButton) findViewById(R.id.btnDeleteWeather);
        btnDeleteWeather.setOnClickListener(btn -> {
            weatherInfoPresenter.deleteCurrentWeather(getIntent().getLongExtra("promptKey", 0));
        });

        weatherInfoPresenter = ((SaveInfoPresenter) getLoaderManager().initLoader(1234, null, this)).getWeatherInfoPresenter();
        weatherInfoPresenter.onAttachView(this, baseRouter);

        binding.getPagerAdapter().setViewCurrentWeatherModelList(weatherInfoPresenter.getViewModelList());

//        pager = (ViewPager) findViewById(R.id.pager);
//        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
//        pager.setAdapter(pagerAdapter);
//        pagerAdapter.setViewCurrentWeatherModelList(weatherInfoPresenter.getViewModelList());
//        pagerAdapter.notifyDataSetChanged();

        if (savedInstanceState != null) {
            binding.pager.setCurrentItem(savedInstanceState.getInt("currentPage"));
        }
    }

    @BindingAdapter(value = {"android:pagerAdapter"}, requireAll = false)
    public static void setViewPager(ViewPager viewPager, FragmentPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        weatherInfoPresenter.onDetachView();
        compositeDisposable.dispose();
        baseRouter = null;
        binding = null;
        weatherInfoPresenter = null;
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentPage", binding.pager.getCurrentItem());
    }

    @Override
    public Loader<IWeatherInfoPresenter> onCreateLoader(int i, Bundle bundle) {
        Loader loader = null;
        if (i == 1234) {
            weatherInfoPresenter = new WeatherInfoPresenter();
            loader = new SaveInfoPresenter(this, weatherInfoPresenter, getIntent().getLongExtra("promptKey", 0));
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<IWeatherInfoPresenter> loader, IWeatherInfoPresenter weatherInfoPresenter) {
        this.weatherInfoPresenter = weatherInfoPresenter;
    }

    @Override
    public void onLoaderReset(Loader<IWeatherInfoPresenter> loader) {

    }

    @Override
    public void paintWeather(List weatherModels) {
        binding.getPagerAdapter().setViewCurrentWeatherModelList(weatherModels);
        binding.getPagerAdapter().notifyDataSetChanged();
    }

    private static class SaveInfoPresenter extends Loader<IWeatherInfoPresenter> {

        private IWeatherInfoPresenter weatherInfoPresenter;

        public SaveInfoPresenter(Context context, IWeatherInfoPresenter weatherInfoPresenter, Long key) {
            super(context);
            this.weatherInfoPresenter = weatherInfoPresenter;
            this.weatherInfoPresenter.getCurrentWeather(key);
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
