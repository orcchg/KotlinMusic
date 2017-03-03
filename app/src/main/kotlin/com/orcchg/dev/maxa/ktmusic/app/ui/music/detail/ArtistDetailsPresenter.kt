package com.orcchg.dev.maxa.ktmusic.app.ui.music.detail

import com.orcchg.dev.maxa.ktmusic.app.ui.base.BasePresenter
import com.orcchg.dev.maxa.ktmusic.app.ui.viewobject.mapper.ArtistDetailsMapper
import com.orcchg.dev.maxa.ktmusic.domain.interactor.music.GetArtistDetails
import com.orcchg.dev.maxa.ktmusic.domain.model.music.Artist
import rx.Subscriber
import javax.inject.Inject

class ArtistDetailsPresenter @Inject constructor(private val getArtistDetailsUseCase: GetArtistDetails)
    : BasePresenter<ArtistDetailsContract.View>(), ArtistDetailsContract.Preseneter {

    /* Lifecycle */
    // --------------------------------------------------------------------------------------------
    override fun onDestroy() {
        getArtistDetailsUseCase.unsubscribe()
        super.onDestroy()
    }

    /* Internal */
    // --------------------------------------------------------------------------------------------
    override fun freshStart() {
        getArtistDetailsUseCase.execute(object : Subscriber<Artist>() {
            override fun onCompleted() {
                // no-op
            }

            override fun onError(e: Throwable) {
                view?.showError()
            }

            override fun onNext(artist: Artist) {
                val mapper = ArtistDetailsMapper()
                val artistVO = mapper.map(artist)
                val grade = artist.calculateGrade()
                view?.showArtist(artistVO)
                view?.setGrade(grade)
            }
        })
    }

    override fun onRestoreState() {
        freshStart()  // nothing to be restored
    }
}
