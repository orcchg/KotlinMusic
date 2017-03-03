package com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.mapper

import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.SmallArtistDBO
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.populator.SmallArtistToDboPopulator
import com.orcchg.dev.maxa.ktmusic.domain.model.music.SmallArtist
import com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper.BaseDuplexMapper
import javax.inject.Inject

class SmallArtistToDboMapper @Inject constructor(private val populator: SmallArtistToDboPopulator)
    : BaseDuplexMapper<SmallArtist, SmallArtistDBO>() {

    /* Direct mapping */
    // ------------------------------------------
    override fun map(model: SmallArtist): SmallArtistDBO {
        val dbo = SmallArtistDBO()
        populator.populate(model, dbo)
        return dbo
    }

    /* Backward mapping */
    // ------------------------------------------
    override fun mapBack(model: SmallArtistDBO): SmallArtist {
        return SmallArtist(id = model.id, name = model.name, coverSmall = model.coverSmall)
    }
}
