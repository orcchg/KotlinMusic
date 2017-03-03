package com.orcchg.dev.maxa.ktmusic.app.ui.viewobject.mapper

import com.orcchg.dev.maxa.ktmusic.app.ui.viewobject.ArtistDetailsVO
import com.orcchg.dev.maxa.ktmusic.domain.model.music.Artist
import com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper.BaseMapper
import javax.inject.Inject

class ArtistDetailsMapper @Inject constructor() : BaseMapper<Artist, ArtistDetailsVO>() {

    override fun map(model: Artist): ArtistDetailsVO {
        return ArtistDetailsVO(id = model.id,
                               name = model.name,
                               coverLarge = model.coverLarge,
                               description = model.description,
                               genres = model.genres,
                               link = model.link,
                               albumsCount = model.albumsCount,
                               tracksCount = model.tracksCount)
    }
}
