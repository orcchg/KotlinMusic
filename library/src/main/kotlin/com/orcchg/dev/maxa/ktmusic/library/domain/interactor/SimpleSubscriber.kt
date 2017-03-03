package com.orcchg.dev.maxa.ktmusic.library.domain.interactor

import rx.Subscriber

/**
 * Default subscriber base class to be used whenever you want default error handling.
 */
class SimpleSubscriber<Result> constructor(subscriber: Subscriber<Result>?, shareSubscriptions: Boolean)
    : rx.Subscriber<Result>(subscriber, shareSubscriptions) {

    constructor() : this(null, false)

    constructor(subscriber: Subscriber<Result>?) : this(subscriber, false)

    override fun onCompleted() {
        // no-op
    }

    override fun onError(e: Throwable) {
        // no-op
    }

    override fun onNext(result: Result) {
        // no-op
    }
}
