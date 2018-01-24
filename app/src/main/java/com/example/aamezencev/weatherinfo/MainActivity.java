package com.example.aamezencev.weatherinfo;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
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
import com.example.aamezencev.weatherinfo.Events.FloatingButtonEventDb;
import com.example.aamezencev.weatherinfo.Fragments.WeatherListRetainFragment;
import com.example.aamezencev.weatherinfo.Requests.PromptRequest;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ViewPromptCityModel>> {

    private View spinner;
    private RecyclerView mRecyclerView;
    private SearchView searchView;
    private CompositeDisposable disposables;

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

        if (getLoaderManager().getLoader(1) != null) {
            Loader loader = getLoaderManager().getLoader(1);
            paint(((MainLoader) loader).getViewPromptCityModelList());
        }
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
        //paint(((MainLoader) getLoaderManager().initLoader(1, null, this)).getViewPromptCityModelList());
        disposables.add(RxSearchView.queryTextChanges(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(str -> str.length() >= 4)
                .subscribe(aVoid -> {
                    getLoaderManager().restartLoader(1, null, this);
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
                FloatingButtonEventDb floatingButtonEventDb = new FloatingButtonEventDb(mAdapter.selectIsCheckedItem(), MainActivity.this);
                floatingButtonEventDb.execute();
                startService(new Intent(MainActivity.this, UpdateService.class));
                startActivity(new Intent(MainActivity.this, WeatherListActivity.class));
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public Loader<List<ViewPromptCityModel>> onCreateLoader(int i, Bundle bundle) {
        Loader<List<ViewPromptCityModel>> loader = null;
        if (i == 1) {
            loader = new MainLoader(this, searchView.getQuery().toString());
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


    private static class MainLoader extends AsyncTaskLoader<List<ViewPromptCityModel>> {
        private List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();
        private String city;

        public MainLoader(Context context, String city) {
            super(context);
            this.city = city;
        }

        @Override
        public List<ViewPromptCityModel> loadInBackground() {
            PromptRequest promptRequest = new PromptRequest(city);
            promptRequest.execute();
            viewPromptCityModelList = new ArrayList<>();
            try {
                viewPromptCityModelList = promptRequest.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return viewPromptCityModelList;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (viewPromptCityModelList == null || viewPromptCityModelList.size() == 0) forceLoad();
        }


        @Override
        protected void onAbandon() {
            super.onAbandon();
        }

        public List<ViewPromptCityModel> getViewPromptCityModelList() {
            return viewPromptCityModelList;
        }

        @Override
        protected void onReset() {
            super.onReset();
        }

        @Override
        public void deliverResult(List<ViewPromptCityModel> data) {
            super.deliverResult(data);
        }
    }
}
