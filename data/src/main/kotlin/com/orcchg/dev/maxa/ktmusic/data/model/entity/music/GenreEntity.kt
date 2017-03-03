package com.orcchg.dev.maxa.ktmusic.data.model.entity.music

import com.google.gson.annotations.SerializedName

data class GenreEntity(
        @SerializedName("name")   val name: String?,
        @SerializedName("genres") val genres: List<String>?)
