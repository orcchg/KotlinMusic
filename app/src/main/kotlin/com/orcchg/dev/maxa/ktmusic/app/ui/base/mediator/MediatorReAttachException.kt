package com.orcchg.dev.maxa.ktmusic.app.ui.base.mediator

import timber.log.Timber

class MediatorReAttachException(message: String) : RuntimeException(message) {

    init {
        Timber.e(message)
    }
}
