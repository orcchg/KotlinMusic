package com.orcchg.dev.maxa.ktmusic.data.model.dbo.music

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SmallArtistDBO(
        open @PrimaryKey var id: Long = 0,
        open var name: String? = null,
        open var coverSmall: String? = null)
    : RealmObject() {

    companion object {
        val COLUMN_ID = "id"
        val COLUMN_NAME = "name"
        val COLUMN_COVER_SMALL = "coverSmall"
    }
}
