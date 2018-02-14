package com.example.aamezencev.weatherinfo.view.adapters;

import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
        PageFragment pageFragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fullInf", weatherModel.getFullInfotmation());
        bundle.putString("icon", weatherModel.getIcon());
        pageFragment.setArguments(bundle);
        return pageFragment;
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
