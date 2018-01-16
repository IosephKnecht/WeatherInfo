package com.example.aamezencev.weatherinfo;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.aamezencev.weatherinfo.Fragments.MainRetainFragment;
import com.example.aamezencev.weatherinfo.Recievers.CityReceiver;
import com.example.aamezencev.weatherinfo.Requests.GetGeoToPlaceId;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver cityReceiver;
    private MainRetainFragment mainRetainFragment;
    private View spinner;


    public MainActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cityReceiver = new CityReceiver();

        IntentFilter intentFilter = new IntentFilter(CityReceiver.CITY_RECEIVER_ID);
        registerReceiver(cityReceiver, intentFilter);

        Fragment fragment = getFragmentManager().findFragmentById(R.id.mainRetainFragment);
        if (fragment != null) {
            mainRetainFragment = (MainRetainFragment) fragment;
            mainRetainFragment.paint();
        }

        paintSpinner();

    }

    @Override
    protected void onStop() {
        unregisterReceiver(cityReceiver);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        cityReceiver = null;

        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        RxSearchView.queryTextChanges(searchView)
                .debounce(2000, TimeUnit.MILLISECONDS)
                .subscribe(aVoid->{
                    if(aVoid.length()>=4){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                createMainReatainFragment(aVoid.toString());
                            }
                        });
                    }
                });

        return true;
    }

    private void createMainReatainFragment(String query) {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.mainRetainFragment);

        if (fragment == null || (fragment != null && fragment.getArguments().getString("city").toString().equals(query) == false)) {
            spinner.setVisibility(View.VISIBLE);
            mainRetainFragment = new MainRetainFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city", query);
            mainRetainFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainRetainFragment, mainRetainFragment);
            fragmentTransaction.commit();
        } else {
            mainRetainFragment.paint();
        }
    }
}
