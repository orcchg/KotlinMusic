package com.orcchg.dev.maxa.ktmusic.data.model.entity.music.mapper

import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.GenreEntity
import com.orcchg.dev.maxa.ktmusic.domain.model.music.Genre
import com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper.BaseMapper
import javax.inject.Inject

class GenreMapper @Inject constructor() : BaseMapper<GenreEntity, Genre>() {

    override fun map(`object`: GenreEntity): Genre {
        return Genre(name = `object`.name, genres = `object`.genres)
    }
}
