package com.orcchg.dev.maxa.ktmusic.domain.repository.music

import com.orcchg.dev.maxa.ktmusic.domain.model.music.Genre
import com.orcchg.dev.maxa.ktmusic.domain.model.music.TotalValue
import com.orcchg.dev.maxa.ktmusic.library.domain.repository.IRepository
import rx.Observable

/**
 * Interface that represents a Repository for getting {@link Genre} related data.
 */
interface GenreRepository : IRepository {

    /**
     * Get an [rx.Observable] which will emit a List of [Genre].
     */
    fun genres(): Observable<List<Genre>>

    /**
     * Get an [rx.Observable] which will emit total number of [Genre] items.
     */
    fun total(): Observable<TotalValue>
}
