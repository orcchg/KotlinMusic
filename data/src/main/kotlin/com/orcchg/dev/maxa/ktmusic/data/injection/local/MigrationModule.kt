package com.orcchg.dev.maxa.ktmusic.data.injection.local

import com.orcchg.dev.maxa.ktmusic.data.source.local.music.Migration
import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
class MigrationModule {

    @Provides @Singleton
    internal fun provideRealmConfiguration(migration: Migration): RealmConfiguration {
        return RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(migration)
                .build()
    }
}
