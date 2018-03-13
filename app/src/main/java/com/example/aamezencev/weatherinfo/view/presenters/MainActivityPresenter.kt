package com.example.aamezencev.weatherinfo.view.presenters

import com.example.aamezencev.weatherinfo.domain.interactors.MainActivityInteractor
import com.example.aamezencev.weatherinfo.domain.interactors.interfaces.IMainInteractor
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseActivity
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter

/**
 * Created by aa.mezencev on 07.03.2018.
 */
class MainActivityPresenter<T> : IMainInteractorOutput<T>, IMainPresenter<T> {
    var mainInteractor: IMainInteractor<T>?
    var baseRouter: IBaseRouter? = null
    var baseActivity: IBaseActivity<T> = null

    init {
        mainInteractor = MainActivityInteractor(this) as IMainInteractor<T>
    }

    override fun onViewAttach(baseActivity: IBaseActivity<Any>, baseRouter: IBaseRouter) {
        this.baseActivity = baseActivity
        this.baseRouter = baseRouter
    }

    override fun OnSucces(viewModelList: MutableList<T>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(ex: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onViewDetach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getViewModel(): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        baseActivity = null
        mainInteractor!!.unRegister()
        mainInteractor = null
        baseRouter = null
    }

    override fun getViewPromptCityModelList(city: String?) {
        mainInteractor!!.onGetViewPromptCityModelList(city)
    }

    override fun addPromptListViewToDb(viewModelList: MutableList<T>?) {
        mainInteractor!!.onAddPromptListViewToDb(viewModelList)
    }

    override fun getHashList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isVisibleFloatingButton(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun selectIsCheckedItem(): MutableList<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}