package com.orcchg.dev.maxa.ktmusic.data.source.repository

import timber.log.Timber

object RepoUtility {

    fun checkLimitAndOffset(limit: Int, offset: Int) {
        if (limit < 0 && offset != 0) {
            Timber.e("Negative limit is specified to fetch all items, offset must be equal to zero! Actual offset: %s", offset)
            throw IllegalArgumentException("Wrong offset value, must be 0, when limit is negative!")
        }
    }

    fun checkListBounds(boundary: Int, total: Int) {
        if (boundary >= total) {
            Timber.e("Boundary (offset + limit) %s exceeds total items count %s", boundary, total)
            throw ArrayIndexOutOfBoundsException(boundary)
        }
    }
}
