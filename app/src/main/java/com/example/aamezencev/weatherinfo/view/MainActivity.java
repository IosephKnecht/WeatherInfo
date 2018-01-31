package com.example.aamezencev.weatherinfo.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.aamezencev.weatherinfo.view.interfaces.CheckBoxClick;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseActivity;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.presenters.IMainPresenter;
import com.example.aamezencev.weatherinfo.view.adapters.MainAdapter;
import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.view.presenters.MainActivityPresenter;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ViewPromptCityModel>>, CheckBoxClick,
        IBaseActivity {

    private View spinner;
    private RecyclerView mRecyclerView;
    private SearchView searchView;
    private CompositeDisposable disposables;
    private List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();
    private MainAdapter mAdapter;
    private FloatingActionButton floatingActionButton;

    private IMainPresenter mainPresenter;
    private IBaseRouter baseRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseRouter = new Router(this);
        mainPresenter = new MainActivityPresenter(this, baseRouter);

        disposables = new CompositeDisposable();

        mRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        spinner = findViewById(R.id.spinner_view);

        paint(viewPromptCityModelList);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        disposables.dispose();
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
                    spinner.setVisibility(View.VISIBLE);
                    getLoaderManager().restartLoader(1, null, this);
                }));

        return true;
    }

    private void updateRecyclerView(List<ViewPromptCityModel> newList) {
//        DiffUtilMainAdapter diffUtilMainAdapter = new DiffUtilMainAdapter(mAdapter.getViewPromptCityModelList(), newList);
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilMainAdapter);
        mAdapter.setViewPromptCityModelList(newList);
        mAdapter.notifyDataSetChanged();
//        diffResult.dispatchUpdatesTo(mAdapter);
        floatingActionButton.setVisibility(isVisibleFloatingButton());
    }

    @Override
    public Loader<List<ViewPromptCityModel>> onCreateLoader(int i, Bundle bundle) {
        Loader<List<ViewPromptCityModel>> loader = null;
        if (i == 1) {
            String city = null;
            if (searchView != null) city = searchView.getQuery().toString();
            loader = new MainLoader(this, mainPresenter, city);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<ViewPromptCityModel>> loader, List<ViewPromptCityModel> viewPromptCityModels) {
        this.viewPromptCityModelList = viewPromptCityModels;
        spinner.setVisibility(View.INVISIBLE);
        updateRecyclerView(viewPromptCityModels);
    }

    @Override
    public void onLoaderReset(Loader<List<ViewPromptCityModel>> loader) {
    }

    @Override
    public void checkBoxClick(View view, ViewPromptCityModel viewPromptCityModel) {
        boolean state = viewPromptCityModel.isChecked();
        viewPromptCityModel.setChecked(!state);

        floatingActionButton.setVisibility(isVisibleFloatingButton());
    }

    @Override
    public void paintList(List viewModelList) {
        getLoaderManager().getLoader(1).deliverResult(viewModelList);
    }

    private void paint(List<ViewPromptCityModel> viewPromptCityModels) {
        mAdapter = new MainAdapter(viewPromptCityModels, this);

        floatingActionButton.setVisibility(isVisibleFloatingButton());

        floatingActionButton.setOnClickListener(fabView -> {
            SharedPreferences sharedPreferences = getSharedPreferences("isFirstRun", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("state", getIntent().getBooleanExtra("isFirstRun", true));
            editor.commit();

            mainPresenter.addPromptListViewToDb(selectIsCheckedItem());
            baseRouter.openWeatherListActivity();
            baseRouter.startUpdateService();
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    private int isVisibleFloatingButton() {
        for (ViewPromptCityModel viewPromptCityModel : viewPromptCityModelList) {
            if (viewPromptCityModel.isChecked()) return View.VISIBLE;
        }
        return View.INVISIBLE;
    }

    private List<ViewPromptCityModel> selectIsCheckedItem() {
        List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();
        for (ViewPromptCityModel viewPromptCityModel : this.viewPromptCityModelList) {
            if (viewPromptCityModel.isChecked()) viewPromptCityModelList.add(viewPromptCityModel);
        }
        return viewPromptCityModelList;
    }

    private static class MainLoader extends Loader<List<ViewPromptCityModel>> {
        private List<ViewPromptCityModel> viewPromptCityModelList;
        private IMainPresenter mainPresenter;
        private String city;

        public MainLoader(Context context, IMainPresenter mainPresenter, String city) {
            super(context);
            this.viewPromptCityModelList = viewPromptCityModelList;
            this.mainPresenter = mainPresenter;
            this.city = city;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (viewPromptCityModelList == null) {
                forceLoad();
            } else {
                deliverResult(viewPromptCityModelList);
            }
        }

        @Override
        public void forceLoad() {
            super.forceLoad();
            mainPresenter.getViewPromptCityModelList(city);
        }

        @Override
        protected void onReset() {
            super.onReset();
            viewPromptCityModelList = null;
        }
    }
}
