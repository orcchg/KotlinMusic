package com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.mapper

import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.ArtistDBO
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.populator.ArtistToDboPopulator
import com.orcchg.dev.maxa.ktmusic.domain.model.music.Artist
import com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper.BaseDuplexMapper
import java.util.*
import javax.inject.Inject

class ArtistToDboMapper @Inject constructor(
        private val mapper: GenreNameToDboMapper,
        private val populator: ArtistToDboPopulator)
    : BaseDuplexMapper<Artist, ArtistDBO>() {

    /* Direct mapping */
    // ------------------------------------------
    override fun map(model: Artist): ArtistDBO {
        val dbo = ArtistDBO()
        populator.populate(model, dbo)
        return dbo
    }

    /* Backward mapping */
    // ------------------------------------------
    override fun mapBack(model: ArtistDBO): Artist {
        val genres = ArrayList<String>()
        for (dbo in model.genres!!) {
            genres.add(mapper.mapBack(dbo))
        }
        return Artist(id = model.id,
                      name = model.name,
                      coverLarge = model.coverLarge,
                      coverSmall = model.coverSmall,
                      description = model.description,
                      genres = genres,
                      link = model.link,
                      albumsCount = model.albumsCount,
                      tracksCount = model.tracksCount)
    }
}
