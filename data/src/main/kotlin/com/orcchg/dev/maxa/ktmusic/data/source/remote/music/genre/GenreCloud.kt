package com.orcchg.dev.maxa.ktmusic.data.source.remote.music.genre

import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.mapper.GenreMapper
import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.mapper.TotalValueMapper
import com.orcchg.dev.maxa.ktmusic.data.source.repository.music.genre.GenreDataSource
import com.orcchg.dev.maxa.ktmusic.domain.model.music.Genre
import com.orcchg.dev.maxa.ktmusic.domain.model.music.TotalValue
import hugo.weaving.DebugLog
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenreCloud @Inject constructor(
        private val restAdapter: ServerGenreRestAdapter,
        private val genreMapper: GenreMapper,
        private val totalValueMapper: TotalValueMapper)
    : GenreDataSource {

    @DebugLog
    override fun genres(): Observable<List<Genre>> {
        return restAdapter.genres().map({ entity -> genreMapper.map(entity) })
    }

    @DebugLog
    override fun genre(name: String): Observable<Genre> {
        return restAdapter.genre(name).map({ entity -> genreMapper.map(entity) })
    }

    @DebugLog
    override fun total(): Observable<TotalValue> {
        return restAdapter.total().map({ entity -> totalValueMapper.map(entity) })
    }
}
