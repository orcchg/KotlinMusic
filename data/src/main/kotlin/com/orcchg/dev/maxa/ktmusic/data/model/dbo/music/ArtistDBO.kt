package com.orcchg.dev.maxa.ktmusic.data.model.dbo.music

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ArtistDBO(
        open @PrimaryKey var id: Long = 0,
        open var name: String? = null,
        open var coverLarge: String? = null,
        open var coverSmall: String? = null,
        open var description: String? = null,
        open var genres: RealmList<GenreNameDBO>? = null,
        open var link: String? = null,
        open var albumsCount: Int = 0,
        open var tracksCount: Int = 0)
    : RealmObject() {

    companion object {
        val COLUMN_ID = "id"
        val COLUMN_NAME = "name"
        val COLUMN_COVER_LARGE = "coverLarge"
        val COLUMN_COVER_SMALL = "coverSmall"
        val COLUMN_DESCRIPTION = "description"
        val COLUMN_GENRES = "genres"
        val COLUMN_LINK = "link"
        val COLUMN_ALBUMS_COUNT = "albumsCount"
        val COLUMN_TRACKS_COUNT = "tracksCount"
    }
}
