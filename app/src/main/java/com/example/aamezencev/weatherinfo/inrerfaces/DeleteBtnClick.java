package com.example.aamezencev.weatherinfo.inrerfaces;

import android.view.View;

import com.example.aamezencev.weatherinfo.data.PromptCityDbModel;

/**
 * Created by aa.mezencev on 25.01.2018.
 */

public interface DeleteBtnClick {
    void deleteBtnClick(View view, Long key,int position);
}
