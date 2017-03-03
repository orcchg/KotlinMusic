package com.orcchg.dev.maxa.ktmusic.app.ui.base

import android.os.Bundle
import hugo.weaving.DebugLog

abstract class BaseCompositePresenter<V : MvpView> : BasePresenter<V>() {

    protected var presenterList: MutableList<MvpPresenter<V>>? = null

    protected abstract fun providePresenterList(): MutableList<MvpPresenter<V>>

    @DebugLog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (presenterList == null) presenterList = providePresenterList()
        for (presenter in presenterList!!) {
            presenter.attachView(view)
            presenter.onCreate(savedInstanceState)
        }
    }

    override fun onStart() {
        super.onStart()
        for (presenter in presenterList!!) presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        for (presenter in presenterList!!) presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        for (presenter in presenterList!!) presenter.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        for (presenter in presenterList!!) presenter.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        for (presenter in presenterList!!) presenter.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        for (presenter in presenterList!!) {
            presenter.onDestroy()
            presenter.detachView()
        }
        presenterList?.clear()
        presenterList = null
    }
}
