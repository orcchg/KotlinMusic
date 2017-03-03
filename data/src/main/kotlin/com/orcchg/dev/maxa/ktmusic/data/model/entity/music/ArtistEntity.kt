package com.orcchg.dev.maxa.ktmusic.data.model.entity.music

import com.google.gson.annotations.SerializedName

data class ArtistEntity(
        @SerializedName("id")          val id: Long,
        @SerializedName("name")        val name: String?,
        @SerializedName("cover")       val cover: Map<String, String>?,
        @SerializedName("description") val description: String?,
        @SerializedName("genres")      val genres: List<String>?,
        @SerializedName("link")        val link: String?,
        @SerializedName("albums")      val albumsCount: Int,
        @SerializedName("tracks")      val tracksCount: Int) {

    companion object {
        val COVER_LARGE = "big"
        val COVER_SMALL = "small"
    }
}
