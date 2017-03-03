package com.orcchg.dev.maxa.ktmusic.app.injection.permission

import android.content.Context
import com.orcchg.dev.maxa.ktmusic.app.manager.PermissionManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PermissionManagerModule(private val context: Context) {

    @Provides @Singleton
    internal fun providePermissionManager(): PermissionManager {
        return PermissionManager(context)
    }
}
