package com.orcchg.dev.maxa.ktmusic.data.source.local.music

import com.orcchg.dev.maxa.ktmusic.data.source.local.music.artist.ArtistMigration
import com.orcchg.dev.maxa.ktmusic.data.source.local.music.genre.GenreMigration
import io.realm.DynamicRealm
import io.realm.RealmMigration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Migration @Inject constructor(
        private val artistMigration: ArtistMigration,
        private val genreMigration: GenreMigration)
    : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        artistMigration.migrate(realm, oldVersion, newVersion)
        genreMigration.migrate(realm, oldVersion, newVersion)
    }

    override fun hashCode(): Int {
        return 17
    }

    override fun equals(other: Any?): Boolean {
        return other is Migration
    }
}
