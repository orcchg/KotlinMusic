package com.orcchg.dev.maxa.ktmusic.data.model.entity.music

import com.google.gson.annotations.SerializedName

data class SmallArtistEntity(
        @SerializedName("id")    val id: Long,
        @SerializedName("name")  val name: String?,
        @SerializedName("cover") val coverSmall: String?)
