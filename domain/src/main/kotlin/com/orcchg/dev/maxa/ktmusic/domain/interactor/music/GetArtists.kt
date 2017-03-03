package com.orcchg.dev.maxa.ktmusic.domain.interactor.music

import com.orcchg.dev.maxa.ktmusic.domain.model.music.SmallArtist
import com.orcchg.dev.maxa.ktmusic.domain.repository.music.ArtistRepository
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.PostExecuteScheduler
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.ThreadExecutor
import com.orcchg.dev.maxa.ktmusic.library.domain.interactor.UseCase
import rx.Observable
import javax.inject.Inject

class GetArtists @Inject constructor(val artistRepository: ArtistRepository,
        threadExecutor: ThreadExecutor, postExecuteScheduler: PostExecuteScheduler)
    : UseCase<List<SmallArtist>>(threadExecutor, postExecuteScheduler) {

    private var parameters: Parameters? = null

    fun setParameters(parameters: Parameters) {
        this.parameters = parameters
    }

    override fun doAction(): Observable<List<SmallArtist>> {
        if (parameters != null) {
            return artistRepository.artists(parameters!!.limit, parameters!!.offset, parameters?.genres)
        }
        return artistRepository.artists()
    }

    /* Parameters */
    // --------------------------------------------------------------------------------------------
    data class Parameters(val limit: Int = -1, val offset: Int = 0, val genres: List<String>?)
}
