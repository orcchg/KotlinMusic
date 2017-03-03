package com.orcchg.dev.maxa.ktmusic.library.domain.interactor

import com.orcchg.dev.maxa.ktmusic.library.domain.executor.PostExecuteScheduler
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.ThreadExecutor
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.schedulers.Schedulers
import rx.subscriptions.Subscriptions

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * @param threadExecutor where to push the request
 * @param postExecuteScheduler where to observe the result
 * @param <Result> Generic type of result on finish of execution.
 */
abstract class UseCase<Result> constructor(
        protected val threadExecutor: ThreadExecutor,
        protected val postExecuteScheduler: PostExecuteScheduler,
        var subscription: Subscription = Subscriptions.empty()) {

    /**
     * Builds an [rx.Observable] which will be used when executing the current [UseCase].
     */
    protected abstract fun doAction(): Observable<Result>

    /**
     * Executes the current use case.
     *
     * @param subscriber The guy who will be listen to the observable build with [.doAction].
     */
    fun execute(subscriber: Subscriber<Result>) {
        this.subscription = this.doAction()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecuteScheduler.getScheduler())
                .subscribe(subscriber)
    }

    /**
     * Unsubscribes from current [rx.Subscription].
     */
    fun unsubscribe() {
        if (!subscription.isUnsubscribed) subscription.unsubscribe()
    }
}