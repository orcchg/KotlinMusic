package com.orcchg.dev.maxa.ktmusic.app.ui.viewobject.mapper

import com.orcchg.dev.maxa.ktmusic.app.ui.viewobject.ArtistListItemVO
import com.orcchg.dev.maxa.ktmusic.domain.model.music.SmallArtist
import com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper.BaseMapper
import javax.inject.Inject

class ArtistListItemMapper @Inject constructor() : BaseMapper<SmallArtist, ArtistListItemVO>() {

    override fun map(model: SmallArtist): ArtistListItemVO {
        return ArtistListItemVO(id = model.id, name = model.name, coverSmall = model.coverSmall)
    }
}
