package com.orcchg.dev.maxa.ktmusic.data.source.repository.music.genre

import com.orcchg.dev.maxa.ktmusic.domain.model.music.Genre
import com.orcchg.dev.maxa.ktmusic.domain.model.music.TotalValue
import com.orcchg.dev.maxa.ktmusic.domain.repository.music.GenreRepository
import rx.Observable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class GenreRepositoryImpl @Inject constructor(
        @Named("genreSource") private val cloudSource: GenreDataSource,
        @Named("genreDatabase") private val localSource: GenreDataSource)
    : GenreRepository {

    override fun genres(): Observable<List<Genre>> {
        return cloudSource.genres()
    }

    override fun total(): Observable<TotalValue> {
        return cloudSource.total()
    }
}
