package com.orcchg.dev.maxa.ktmusic.app.ui.base

import android.app.IntentService
import android.content.Intent
import com.orcchg.dev.maxa.ktmusic.utility.DebugSake
import com.orcchg.dev.maxa.ktmusic.utility.android.MainLooperSpy
import timber.log.Timber

abstract class BaseIntentService(name: String) : IntentService(name) {

    @DebugSake protected val mainLooperSpy = MainLooperSpy()

    private var startedHandle = false

    protected fun wasStartedHandle(): Boolean {
        return startedHandle
    }

    override fun onCreate() {
        super.onCreate()
        Timber.tag(javaClass.simpleName)
        Timber.i("onCreate(intent service=%s)", hashCode())
    }

    override fun onHandleIntent(intent: Intent?) {
        Timber.tag(javaClass.simpleName)
        Timber.i("onHandleIntent(intent service=%s)", hashCode())
        startedHandle = true
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(javaClass.simpleName)
        Timber.i("onDestroy(intent service=%s)", hashCode())
    }
}
