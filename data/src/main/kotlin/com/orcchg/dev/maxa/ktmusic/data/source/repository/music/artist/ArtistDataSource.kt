package com.orcchg.dev.maxa.ktmusic.data.source.repository.music.artist

import com.orcchg.dev.maxa.ktmusic.data.source.repository.DataSource
import com.orcchg.dev.maxa.ktmusic.domain.model.music.Artist
import com.orcchg.dev.maxa.ktmusic.domain.model.music.SmallArtist
import com.orcchg.dev.maxa.ktmusic.domain.model.music.TotalValue
import rx.Observable

/**
 * Source of [Artist] data.
 */
interface ArtistDataSource : DataSource {

    fun artists(): Observable<List<SmallArtist>>
    fun artists(limit: Int, offset: Int): Observable<List<SmallArtist>>
    fun artists(genres: List<String>?): Observable<List<SmallArtist>>
    fun artists(limit: Int, offset: Int, genres: List<String>?): Observable<List<SmallArtist>>

    fun artist(artistId: Long): Observable<Artist>

    fun total(): Observable<TotalValue>
    fun total(genres: List<String>?): Observable<TotalValue>
}
