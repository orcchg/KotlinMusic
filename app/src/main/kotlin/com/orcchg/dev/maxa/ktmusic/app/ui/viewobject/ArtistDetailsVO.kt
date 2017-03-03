package com.orcchg.dev.maxa.ktmusic.app.ui.viewobject

data class ArtistDetailsVO(
        val id: Long,
        val name: String?,
        val coverLarge: String?,
        val description: String?,
        val genres: List<String>?,
        val link: String?,
        val albumsCount: Int,
        val tracksCount: Int)
