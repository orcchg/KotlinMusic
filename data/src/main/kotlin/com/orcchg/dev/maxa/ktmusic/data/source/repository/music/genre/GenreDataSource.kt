package com.orcchg.dev.maxa.ktmusic.data.source.repository.music.genre

import com.orcchg.dev.maxa.ktmusic.data.source.repository.DataSource
import com.orcchg.dev.maxa.ktmusic.domain.model.music.Genre
import com.orcchg.dev.maxa.ktmusic.domain.model.music.TotalValue
import rx.Observable

/**
 * Source of [Genre] data.
 */
interface GenreDataSource : DataSource {

    fun genres(): Observable<List<Genre>>

    fun genre(name: String): Observable<Genre>

    fun total(): Observable<TotalValue>
}
