package com.example.aamezencev.weatherinfo.view

import android.app.LoaderManager
import android.content.Context
import android.content.Loader
import android.os.Bundle
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseActivity
import com.example.aamezencev.weatherinfo.view.interfaces.IBasePresenter
import com.example.aamezencev.weatherinfo.view.interfaces.IBaseRouter

/**
 * Created by aa.mezencev on 05.03.2018.
 */
class StoragePresenter<T : IBasePresenter<IBaseActivity<*>, IBaseRouter>>(context: Context,
                                                                          set: () -> T,
                                                                          callMethods: (T) -> Unit) : Loader<T>(context) {

    val presenter: T

    init {
        presenter = set()
        callMethods(presenter)
    }

    override fun onReset() {
        super.onReset()
        presenter!!.onDestroy()
    }

    class StoragePresenterCallback<T : IBasePresenter<IBaseActivity<*>, IBaseRouter>>(var context: Context,
                                                                                      var lamda1: () -> T,
                                                                                      var lamda2: (T) -> Unit) : LoaderManager.LoaderCallbacks<T> {
        override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<T> {
            return StoragePresenter<T>(context, lamda1, lamda2)
        }

        override fun onLoadFinished(p0: Loader<T>?, p1: T) {
        }

        override fun onLoaderReset(p0: Loader<T>?) {
        }

    }
}