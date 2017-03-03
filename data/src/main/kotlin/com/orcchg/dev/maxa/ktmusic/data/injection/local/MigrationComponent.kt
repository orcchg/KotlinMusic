package com.orcchg.dev.maxa.ktmusic.data.injection.local

import dagger.Component
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(MigrationModule::class))
interface MigrationComponent {

    fun realmConfiguration(): RealmConfiguration
}
