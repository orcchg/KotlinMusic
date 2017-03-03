package com.orcchg.dev.maxa.ktmusic.app.ui.music.detail

import com.orcchg.dev.maxa.ktmusic.app.ui.base.MvpPresenter
import com.orcchg.dev.maxa.ktmusic.app.ui.base.MvpView
import com.orcchg.dev.maxa.ktmusic.app.ui.viewobject.ArtistDetailsVO

interface ArtistDetailsContract {
    interface View : MvpView {
        fun setGrade(grade: Int)
        fun showArtist(artist: ArtistDetailsVO)
        fun showError()
    }

    interface Preseneter : MvpPresenter<View>
}
