package com.orcchg.dev.maxa.ktmusic.app.ui.base

import android.content.Intent
import android.os.Bundle

interface MvpPresenter<in V : MvpView> {
    fun attachView(view: V?)
    fun detachView()

    fun onCreate(savedInstanceState: Bundle?)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun onStart()
    fun onResume()
    fun onPause()
    fun onSaveInstanceState(outState: Bundle)
    fun onStop()
    fun onDestroy()
}
