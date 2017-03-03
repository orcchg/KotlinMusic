package com.orcchg.dev.maxa.ktmusic.domain.model.music

data class Artist(
        val id: Long,
        val name: String?,
        val coverLarge: String?,
        val coverSmall: String?,
        val description: String?,
        val genres: List<String>?,
        val link: String?,
        val albumsCount: Int,
        val tracksCount: Int) {

    /**
     * Calculates a grade of a given [Artist] as a ratio between
     * [Artist.tracksCount] and [Artist.albumsCount].
     * The more tracks and less albums a certain musician has the better
     * grade it has been given.

     * @param artist Input model
     * *
     * @return grade of musician
     */
    fun calculateGrade(): Int {
        val ratio: Float = tracksCount.toFloat() / albumsCount
        if (ratio > 10.0f) return 10
        return ratio.toInt()
    }
}
