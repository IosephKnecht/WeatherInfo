package com.example.aamezencev.weatherinfo.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.Router;
import com.example.aamezencev.weatherinfo.UpdateService;
import com.example.aamezencev.weatherinfo.events.WeatherDeleteItemEvent;
import com.example.aamezencev.weatherinfo.inrerfaces.DeleteBtnClick;
import com.example.aamezencev.weatherinfo.inrerfaces.view.IBaseActivity;
import com.example.aamezencev.weatherinfo.inrerfaces.view.IBaseRouter;
import com.example.aamezencev.weatherinfo.inrerfaces.view.IMainPresenter;
import com.example.aamezencev.weatherinfo.inrerfaces.WeatherItemClick;
import com.example.aamezencev.weatherinfo.view.adapters.DiffUtilWeatherListAdapter;
import com.example.aamezencev.weatherinfo.view.adapters.WeatherListAdapter;
import com.example.aamezencev.weatherinfo.view.presenters.MainActivityPresenter;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class WeatherListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ViewPromptCityModel>>,
        WeatherItemClick, DeleteBtnClick, IBaseActivity {
    private RecyclerView mRecyclerView;
    private WeatherListAdapter mAdapter;

    private List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();

    private CompositeDisposable compositeDisposable;

    private IMainPresenter mainPresenter;
    private IBaseRouter baseRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);
        baseRouter = new Router(this);
        mainPresenter = new MainActivityPresenter(this, baseRouter);

        compositeDisposable = new CompositeDisposable();

        EventBus.getDefault().register(this);

        Intent intent = new Intent(this, UpdateService.class);

        boolean state = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("serviceSwitch", true);
        if (state) {
            this.startService(intent);
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
        mainPresenter.onDestroy();
        mainPresenter = null;
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

    public void paint() {
        mAdapter = new WeatherListAdapter(viewPromptCityModelList, this, this);

        mRecyclerView.setAdapter(mAdapter);
    }

    public void updateRecyclerView(List<ViewPromptCityModel> newList) {
        DiffUtilWeatherListAdapter diffUtilWeatherListAdapter = new DiffUtilWeatherListAdapter(mAdapter.getViewPromptCityModelList(), newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilWeatherListAdapter);
        mAdapter.setViewPromptCityModelList(newList);
        mAdapter.notifyDataSetChanged();
        diffResult.dispatchUpdatesTo(mAdapter);
    }

    @Override
    public Loader<List<ViewPromptCityModel>> onCreateLoader(int i, Bundle bundle) {
        android.content.Loader loader = null;
        if (i == 123) {
            loader = new MyLoader(this, mainPresenter);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<ViewPromptCityModel>> loader, List<ViewPromptCityModel> viewPromptCityModelList) {
        this.viewPromptCityModelList = viewPromptCityModelList;
        updateRecyclerView(viewPromptCityModelList);
    }

    @Override
    public void onLoaderReset(Loader<List<ViewPromptCityModel>> loader) {

    }

    @Override
    public void weatherItemClick(View view, Long key, String actionTitle) {
        baseRouter.openWeatherInfoActivity(key, actionTitle);
    }

    @Override
    public void deleteBtnClick(View view, Long key, int position) {
        mainPresenter.deleteItemAsDb(key);
    }

    @Override
    public void paintList(List viewModelList) {
        getLoaderManager().getLoader(123).deliverResult(viewModelList);
        updateRecyclerView(viewModelList);
    }


    private static class MyLoader extends android.content.Loader<List<ViewPromptCityModel>> {

        private Context context;
        private List<ViewPromptCityModel> viewPromptCityModelList;
        private IMainPresenter mainPresenter;

        public MyLoader(Context context, IMainPresenter mainPresenter) {
            super(context);
            this.context = context;
            this.mainPresenter = mainPresenter;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (viewPromptCityModelList == null) forceLoad();
        }

        @Override
        public void forceLoad() {
            super.forceLoad();
            if (viewPromptCityModelList == null) {
                mainPresenter.getPromptCityDbModelList();
            } else {
                deliverResult(viewPromptCityModelList);
            }
        }

        @Override
        protected void onReset() {
            super.onReset();
            viewPromptCityModelList = null;
        }
    }
}
