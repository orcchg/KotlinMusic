package com.orcchg.dev.maxa.ktmusic.app.ui.base

import android.app.Service
import com.orcchg.dev.maxa.ktmusic.utility.DebugSake
import com.orcchg.dev.maxa.ktmusic.utility.android.MainLooperSpy
import timber.log.Timber

abstract class BaseService : Service() {

    @DebugSake protected val mainLooperSpy = MainLooperSpy()

    override fun onCreate() {
        super.onCreate()
        Timber.tag(javaClass.simpleName)
        Timber.i("onCreate(service=%s)", hashCode())
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(javaClass.simpleName)
        Timber.i("onDestroy(service=%s)", hashCode())
    }
}
