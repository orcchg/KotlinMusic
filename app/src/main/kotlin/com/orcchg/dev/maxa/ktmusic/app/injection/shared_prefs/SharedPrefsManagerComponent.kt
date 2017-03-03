package com.orcchg.dev.maxa.ktmusic.app.injection.shared_prefs

import com.orcchg.dev.maxa.ktmusic.app.manager.SharedPrefsManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(SharedPrefsManagerModule::class))
interface SharedPrefsManagerComponent {

    fun sharedPrefsManager(): SharedPrefsManager
}
