package com.example.aamezencev.weatherinfo.inrerfaces.view;

import java.util.List;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

public interface IBaseActivity<T> {
    void paintList(List<T> viewModelList);
}
