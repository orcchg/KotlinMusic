package com.orcchg.dev.maxa.ktmusic.data.source.local.music.genre

import io.realm.DynamicRealm
import io.realm.RealmMigration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenreMigration @Inject constructor() : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        // no-op
    }
}
