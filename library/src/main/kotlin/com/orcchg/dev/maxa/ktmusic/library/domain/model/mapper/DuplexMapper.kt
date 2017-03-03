package com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper

interface DuplexMapper<From, To> : Mapper<From, To> {
    fun mapBack(model: To): From
    fun mapBack(list: List<To>): List<From>
}
