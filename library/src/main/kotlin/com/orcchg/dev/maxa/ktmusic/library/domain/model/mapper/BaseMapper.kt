package com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper

abstract class BaseMapper<in From, out To> : Mapper<From, To> {

    override fun map(list: List<From>): List<To> {
        return list.map { from -> map(from) }
    }
}
