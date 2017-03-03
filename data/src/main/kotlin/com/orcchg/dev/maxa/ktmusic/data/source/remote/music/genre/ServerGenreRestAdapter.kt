package com.orcchg.dev.maxa.ktmusic.data.source.remote.music.genre

import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.GenreEntity
import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.TotalValueEntity
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface ServerGenreRestAdapter {

    companion object {
        val ENDPOINT = "http://194.190.63.108:9123/"
    }

    @GET("/genres")
    fun genres(): Observable<List<GenreEntity>>

    @GET("/genre")
    fun genre(@Query("name") name: String): Observable<GenreEntity>

    @GET("/total_genres")
    fun total(): Observable<TotalValueEntity>
}