package com.orcchg.dev.maxa.ktmusic.domain.interactor.music

import com.orcchg.dev.maxa.ktmusic.domain.model.music.Artist
import com.orcchg.dev.maxa.ktmusic.domain.repository.music.ArtistRepository
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.PostExecuteScheduler
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.ThreadExecutor
import com.orcchg.dev.maxa.ktmusic.library.domain.interactor.UseCase
import rx.Observable
import javax.inject.Inject

class GetArtistDetails @Inject constructor(val artistId: Long, val artistRepository: ArtistRepository,
        threadExecutor: ThreadExecutor, postExecuteScheduler: PostExecuteScheduler)
    : UseCase<Artist>(threadExecutor, postExecuteScheduler) {

    override fun doAction(): Observable<Artist> {
        return artistRepository.artist(artistId)
    }
}
