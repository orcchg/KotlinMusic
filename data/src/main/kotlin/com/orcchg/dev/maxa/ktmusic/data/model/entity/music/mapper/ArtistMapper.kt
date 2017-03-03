package com.orcchg.dev.maxa.ktmusic.data.model.entity.music.mapper

import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.ArtistEntity
import com.orcchg.dev.maxa.ktmusic.domain.model.music.Artist
import com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper.BaseMapper
import java.util.*
import javax.inject.Inject

class ArtistMapper @Inject constructor() : BaseMapper<ArtistEntity, Artist>() {

    override fun map(`object`: ArtistEntity): Artist {
        val emptyCovers = HashMap<String, String>()
        emptyCovers[ArtistEntity.COVER_LARGE] = ""
        emptyCovers[ArtistEntity.COVER_SMALL] = ""
        val cover = `object`.cover ?: emptyCovers

        return Artist(id = `object`.id,
                      name = `object`.name,
                      coverLarge = cover[ArtistEntity.COVER_LARGE],
                      coverSmall = cover[ArtistEntity.COVER_SMALL],
                      description = `object`.description,
                      genres = `object`.genres,
                      link = `object`.link,
                      albumsCount = `object`.albumsCount,
                      tracksCount = `object`.tracksCount)
    }
}
