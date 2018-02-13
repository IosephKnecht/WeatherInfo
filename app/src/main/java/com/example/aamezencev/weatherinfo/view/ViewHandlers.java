package com.example.aamezencev.weatherinfo.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.example.aamezencev.weatherinfo.BR;
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter;
import com.example.aamezencev.weatherinfo.view.presenters.IMainPresenter;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by aa.mezencev on 13.02.2018.
 */

public class ViewHandlers extends BaseObservable {
    private CompositeDisposable disposables;
    private IMainPresenter mainPresenter;
    private IBaseRouter baseRouter;
    private int fabIsVisible = View.INVISIBLE;
    private int spinnerIsVisible = View.INVISIBLE;

    public ViewHandlers(IMainPresenter mainPresenter, IBaseRouter baseRouter) {
        disposables = new CompositeDisposable();
        this.mainPresenter = mainPresenter;
        this.baseRouter = baseRouter;
    }

    public void onFabClick(View view, boolean state) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("isFirstRun", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("state", state);
        editor.commit();

        mainPresenter.addPromptListViewToDb(mainPresenter.selectIsCheckedItem());
        baseRouter.openWeatherListActivity();
    }

    public void onCheckBoxClick(View view, ViewPromptCityModel city) {
        boolean state = city.isChecked();
        city.setChecked(!state);
        setFabIsVisible(mainPresenter.isVisibleFloatingButton());
        //fab.setVisibility(mainPresenter.isVisibleFloatingButton());
    }

    @BindingAdapter("bind:vis")
    public static void vis(View view, int fabIsVisible) {
        view.setVisibility(fabIsVisible);
    }

    @BindingAdapter("bind:onChangeVisible")
    public static void spinnerOnChangeVisible(View view, int spinnerIsVisible) {
        view.setVisibility(spinnerIsVisible);
    }

    public SearchView.OnQueryTextListener getQueryTextListener(View view) {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                disposables.add(RxSearchView.queryTextChanges((SearchView) view)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(str -> str.length() >= 4)
                        .subscribe(aVoid -> {
                            setSpinnerIsVisible(View.VISIBLE);
                            mainPresenter.getViewPromptCityModelList(aVoid.toString());
                        }));
                return true;
            }
        };
    }

    public void onDestroy() {
        mainPresenter = null;
        baseRouter = null;
        disposables.dispose();
        disposables = null;
    }

    @Bindable
    public int getFabIsVisible() {
        return fabIsVisible;
    }

    public void setFabIsVisible(int fabIsVisible) {
        this.fabIsVisible = fabIsVisible;
        notifyPropertyChanged(BR.fabIsVisible);
    }

    @Bindable
    public int getSpinnerIsVisible() {
        return spinnerIsVisible;
    }

    public void setSpinnerIsVisible(int spinnerIsVisible) {
        this.spinnerIsVisible = spinnerIsVisible;
        notifyPropertyChanged(BR.spinnerIsVisible);
    }
}
