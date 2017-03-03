package com.orcchg.dev.maxa.ktmusic.library.domain.executor

import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UiThread @Inject constructor() : PostExecuteScheduler {

    override fun getScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
