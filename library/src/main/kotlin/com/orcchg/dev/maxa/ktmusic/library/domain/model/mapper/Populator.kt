package com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper

interface Populator<in From, in To> {
    fun populate(from: From, to: To)
}
