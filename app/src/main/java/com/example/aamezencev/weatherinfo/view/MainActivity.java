package com.example.aamezencev.weatherinfo.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.databinding.ActivityMainBinding;
import com.example.aamezencev.weatherinfo.view.adapters.DiffUtilMainAdapter;
import com.example.aamezencev.weatherinfo.view.adapters.MainAdapter;
import com.example.aamezencev.weatherinfo.view.handlers.ViewHandlers;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseActivity;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.presenters.IMainPresenter;
import com.example.aamezencev.weatherinfo.view.presenters.MainActivityPresenter;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<IMainPresenter>,
        IBaseActivity {
    private CompositeDisposable disposables;
    private ActivityMainBinding binding;

    private IMainPresenter mainPresenter;
    private IBaseRouter baseRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        disposables = new CompositeDisposable();

        baseRouter = new Router(this);

        mainPresenter = ((SaveMainPresenterLoader) getLoaderManager().initLoader(1, null, this)).getMainPresenter();
        mainPresenter.onViewAttach(this, baseRouter);
        binding.setState(getIntent().getBooleanExtra("isFirstRun", true));
        ViewHandlers viewHandlers = new ViewHandlers(mainPresenter, baseRouter);
        binding.setHandlers(viewHandlers);
        binding.setAdapter(new MainAdapter(viewHandlers));
    }

    @BindingAdapter(value = {"android:setAdapter"}, requireAll = false)
    public static void setAdapter(RecyclerView recyclerView, MainAdapter adapter) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        mainPresenter.onViewDetach();
        mainPresenter = null;
        baseRouter = null;
        disposables.dispose();
        disposables = null;
        binding = null;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(binding.getHandlers().getQueryTextListener(searchView));

        return true;
    }

    private void updateRecyclerView(List<ViewPromptCityModel> newList) {
        DiffUtilMainAdapter diffUtilMainAdapter = new DiffUtilMainAdapter(binding.getAdapter().getViewPromptCityModelList(), newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilMainAdapter);
        binding.getAdapter().setViewPromptCityModelList(newList);
        diffResult.dispatchUpdatesTo(binding.getAdapter());
        binding.getHandlers().setFabIsVisible(mainPresenter.isVisibleFloatingButton());
    }

    @Override
    public void paintList(List viewModelList) {
        if (binding.getHandlers() != null) {
            binding.getHandlers().setSpinnerIsVisible(View.INVISIBLE);
        }
        updateRecyclerView(viewModelList);
    }

    @Override
    public Loader<IMainPresenter> onCreateLoader(int i, Bundle bundle) {
        android.content.Loader loader = null;
        if (i == 1) {
            mainPresenter = new MainActivityPresenter();
            loader = new SaveMainPresenterLoader(this, mainPresenter);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<IMainPresenter> loader, IMainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
        this.mainPresenter.getHashList();
    }

    @Override
    public void onLoaderReset(Loader<IMainPresenter> loader) {
        String s = null;
    }

    private static class SaveMainPresenterLoader extends Loader<IMainPresenter> {

        private IMainPresenter mainPresenter;

        private SaveMainPresenterLoader(Context context, IMainPresenter mainPresenter) {
            super(context);
            this.mainPresenter = mainPresenter;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            deliverResult(mainPresenter);
        }

        @Override
        protected void onReset() {
            super.onReset();
            mainPresenter.onDestroy();
            mainPresenter = null;
        }

        private IMainPresenter getMainPresenter() {
            return mainPresenter;
        }
    }
}
