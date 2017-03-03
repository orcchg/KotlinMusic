package com.orcchg.dev.maxa.ktmusic.domain.interactor.music

import com.orcchg.dev.maxa.ktmusic.domain.model.music.TotalValue
import com.orcchg.dev.maxa.ktmusic.domain.repository.music.ArtistRepository
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.PostExecuteScheduler
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.ThreadExecutor
import com.orcchg.dev.maxa.ktmusic.library.domain.interactor.UseCase
import rx.Observable
import javax.inject.Inject

class GetTotalArtists @Inject constructor(val artistRepository: ArtistRepository,
        threadExecutor: ThreadExecutor, postExecuteScheduler: PostExecuteScheduler)
    : UseCase<TotalValue>(threadExecutor, postExecuteScheduler) {

    private var parameters: Parameters? = null

    fun setParameters(parameters: Parameters) {
        this.parameters = parameters
    }

    override fun doAction(): Observable<TotalValue> {
        if (parameters != null) {
            return artistRepository.total(parameters?.genres)
        }
        return artistRepository.total()
    }

    /* Parameters */
    // --------------------------------------------------------------------------------------------
    data class Parameters(val genres: List<String>?)
}
