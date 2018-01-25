package com.example.aamezencev.weatherinfo;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.aamezencev.weatherinfo.Adapters.MainAdapter;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ViewPromptCityModel>> {

    private View spinner;
    private RecyclerView mRecyclerView;
    private SearchView searchView;
    private CompositeDisposable disposables;
    private List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();

        paintSpinner();
    }

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

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        disposables.dispose();
    }

    private void paintSpinner() {
        FrameLayout frameLayout = findViewById(R.id.flSpinner);
        LayoutInflater layoutInflater = getLayoutInflater();
        spinner = layoutInflater.inflate(R.layout.spinner_layout, frameLayout, false);
        spinner.setVisibility(View.INVISIBLE);
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
                    disposables.add(RxGoogleApiManager.instance().promptRequest(aVoid.toString())
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
                                this.viewPromptCityModelList = viewPromptCityModelList;
                                getLoaderManager().restartLoader(1, null, this);
                            }));
                }));

        return true;
    }

    private void paint(List<ViewPromptCityModel> viewCityModelList) {
        View view = findViewById(R.id.spinner_view);
        if (view != null) view.setVisibility(View.INVISIBLE);

        MainAdapter mAdapter = new MainAdapter(viewCityModelList);

        FloatingActionButton floatingButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingButton.setVisibility(mAdapter.isVisibleFloatingButton());

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxDbManager.setDaoSession(((App) getApplicationContext()).getDaoSession());
                RxDbManager.instance().addPromptListToDb(mAdapter.selectIsCheckedItem())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subs -> {
                            startService(new Intent(MainActivity.this, UpdateService.class));
                            startActivity(new Intent(MainActivity.this, WeatherListActivity.class));
                        });

            }
        });

        mRecyclerView.setAdapter(mAdapter);
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
        paint(viewPromptCityModels);
    }

    @Override
    public void onLoaderReset(Loader<List<ViewPromptCityModel>> loader) {
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
