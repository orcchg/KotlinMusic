package com.orcchg.dev.maxa.ktmusic.app.injection.shared_prefs

import android.content.Context
import com.orcchg.dev.maxa.ktmusic.app.manager.SharedPrefsManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefsManagerModule(private val context: Context) {

    @Provides @Singleton
    internal fun provideSharedPrefsManager(): SharedPrefsManager {
        return SharedPrefsManager(context)
    }
}
