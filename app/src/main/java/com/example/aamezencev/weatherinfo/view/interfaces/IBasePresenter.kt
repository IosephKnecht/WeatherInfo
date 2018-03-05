package com.example.aamezencev.weatherinfo.view.interfaces

/**
 * Created by aa.mezencev on 05.03.2018.
 */
interface IBasePresenter<BV : IBaseActivity<*>, BR : IBaseRouter> {
    fun onViewAttach(baseActivity: BV, baseRouter: BR)

    fun onViewDetach()

    fun getViewModel(): Any

    fun onDestroy()
}