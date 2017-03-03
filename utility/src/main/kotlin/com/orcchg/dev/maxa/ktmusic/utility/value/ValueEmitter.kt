package com.orcchg.dev.maxa.ktmusic.utility.value

interface ValueEmitter<in Value> {
    fun emit(value: Value)
}
