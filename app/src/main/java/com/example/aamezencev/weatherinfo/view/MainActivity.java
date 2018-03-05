package com.example.aamezencev.weatherinfo.view;

import android.content.Loader;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.util.ObjectsCompat;
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
import com.example.aamezencev.weatherinfo.view.adapters.DiffUtilHelper;
import com.example.aamezencev.weatherinfo.view.adapters.MainAdapter;
import com.example.aamezencev.weatherinfo.view.handlers.ViewHandlers;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseActivity;
import com.example.aamezencev.weatherinfo.view.interfaces.IBasePresenter;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.presenters.IMainPresenter;
import com.example.aamezencev.weatherinfo.view.presenters.MainActivityPresenter;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityList;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements IBaseActivity {
    private CompositeDisposable disposables;
    private ActivityMainBinding binding;
    private IMainPresenter<ViewPromptCityModel> mainPresenter;
    private IBaseRouter baseRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        disposables = new CompositeDisposable();

        baseRouter = new Router(this);

        Loader<MainActivityPresenter> loader = getLoaderManager().initLoader(123, null,
                new StoragePresenter.StoragePresenterCallback<>(this,
                        () -> new MainActivityPresenter(),
                        mainActivityPresenter -> {
                            mainActivityPresenter.getHashList();
                            return null;
                        }));

        mainPresenter = (IMainPresenter) ((StoragePresenter) loader).getPresenter();
        mainPresenter.onViewAttach(this, baseRouter);
        binding.setState(getIntent().getBooleanExtra("isFirstRun", true));
        //binding.setViewPromptCityList((ViewPromptCityList) mainPresenter.getViewModel());
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

//    @BindingAdapter(value = {"android:updateViewModel"})
//    public static void updateViewModel(RecyclerView recyclerView, ViewPromptCityList viewModel) {
//        DiffUtilHelper<ViewPromptCityModel> diffUtilHelper = new DiffUtilHelper<>(((MainAdapter) recyclerView.getAdapter()).getViewPromptCityModelList(),
//                viewModel.getList(), (oldModel, newModel) -> ObjectsCompat.equals(oldModel.getKey(), newModel.getKey()));
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilHelper);
//        ((MainAdapter) recyclerView.getAdapter()).setViewPromptCityModelList(viewModel.getList());
//        diffResult.dispatchUpdatesTo(recyclerView.getAdapter());
//        //handlers.setFabIsVisible(mainPresenter.isVisibleFloatingButton());
//    }

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
}
