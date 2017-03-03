package com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.populator

import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.GenreNameDBO
import com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper.Populator
import javax.inject.Inject

class GenreNameToDboPopulator @Inject constructor() : Populator<String, GenreNameDBO> {

    override fun populate(str: String, dbo: GenreNameDBO) {
        dbo.name = str
    }
}
