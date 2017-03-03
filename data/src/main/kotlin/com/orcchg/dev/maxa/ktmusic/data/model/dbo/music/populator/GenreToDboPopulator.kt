package com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.populator

import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.GenreDBO
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.GenreNameDBO
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.mapper.GenreNameToDboMapper
import com.orcchg.dev.maxa.ktmusic.domain.model.music.Genre
import com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper.Populator
import io.realm.RealmList
import javax.inject.Inject

class GenreToDboPopulator @Inject constructor(private val mapper: GenreNameToDboMapper)
    : Populator<Genre, GenreDBO> {

    override fun populate(model: Genre, dbo: GenreDBO) {
        dbo.name = model.name
        dbo.genres = RealmList<GenreNameDBO>()
        val list = model.genres
        for (item in list!!) {
            dbo.genres?.add(mapper.map(item))
        }
    }
}
