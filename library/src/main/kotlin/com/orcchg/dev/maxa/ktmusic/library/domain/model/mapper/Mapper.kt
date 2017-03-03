package com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper

interface Mapper<in From, out To> {
    fun map(model: From): To
    fun map(list: List<From>): List<To>
}
