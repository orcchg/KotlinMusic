package com.orcchg.dev.maxa.ktmusic.data.source.repository.music.artist

import com.orcchg.dev.maxa.ktmusic.domain.model.music.Artist
import com.orcchg.dev.maxa.ktmusic.domain.model.music.SmallArtist
import com.orcchg.dev.maxa.ktmusic.domain.model.music.TotalValue
import com.orcchg.dev.maxa.ktmusic.domain.repository.music.ArtistRepository
import rx.Observable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Concrete implementation of [ArtistRepository]. This repository rules where to fetch data
 * from. It checks local storage and in case of cache-miss requests cloud source for data.
 * It also manages to cache data locally.
 */
@Singleton
class ArtistRepositoryImpl @Inject constructor(
        @Named("artistCloud") private val cloudSource: ArtistDataSource,
        @Named("artistDatabase") private val localSource: ArtistDataSource)
    : ArtistRepository {

    override fun artists(): Observable<List<SmallArtist>> {
        return cloudSource.artists()
    }

    override fun artists(limit: Int, offset: Int, genres: List<String>?): Observable<List<SmallArtist>> {
        return cloudSource.artists(limit, offset, genres)
    }

    override fun artist(artistId: Long): Observable<Artist> {
        return cloudSource.artist(artistId)
    }

    override fun total(): Observable<TotalValue> {
        return cloudSource.total()
    }

    override fun total(genres: List<String>?): Observable<TotalValue> {
        return cloudSource.total(genres)
    }
}
