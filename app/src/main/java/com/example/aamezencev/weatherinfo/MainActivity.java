package com.example.aamezencev.weatherinfo;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.aamezencev.weatherinfo.Adapters.DiffUtilMainAdapter;
import com.example.aamezencev.weatherinfo.Adapters.MainAdapter;
import com.example.aamezencev.weatherinfo.Inrerfaces.CheckBoxClick;
import com.example.aamezencev.weatherinfo.JsonModels.JsonPromptModel;
import com.example.aamezencev.weatherinfo.Mappers.JsonPromptModelToViewPromptModel;
import com.example.aamezencev.weatherinfo.Queries.RxDbManager;
import com.example.aamezencev.weatherinfo.Requests.RxGoogleApiManager;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ViewPromptCityModel>>, CheckBoxClick {

    private View spinner;
    private RecyclerView mRecyclerView;
    private SearchView searchView;
    private CompositeDisposable disposables;
    private List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();
    private MainAdapter mAdapter;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        disposables = new CompositeDisposable();

        mRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        paint(viewPromptCityModelList);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        disposables.dispose();
    }

    private void paintSpinner(int isVisible) {
        FrameLayout frameLayout = findViewById(R.id.flSpinner);
        frameLayout.removeAllViews();
        LayoutInflater layoutInflater = getLayoutInflater();
        spinner = layoutInflater.inflate(R.layout.spinner_layout, frameLayout, false);
        spinner.setVisibility(isVisible);
        frameLayout.addView(spinner);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        disposables.add(RxSearchView.queryTextChanges(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(str -> str.length() >= 4)
                .subscribe(aVoid -> {
                    RxGoogleApiManager googleApiManager = ((App) getApplicationContext().getApplicationContext()).getGoogleApiManager();
                    disposables.add(googleApiManager.promptRequest(aVoid.toString())
                            .subscribeOn(Schedulers.io())
                            .map(response -> {
                                String jsonString = response.body().string();
                                Gson gson = new Gson();
                                Type type = new TypeToken<JsonPromptModel>() {
                                }.getType();
                                JsonPromptModel jsonPromptModel = gson.fromJson(jsonString, type);
                                return jsonPromptModel;
                            })
                            .map(jsonPromptModel -> {
                                JsonPromptModelToViewPromptModel mapper = new JsonPromptModelToViewPromptModel(jsonPromptModel);
                                ViewPromptModel viewPromptModel = mapper.map();
                                return viewPromptModel.getViewPromptCityModelList();
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(viewPromptCityModelList -> {
                                paintSpinner(View.VISIBLE);
                                this.viewPromptCityModelList = viewPromptCityModelList;
                                getLoaderManager().restartLoader(1, null, this);
                            }));
                }));

        return true;
    }

    private void paint(List<ViewPromptCityModel> viewCityModelList) {
        View view = findViewById(R.id.spinner_view);
        if (view != null) view.setVisibility(View.INVISIBLE);

        mAdapter = new MainAdapter(viewCityModelList, this);

        floatingActionButton.setVisibility(mAdapter.isVisibleFloatingButton());

        floatingActionButton.setOnClickListener(fabView -> {
            SharedPreferences sharedPreferences = getSharedPreferences("isFirstRun", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("state", getIntent().getBooleanExtra("isFirstRun", true));
            editor.commit();

            RxDbManager dbManager = ((App) getApplicationContext()).getDbManager();
            disposables.add(dbManager.addPromptListViewToDb(mAdapter.selectIsCheckedItem())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subs -> {
                        startService(new Intent(MainActivity.this, UpdateService.class));
                        startActivity(new Intent(MainActivity.this, WeatherListActivity.class));
                    }));

        });

        mRecyclerView.setAdapter(mAdapter);
    }

    private void updateRecyclerView(List<ViewPromptCityModel> newList) {
        DiffUtilMainAdapter diffUtilMainAdapter = new DiffUtilMainAdapter(mAdapter.getViewPromptCityModelList(), newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilMainAdapter);
        mAdapter.setViewPromptCityModelList(newList);
        diffResult.dispatchUpdatesTo(mAdapter);
        floatingActionButton.setVisibility(mAdapter.isVisibleFloatingButton());
    }

    @Override
    public Loader<List<ViewPromptCityModel>> onCreateLoader(int i, Bundle bundle) {
        Loader<List<ViewPromptCityModel>> loader = null;
        if (i == 1) {
            loader = new MainLoader(this, viewPromptCityModelList);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<ViewPromptCityModel>> loader, List<ViewPromptCityModel> viewPromptCityModels) {
        paintSpinner(View.INVISIBLE);
        updateRecyclerView(viewPromptCityModels);
    }

    @Override
    public void onLoaderReset(Loader<List<ViewPromptCityModel>> loader) {
    }

    @Override
    public void checkBoxClick(View view, ViewPromptCityModel viewPromptCityModel) {
        boolean state = viewPromptCityModel.isChecked();
        viewPromptCityModel.setChecked(!state);

        floatingActionButton.setVisibility(mAdapter.isVisibleFloatingButton());
    }


    private static class MainLoader extends Loader<List<ViewPromptCityModel>> {
        private List<ViewPromptCityModel> viewPromptCityModelList;

        public MainLoader(Context context, List<ViewPromptCityModel> viewPromptCityModelList) {
            super(context);
            this.viewPromptCityModelList = viewPromptCityModelList;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (viewPromptCityModelList != null) {
                deliverResult(viewPromptCityModelList);
                return;
            }
            forceLoad();
        }

        @Override
        public void forceLoad() {
            super.forceLoad();
            deliverResult(viewPromptCityModelList);
        }

        @Override
        protected void onReset() {
            super.onReset();
            viewPromptCityModelList = null;
        }
    }
}
