package com.orcchg.dev.maxa.ktmusic.data.model.dbo.music

import io.realm.RealmObject

open class GenreNameDBO(open var name: String? = null) : RealmObject() {

    companion object {
        val COLUMN_NAME = "name"
    }
}
