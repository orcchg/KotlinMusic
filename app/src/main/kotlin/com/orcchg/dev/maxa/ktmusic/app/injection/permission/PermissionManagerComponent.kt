package com.orcchg.dev.maxa.ktmusic.app.injection.permission

import com.orcchg.dev.maxa.ktmusic.app.manager.PermissionManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(PermissionManagerModule::class))
interface PermissionManagerComponent {

    fun permissionManager(): PermissionManager
}
