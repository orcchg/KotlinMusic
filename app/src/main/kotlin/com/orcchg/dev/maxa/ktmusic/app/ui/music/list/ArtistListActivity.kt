package com.orcchg.dev.maxa.ktmusic.app.ui.music.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import butterknife.ButterKnife
import butterknife.bindView
import com.orcchg.dev.maxa.ktmusic.R
import com.orcchg.dev.maxa.ktmusic.app.ui.base.BaseActivity
import com.orcchg.dev.maxa.ktmusic.app.ui.base.common.view.misc.GridItemDecorator
import com.orcchg.dev.maxa.ktmusic.app.ui.base.common.view.misc.ViewUtility
import com.orcchg.dev.maxa.ktmusic.app.ui.music.list.injection.ArtistListComponent
import com.orcchg.dev.maxa.ktmusic.app.ui.music.list.injection.DaggerArtistListComponent
import com.orcchg.dev.maxa.ktmusic.app.ui.viewobject.ArtistListItemVO
import hugo.weaving.DebugLog
import timber.log.Timber

class ArtistListActivity : BaseActivity<ArtistListContract.View, ArtistListContract.Preseneter>(), ArtistListContract.View {

    val toolbar:            Toolbar            by bindView(R.id.toolbar)
    val dropshadowView:     View               by bindView(R.id.rl_toolbar_dropshadow)
    val swipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.swipe_refresh_layout)
    val listView:           RecyclerView       by bindView(R.id.rv_items)
    val emptyView:          View               by bindView(R.id.empty_view)
    val loadingView:        View               by bindView(R.id.loading_view)
    val errorView:          View               by bindView(R.id.error_view)
    val retryButton:        Button             by bindView(R.id.btn_retry)

    private lateinit var component: ArtistListComponent
    private lateinit var layoutManager: LinearLayoutManager

    private var lastVisible = -1

    companion object {
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, ArtistListActivity::class.java)
        }
    }

    override fun createPresenter(): ArtistListContract.Preseneter {
        return component.presenter()
    }

    override fun injectDependencies() {
        // ArtistRepositoryComponent artistRepositoryComponent = DaggerArtistRepositoryComponent.create();
        component = DaggerArtistListComponent.builder()
            .applicationComponent(applicationComponent)
            // .artistRepositoryComponent(artistRepositoryComponent)
            .build()
    }

    // --------------------------------------------------------------------------------------------
    class Memento {
        var layoutManagerState: Parcelable? = null

        internal fun toBundle(outState: Bundle) {
            outState.putParcelable(BUNDLE_KEY_LM_STATE, layoutManagerState)
        }

        companion object {
            private val BUNDLE_KEY_LM_STATE = "bundle_key_lm_state"

            internal fun fromBundle(savedInstanceState: Bundle): Memento {
                val memento = Memento()
                memento.layoutManagerState = savedInstanceState.getParcelable<Parcelable>(BUNDLE_KEY_LM_STATE)
                return memento
            }
        }
    }

    internal var memento = Memento()

    /* Lifecycle */
    // --------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_list)
        ButterKnife.bind(this)
        initView()
        initToolbar()

        if (savedInstanceState != null) {
            memento = Memento.fromBundle(savedInstanceState)
        }
    }

    override fun onStart() {
        super.onStart()
        if (isStateRestored && memento.layoutManagerState != null) {
            Timber.i("Restored state of layout manager")
            layoutManager.onRestoreInstanceState(memento.layoutManagerState)
        }
        listView.layoutManager = layoutManager
    }

    @DebugLog
    override fun onSaveInstanceState(outState: Bundle) {
        memento.layoutManagerState = layoutManager.onSaveInstanceState()
        memento.toBundle(outState)
        super.onSaveInstanceState(outState)
    }

    /* View */
    // --------------------------------------------------------------------------------------------
    private fun initView() {
        retryButton.setOnClickListener { presenter.retry() }
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorAccent))
        swipeRefreshLayout.setOnRefreshListener { presenter.retry() }

        if (ViewUtility.isLargeScreen(this)) {
            layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.grid_span))
            listView.addItemDecoration(GridItemDecorator(this, R.dimen.grid_card_spacing))
        } else {
            layoutManager = LinearLayoutManager(this)
        }

        listView.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                processListScroll(recyclerView, dx, dy)
            }
        })
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.str_musicians_list)
        toolbar.setNavigationOnClickListener { view -> finish() }
    }

    /* Contract */
    // --------------------------------------------------------------------------------------------
    override fun openArtistDetails(artistId: Long) {
        navigationComponent.navigator().openDetailsScreen(this, artistId)
    }

    override fun getListView(tag: Int): RecyclerView {
        return listView
    }

    override fun showArtists(artists: List<ArtistListItemVO>) {
        swipeRefreshLayout.isRefreshing = false
        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE

        if (artists.isEmpty()) {
            listView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            emptyView.visibility = View.GONE
            listView.visibility = View.VISIBLE
        }
        showShadow(true)
    }

    override fun showError() {
        swipeRefreshLayout.isRefreshing = false
        listView.visibility = View.GONE
        emptyView.visibility = View.GONE
        loadingView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
        showShadow(true)
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = false
        listView.visibility = View.GONE
        emptyView.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
        showShadow(false)  // don't overlap with progress bar
    }

    /* Internal */
    // --------------------------------------------------------------------------------------------
    internal fun processListScroll(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        if (dy <= 0) {
            return   // skip scroll up
        }

        val last = layoutManager.findLastVisibleItemPosition()
        if (lastVisible == last) {
            return   // skip scroll due to layout
        }

        lastVisible = last
        val total = layoutManager.itemCount
        presenter.onScroll(total - last)
    }

    fun showShadow(show: Boolean) {
        dropshadowView.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }
}
