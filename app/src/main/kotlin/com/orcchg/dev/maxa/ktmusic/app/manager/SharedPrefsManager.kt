package com.orcchg.dev.maxa.ktmusic.app.manager

import android.content.Context
import android.content.SharedPreferences
import hugo.weaving.DebugLog
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsManager @DebugLog @Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences

    init {
        Timber.d("SharedPrefsManager ctor")
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        private val SHARED_PREFS_FILE_NAME = "rxmusic_shared_preferences"
    }

    /* Read & Write shared preferences */
    // --------------------------------------------------------------------------------------------
}
