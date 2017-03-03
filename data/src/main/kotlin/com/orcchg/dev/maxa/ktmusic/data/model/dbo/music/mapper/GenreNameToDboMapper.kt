package com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.mapper

import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.GenreNameDBO
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.populator.GenreNameToDboPopulator
import com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper.BaseDuplexMapper
import javax.inject.Inject

class GenreNameToDboMapper @Inject constructor(private val populator: GenreNameToDboPopulator)
    : BaseDuplexMapper<String, GenreNameDBO>() {

    /* Direct mapping */
    // ------------------------------------------
    override fun map(model: String): GenreNameDBO {
        val dbo = GenreNameDBO()
        populator.populate(model, dbo)
        return dbo
    }

    /* Backward mapping */
    // ------------------------------------------
    override fun mapBack(model: GenreNameDBO): String {
        return model.name ?: ""
    }
}
