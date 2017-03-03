package com.orcchg.dev.maxa.ktmusic.domain.interactor.music

import com.orcchg.dev.maxa.ktmusic.domain.model.music.TotalValue
import com.orcchg.dev.maxa.ktmusic.domain.repository.music.GenreRepository
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.PostExecuteScheduler
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.ThreadExecutor
import com.orcchg.dev.maxa.ktmusic.library.domain.interactor.UseCase
import rx.Observable
import javax.inject.Inject

class GetTotalGenres @Inject constructor(val genreRepository: GenreRepository,
        threadExecutor: ThreadExecutor, postExecuteScheduler: PostExecuteScheduler)
    : UseCase<TotalValue>(threadExecutor, postExecuteScheduler) {

    override fun doAction(): Observable<TotalValue> {
        return genreRepository.total()
    }
}
