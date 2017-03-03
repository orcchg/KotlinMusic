package com.orcchg.dev.maxa.ktmusic.domain.repository.music

import com.orcchg.dev.maxa.ktmusic.domain.model.music.Artist
import com.orcchg.dev.maxa.ktmusic.domain.model.music.SmallArtist
import com.orcchg.dev.maxa.ktmusic.domain.model.music.TotalValue
import com.orcchg.dev.maxa.ktmusic.library.domain.repository.IRepository
import rx.Observable

/**
 * Interface that represents a Repository for getting {@link Artist} related data.
 */
interface ArtistRepository : IRepository {

    /**
     * Get an [rx.Observable] which will emit a List of [SmallArtist].
     */
    fun artists(): Observable<List<SmallArtist>>

    fun artists(limit: Int, offset: Int, genres: List<String>?): Observable<List<SmallArtist>>

    /**
     * Get an [rx.Observable] which will emit a [Artist].

     * @param artistId The Artist id used to retrieve Artist data.
     */
    fun artist(artistId: Long): Observable<Artist>

    /**
     * Get an [rx.Observable] which will emit total number of [Artist] items.
     */
    fun total(): Observable<TotalValue>

    /**
     * Get an [rx.Observable] which will emit total number of [Artist] items.

     * @param genres Specifies a list of genres to filter received [Artist] items by.
     */
    fun total(genres: List<String>?): Observable<TotalValue>
}
