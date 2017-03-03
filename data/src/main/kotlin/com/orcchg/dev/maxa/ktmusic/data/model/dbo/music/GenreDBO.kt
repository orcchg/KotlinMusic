package com.orcchg.dev.maxa.ktmusic.data.model.dbo.music

import io.realm.RealmList
import io.realm.RealmObject

open class GenreDBO(
        open var name: String? = null,
        open var genres: RealmList<GenreNameDBO>? = null)
    : RealmObject() {

    companion object {
        val COLUMN_NAME = "name"
        val COLUMN_GENRES = "genres"
    }
}
