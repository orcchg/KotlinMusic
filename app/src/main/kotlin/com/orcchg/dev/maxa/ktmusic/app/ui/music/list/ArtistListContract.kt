package com.orcchg.dev.maxa.ktmusic.app.ui.music.list

import com.orcchg.dev.maxa.ktmusic.app.ui.base.MvpListView
import com.orcchg.dev.maxa.ktmusic.app.ui.base.MvpPresenter
import com.orcchg.dev.maxa.ktmusic.app.ui.viewobject.ArtistListItemVO

interface ArtistListContract {
    interface View : MvpListView {
        fun openArtistDetails(artistId: Long)

        fun showArtists(artists: List<ArtistListItemVO>)
        fun showError()
        fun showLoading()
    }

    interface Preseneter : MvpPresenter<View> {
        fun retry()
        fun onScroll(itemsLeftToEnd: Int)
    }
}
