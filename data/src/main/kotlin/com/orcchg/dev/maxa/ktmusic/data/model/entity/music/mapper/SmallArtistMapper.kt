package com.orcchg.dev.maxa.ktmusic.data.model.entity.music.mapper

import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.SmallArtistEntity
import com.orcchg.dev.maxa.ktmusic.domain.model.music.SmallArtist
import com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper.BaseMapper
import javax.inject.Inject

class SmallArtistMapper @Inject constructor() : BaseMapper<SmallArtistEntity, SmallArtist>() {

    override fun map(`object`: SmallArtistEntity): SmallArtist {
        return SmallArtist(id = `object`.id, name = `object`.name, coverSmall = `object`.coverSmall)
    }
}
