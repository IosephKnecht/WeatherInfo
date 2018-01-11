package com.example.aamezencev.weatherinfo;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aamezencev.weatherinfo.Fragments.MainRetainFragment;
import com.example.aamezencev.weatherinfo.Recievers.CityReceiver;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private BroadcastReceiver cityReceiver;
    private MainRetainFragment mainRetainFragment;


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
        //sendBroadcast(new Intent(CityReceiver.CITY_RECEIVER_ID));
        //paintSpinner();
    }

//    private void paintSpinner() {
//        FrameLayout frameLayout = findViewById(R.id.flSpinner);
//        LayoutInflater layoutInflater = getLayoutInflater();
//        spinner = layoutInflater.inflate(R.layout.spinner_layout, frameLayout, false);
//        spinner.setVisibility(View.VISIBLE);
//        frameLayout.addView(spinner);
//
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        Fragment fragment = getFragmentManager().findFragmentById(R.id.mainRetainFragment);

        if (fragment == null || (fragment != null && fragment.getArguments().getString("city").toString().equals(query) == false)) {
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

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
