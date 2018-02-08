package com.example.aamezencev.weatherinfo.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;

import com.example.aamezencev.weatherinfo.fragments.PageFragment;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewCurrentWeatherModel;

import java.util.List;

/**
 * Created by aa.mezencev on 08.02.2018.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private List<ViewCurrentWeatherModel> viewCurrentWeatherModelList;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        ViewCurrentWeatherModel weatherModel = viewCurrentWeatherModelList.get(position);
        return PageFragment.newInstance(position, weatherModel.getFullInfotmation(), weatherModel.getIcon());
    }

    @Override
    public int getCount() {
        if (viewCurrentWeatherModelList != null && viewCurrentWeatherModelList.size() != 0)
            return viewCurrentWeatherModelList.size();
        else {
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return viewCurrentWeatherModelList.get(position).getDate();
    }

    public void setViewCurrentWeatherModelList(List<ViewCurrentWeatherModel> list) {
        viewCurrentWeatherModelList = list;
    }

}
