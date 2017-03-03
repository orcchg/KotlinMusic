package com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.populator

import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.SmallArtistDBO
import com.orcchg.dev.maxa.ktmusic.domain.model.music.SmallArtist
import com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper.Populator
import javax.inject.Inject

class SmallArtistToDboPopulator @Inject constructor() : Populator<SmallArtist, SmallArtistDBO> {

    override fun populate(model: SmallArtist, dbo: SmallArtistDBO) {
        dbo.id = model.id
        dbo.name = model.name
        dbo.coverSmall = model.coverSmall
    }
}
