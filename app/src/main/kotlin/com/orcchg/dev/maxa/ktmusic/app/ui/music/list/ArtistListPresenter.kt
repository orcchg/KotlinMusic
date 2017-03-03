package com.orcchg.dev.maxa.ktmusic.app.ui.music.list

import android.view.View
import com.orcchg.dev.maxa.ktmusic.app.ui.base.BaseListPresenter
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.BaseAdapter
import com.orcchg.dev.maxa.ktmusic.app.ui.viewobject.mapper.ArtistListItemMapper
import com.orcchg.dev.maxa.ktmusic.domain.interactor.music.GetArtists
import com.orcchg.dev.maxa.ktmusic.domain.interactor.music.GetTotalArtists
import com.orcchg.dev.maxa.ktmusic.domain.model.music.SmallArtist
import com.orcchg.dev.maxa.ktmusic.domain.model.music.TotalValue
import hugo.weaving.DebugLog
import rx.Subscriber
import timber.log.Timber
import javax.inject.Inject

class ArtistListPresenter @Inject constructor(
        private val getArtistsUseCase: GetArtists,
        private val getTotalArtistsUseCase: GetTotalArtists)
    : BaseListPresenter<ArtistListContract.View>(), ArtistListContract.Preseneter {

    private val artistsAdapter: ArtistListAdapter

    // --------------------------------------------------------------------------------------------
    init {
        this.artistsAdapter = createListAdapter() as ArtistListAdapter
    }

    override fun createListAdapter(): BaseAdapter<*, *> {
        val l = { view: View, artistId: Long -> openArtistDetails(artistId) }
        val artistsAdapter = ArtistListAdapter(l as ItemClickListener)
        this.artistsAdapter.onErrorClickListener = View.OnClickListener { view -> retryLoadMore() }
        return artistsAdapter
    }

    /* Lifecycle */
    // --------------------------------------------------------------------------------------------
    override fun onDestroy() {
        getArtistsUseCase.unsubscribe()
        getTotalArtistsUseCase.unsubscribe()
        super.onDestroy()
    }

    /* Contract */
    // --------------------------------------------------------------------------------------------
    @DebugLog
    override fun retry() {
        dropListStat()
        freshStart()
    }

    /* Internal */
    // --------------------------------------------------------------------------------------------
    override fun freshStart() {
        val parameters = GetTotalArtists.Parameters(/* memento.genres */null)
        getTotalArtistsUseCase.setParameters(parameters)
        getTotalArtistsUseCase.execute(object : Subscriber<TotalValue>() {
            override fun onCompleted() {
                loadArtists(LIMIT_PER_REQUEST, 0, /* memento.genres */ null)
            }

            override fun onError(e: Throwable) {
                view?.showError()
            }

            override fun onNext(total: TotalValue) {
                Timber.i("Total artists: %s", total.value)
                listMemento.totalItems = total.value
            }
        })
    }

    override fun onRestoreState() {
        listMemento = Memento.fromBundle(savedInstanceState!!)
        val limit = listMemento.currentSize
        listMemento.currentSize = 0
        artistsAdapter.clear()
        loadArtists(limit, 0, /* memento.genres */ null)
    }

    override val listTag: Int
        get() = throw UnsupportedOperationException()

    override fun onLoadMore() {
        loadArtists(LIMIT_PER_REQUEST, listMemento.currentOffset, /* memento.genres */ null)
    }

    @DebugLog
    private fun retryLoadMore() {
        artistsAdapter.onError(false)  // show loading more
        loadArtists(LIMIT_PER_REQUEST, listMemento.currentOffset, /* memento.genres */ null)
    }

    @DebugLog
    private fun loadArtists(genres: List<String>?) {
        loadArtists(-1, 0, genres)
    }

    @DebugLog
    private fun loadArtists(limit: Int = -1, offset: Int = 0, genres: List<String>? = null) {
        // memento.genres = genres;
        if (listMemento.totalItems <= 0) {
            view?.showLoading()
        }
        val parameters = GetArtists.Parameters(limit = limit, offset = offset, genres = genres)
        getArtistsUseCase.setParameters(parameters)
        getArtistsUseCase.execute(object : Subscriber<List<SmallArtist>>() {
            override fun onCompleted() {
                // no-op
            }

            override fun onError(e: Throwable) {
                if (listMemento.currentSize <= 0) {
                    view?.showError()
                } else {
                    artistsAdapter.onError(true)
                }
            }

            override fun onNext(artists: List<SmallArtist>) {
                listMemento.currentSize += artists.size
                val mapper = ArtistListItemMapper()
                val vos = mapper.map(artists)
                artistsAdapter.populate(vos, isThereMore)
                view?.showArtists(vos)
            }
        })
    }

    private fun openArtistDetails(artistId: Long) {
        view?.openArtistDetails(artistId)
    }

    companion object {
        private val LIMIT_PER_REQUEST = 20
        private val LOAD_MORE_THRESHOLD = 1
    }
}

