package com.example.aamezencev.weatherinfo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aamezencev.weatherinfo.R;

/**
 * Created by aa.mezencev on 08.02.2018.
 */

public class PageFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;
    int backColor;

    public static PageFragment newInstance(int page, String fullInfWeather, String image, String name) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putString("fullInfWeather", fullInfWeather);
        arguments.putString("image", image);
        arguments.putString("name", name);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page, null);

        TextView tvPage = (TextView) view.findViewById(R.id.tvPage);
        tvPage.setText(getArguments().getString("fullInfWeather"));
        tvPage.setBackgroundColor(backColor);

        ImageView weatherImage = (ImageView) view.findViewById(R.id.weatherImage);

        Glide.with(getActivity())
                .load("http://openweathermap.org/img/w/" + getArguments().getString("image") + ".png")
                .into(weatherImage);

        getActivity().setTitle("Station name: " + getArguments().getString("name"));
        return view;
    }
}
