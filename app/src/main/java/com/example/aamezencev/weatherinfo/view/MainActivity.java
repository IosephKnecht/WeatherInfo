package com.example.aamezencev.weatherinfo.view;

import android.app.LoaderManager;
import android.content.Context;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.aamezencev.weatherinfo.view.adapters.DiffUtilMainAdapter;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<IMainPresenter>, CheckBoxClick,
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

        disposables = new CompositeDisposable();

        mRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        spinner = findViewById(R.id.spinner_view);

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
        searchView = (SearchView) searchItem.getActionView();
        disposables.add(RxSearchView.queryTextChanges(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(str -> str.length() >= 4)
                .subscribe(aVoid -> {
                    spinner.setVisibility(View.VISIBLE);
                    mainPresenter.getViewPromptCityModelList(aVoid.toString());
                }));

        return true;
    }

    private void updateRecyclerView(List<ViewPromptCityModel> newList) {
        DiffUtilMainAdapter diffUtilMainAdapter = new DiffUtilMainAdapter(mAdapter.getViewPromptCityModelList(), newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilMainAdapter);
        mAdapter.setViewPromptCityModelList(newList);
        diffResult.dispatchUpdatesTo(mAdapter);
        floatingActionButton.setVisibility(mainPresenter.isVisibleFloatingButton());
    }


    @Override
    public void checkBoxClick(View view, ViewPromptCityModel viewPromptCityModel) {
        boolean state = viewPromptCityModel.isChecked();
        viewPromptCityModel.setChecked(!state);

        floatingActionButton.setVisibility(mainPresenter.isVisibleFloatingButton());
    }

    @Override
    public void paintList(List viewModelList) {
        spinner.setVisibility(View.INVISIBLE);
        updateRecyclerView(viewModelList);
    }

    private void paint(List<ViewPromptCityModel> viewPromptCityModels) {
        mAdapter = new MainAdapter(viewPromptCityModels, this);

        floatingActionButton.setVisibility(mainPresenter.isVisibleFloatingButton());

        floatingActionButton.setOnClickListener(fabView -> {
            SharedPreferences sharedPreferences = getSharedPreferences("isFirstRun", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("state", getIntent().getBooleanExtra("isFirstRun", true));
            editor.commit();

            mainPresenter.addPromptListViewToDb(mainPresenter.selectIsCheckedItem());
            baseRouter.openWeatherListActivity();
            baseRouter.startUpdateService();
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public Loader<IMainPresenter> onCreateLoader(int i, Bundle bundle) {
        android.content.Loader loader = null;
        if (i == 1) {
            //loader = new SaveMainPresenter(this, baseRouter, this, mainPresenter);
            loader = new SaveMainPresenterLoader(this, this, mainPresenter);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<IMainPresenter> loader, IMainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
        paint(viewPromptCityModelList);
    }

    @Override
    public void onLoaderReset(Loader<IMainPresenter> loader) {
        String s = null;
    }

    private static class SaveMainPresenterLoader extends Loader<IMainPresenter> {

        private IMainPresenter mainPresenter;
        private IBaseRouter baseRouter;
        private IBaseActivity baseActivity;

        public SaveMainPresenterLoader(Context context, IBaseActivity baseActivity, IMainPresenter mainPresenter) {
            super(context);
            this.mainPresenter = mainPresenter;
            this.baseActivity = baseActivity;
            this.baseRouter = new Router(context);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (mainPresenter == null) {
                forceLoad();
            } else {
                deliverResult(mainPresenter);
            }
        }

        @Override
        protected void onForceLoad() {
            super.onForceLoad();
            deliverResult(new MainActivityPresenter(baseActivity, baseRouter));
        }
    }
}
