package com.orcchg.dev.maxa.ktmusic.data.source.remote.music.artist

import android.text.TextUtils
import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.mapper.ArtistMapper
import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.mapper.SmallArtistMapper
import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.mapper.TotalValueMapper
import com.orcchg.dev.maxa.ktmusic.data.source.repository.music.artist.ArtistDataSource
import com.orcchg.dev.maxa.ktmusic.domain.model.music.Artist
import com.orcchg.dev.maxa.ktmusic.domain.model.music.SmallArtist
import com.orcchg.dev.maxa.ktmusic.domain.model.music.TotalValue
import hugo.weaving.DebugLog
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistCloud @Inject constructor(
        private val restAdapter: ServerArtistRestAdapter,
        private val artistMapper: ArtistMapper,
        private val smallArtistMapper: SmallArtistMapper,
        private val totalValueMapper: TotalValueMapper)
    : ArtistDataSource {

    @DebugLog
    override fun artists(): Observable<List<SmallArtist>> {
        return artists(-1, 0)
    }

    @DebugLog
    override fun artists(limit: Int, offset: Int): Observable<List<SmallArtist>> {
        return artists(limit, offset, null)
    }

    @DebugLog
    override fun artists(genres: List<String>?): Observable<List<SmallArtist>> {
        return artists(-1, 0, genres)
    }

    @DebugLog
    override fun artists(limit: Int, offset: Int, genres: List<String>?): Observable<List<SmallArtist>> {
        val Limit = if (limit == -1) null else limit
        val Offset = if (offset == 0) null else offset
        val genresQuery = if (genres == null || genres.isEmpty()) null else TextUtils.join(",", genres)
        return restAdapter.artists(Limit, Offset, genresQuery).map({ entity -> smallArtistMapper.map(entity) })
    }

    @DebugLog
    override fun artist(artistId: Long): Observable<Artist> {
        return restAdapter.artist(artistId).map({ entity -> artistMapper.map(entity) })
    }

    @DebugLog
    override fun total(): Observable<TotalValue> {
        return restAdapter.total(null).map({ entity -> totalValueMapper.map(entity) })
    }

    @DebugLog
    override fun total(genres: List<String>?): Observable<TotalValue> {
        val genresQuery = if (genres == null || genres.isEmpty()) null else TextUtils.join(",", genres)
        return restAdapter.total(genresQuery).map({ entity -> totalValueMapper.map(entity) })
    }
}
