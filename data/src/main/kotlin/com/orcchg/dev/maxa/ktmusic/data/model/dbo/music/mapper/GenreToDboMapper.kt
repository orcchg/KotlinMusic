package com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.mapper

import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.GenreDBO
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.populator.GenreToDboPopulator
import com.orcchg.dev.maxa.ktmusic.domain.model.music.Genre
import com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper.BaseDuplexMapper
import java.util.*
import javax.inject.Inject

class GenreToDboMapper @Inject constructor(
        private val mapper: GenreNameToDboMapper,
        private val populator: GenreToDboPopulator)
    : BaseDuplexMapper<Genre, GenreDBO>() {

    /* Direct mapping */
    // ------------------------------------------
    override fun map(model: Genre): GenreDBO {
        val dbo = GenreDBO()
        populator.populate(model, dbo)
        return dbo
    }

    /* Backward mapping */
    // ------------------------------------------
    override fun mapBack(model: GenreDBO): Genre {
        val genres = ArrayList<String>()
        for (dbo in model.genres!!) {
            genres.add(mapper.mapBack(dbo))
        }
        return Genre(name = model.name, genres = genres)
    }
}
