package com.example.aamezencev.weatherinfo.view.viewModels

import android.databinding.BaseObservable
import android.databinding.Bindable

/**
 * Created by aa.mezencev on 05.03.2018.
 */
class ViewPromptCityList() : BaseObservable() {
    var list: List<ViewPromptCityModel>? = List(DEFAULT_BUFFER_SIZE, { ViewPromptCityModel() })
        @Bindable
        get() = field
        set(value) {
            field = value
            //notifyPropertyChanged(BR.viewPromptCityList)
        }
}