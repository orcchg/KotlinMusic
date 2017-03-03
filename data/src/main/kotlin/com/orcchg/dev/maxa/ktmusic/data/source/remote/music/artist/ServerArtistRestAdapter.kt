package com.orcchg.dev.maxa.ktmusic.data.source.remote.music.artist

import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.ArtistEntity
import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.SmallArtistEntity
import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.TotalValueEntity
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface ServerArtistRestAdapter {

    companion object {
        val ENDPOINT = "http://194.190.63.108:9123/"
    }

    @GET("/all")
    fun artists(@Query("limit") limit: Int?, @Query("offset") offset: Int?,
                @Query("genres") genres: String?): Observable<List<SmallArtistEntity>>

    @GET("/single")
    fun artist(@Query("id") artistId: Long): Observable<ArtistEntity>

    @GET("/total")
    fun total(@Query("genres") genres: String?): Observable<TotalValueEntity>
}
