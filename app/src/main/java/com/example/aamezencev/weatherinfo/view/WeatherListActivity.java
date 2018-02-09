package com.example.aamezencev.weatherinfo.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.UpdateService;
import com.example.aamezencev.weatherinfo.events.UpdatedCurrentWeather;
import com.example.aamezencev.weatherinfo.events.WeatherDeleteItemEvent;
import com.example.aamezencev.weatherinfo.fragments.ServiceDialog;
import com.example.aamezencev.weatherinfo.view.adapters.DiffUtilWeatherListAdapter;
import com.example.aamezencev.weatherinfo.view.adapters.RecyclerItemTouchHelper;
import com.example.aamezencev.weatherinfo.view.adapters.WeatherListAdapter;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.interfaces.IWeatherListActivity;
import com.example.aamezencev.weatherinfo.view.interfaces.WeatherItemClick;
import com.example.aamezencev.weatherinfo.view.presenters.IWeatherListPresenter;
import com.example.aamezencev.weatherinfo.view.presenters.WeatherListPresenter;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class WeatherListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<IWeatherListPresenter>,
        WeatherItemClick, IWeatherListActivity, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private RecyclerView mRecyclerView;
    private WeatherListAdapter mAdapter;

    private CompositeDisposable compositeDisposable;

    private IWeatherListPresenter weatherListPresenter;
    private IBaseRouter baseRouter;
    private ServiceDialog serviceDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        baseRouter = new Router(this);
        weatherListPresenter = ((SaveWeatherListPresenter) getLoaderManager().initLoader(123, null, this)).getPresenter();
        weatherListPresenter.onAttachView(this, baseRouter);

        compositeDisposable = new CompositeDisposable();

        mRecyclerView = findViewById(R.id.weatherRecycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAdapter = new WeatherListAdapter(new ArrayList<>(), this);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecyclerView);

        serviceDialog = new ServiceDialog();

        boolean state = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("serviceSwitch", true);
        if (!state) {
            if (getFragmentManager().findFragmentByTag("serviceDialog") == null)
                serviceDialog.show(getFragmentManager(), "serviceDialog");
        } else {
            baseRouter.startUpdateService();
        }
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        weatherListPresenter.onDetachView();
        stopService(new Intent(this, UpdateService.class));
        compositeDisposable.dispose();
        baseRouter = null;
        serviceDialog = null;
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.returnHomeItem:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.settingsItem:
                intent = new Intent(this, SettingsActivity.class);
                break;
        }
        startActivity(intent);
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
    public void paintList(List viewModelList) {
        updateRecyclerView(viewModelList);
    }

    @Subscribe
    public void updatedCurrentWeather(UpdatedCurrentWeather updatedCurrentWeather) {
        if (weatherListPresenter != null) weatherListPresenter.getPromptCityDbModelList();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        int adapterPosition = viewHolder.getAdapterPosition();
        Long key = Long.valueOf(mAdapter.getViewPromptCityModelList().get(adapterPosition).getKey());
        mAdapter.removeItem(adapterPosition);
        weatherListPresenter.deleteItemAsDb(key);
    }

    private static class SaveWeatherListPresenter extends Loader<IWeatherListPresenter> {

        private IWeatherListPresenter weatherListPresenter;

        public SaveWeatherListPresenter(Context context, IWeatherListPresenter weatherListPresenter) {
            super(context);
            this.weatherListPresenter = weatherListPresenter;
            this.weatherListPresenter.getPromptCityDbModelList();
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
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
