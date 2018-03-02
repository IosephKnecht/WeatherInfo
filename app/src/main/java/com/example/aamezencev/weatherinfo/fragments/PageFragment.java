package com.example.aamezencev.weatherinfo.fragments;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
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
import com.example.aamezencev.weatherinfo.databinding.PageBinding;

/**
 * Created by aa.mezencev on 08.02.2018.
 */

public class PageFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    private int pageNumber;
    private int backColor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.page, null);
        PageBinding binding = DataBindingUtil.inflate(inflater,R.layout.page,container,false);
        View view = binding.getRoot();
        binding.setFullInf(getArguments().getString("fullInf"));
        binding.setIcon(getArguments().getString("icon"));

//        TextView tvPage = (TextView) view.findViewById(R.id.tvPage);
//        tvPage.setText(getArguments().getString("fullInfWeather"));
//        tvPage.setBackgroundColor(backColor);

//        ImageView weatherImage = (ImageView) view.findViewById(R.id.weatherImage);

//        Glide.with(getActivity())
//                .load("http://openweathermap.org/img/w/" + getArguments().getString("image") + ".png")
//                .into(weatherImage);

        //getActivity().setTitle("Station name: " + getArguments().getString("name"));
        return view;
    }

    @BindingAdapter("bind:loadImage")
    public static void loadImage(View view, String icon) {
        Glide.with(view.getContext())
                .load("http://openweathermap.org/img/w/" + icon + ".png")
                .into((ImageView) view);
    }
}
