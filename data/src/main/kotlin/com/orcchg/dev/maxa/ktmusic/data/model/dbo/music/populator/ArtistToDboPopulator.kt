package com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.populator

import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.ArtistDBO
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.GenreNameDBO
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.mapper.GenreNameToDboMapper
import com.orcchg.dev.maxa.ktmusic.domain.model.music.Artist
import com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper.Populator
import io.realm.RealmList
import javax.inject.Inject

class ArtistToDboPopulator @Inject constructor(private val mapper: GenreNameToDboMapper)
    : Populator<Artist, ArtistDBO> {

    override fun populate(model: Artist, dbo: ArtistDBO) {
        dbo.id = model.id
        dbo.name = model.name
        dbo.coverLarge = model.coverLarge
        dbo.coverSmall = model.coverSmall
        dbo.description = model.description
        dbo.genres = RealmList<GenreNameDBO>()
        dbo.link = model.link
        dbo.albumsCount = model.albumsCount
        dbo.tracksCount = model.tracksCount
        val list = model.genres
        for (item in list!!) {
            dbo.genres?.add(mapper.map(item))
        }
    }
}
