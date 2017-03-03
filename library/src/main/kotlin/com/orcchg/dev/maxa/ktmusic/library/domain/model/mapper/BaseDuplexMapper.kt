package com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper

abstract class BaseDuplexMapper<From, To> : DuplexMapper<From, To> {

    override fun map(list: List<From>): List<To> {
        return list.map { from -> map(from) }
    }

    override fun mapBack(list: List<To>): List<From> {
        return list.map { from -> mapBack(from) }
    }
}
