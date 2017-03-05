package com.orcchg.dev.maxa.ktmusic.app.ui.base.common.screen

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.bindView
import com.orcchg.dev.maxa.ktmusic.R
import com.orcchg.dev.maxa.ktmusic.app.ui.base.BaseListFragment
import com.orcchg.dev.maxa.ktmusic.app.ui.base.MvpPresenter
import com.orcchg.dev.maxa.ktmusic.app.ui.base.MvpView
import com.orcchg.dev.maxa.ktmusic.app.ui.base.common.content.IListReach
import com.orcchg.dev.maxa.ktmusic.app.ui.base.common.content.IScrollGrid
import com.orcchg.dev.maxa.ktmusic.app.ui.base.common.content.IScrollList
import com.orcchg.dev.maxa.ktmusic.app.ui.base.common.content.ShadowHolder
import com.orcchg.dev.maxa.ktmusic.utility.ui.UiUtility
import hugo.weaving.DebugLog
import timber.log.Timber

abstract class CollectionFragment<in V : MvpView, P : MvpPresenter<V>> : BaseListFragment<V, P>(), LceView {

    protected val swipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.swipe_refresh_layout)
    protected val emptyView:          View               by bindView(R.id.empty_view)
    protected val errorView:          View               by bindView(R.id.error_view)
    protected val loadingView:        View               by bindView(R.id.loading_view)
    protected val emptyDataTextView:  TextView           by bindView(R.id.tv_empty_data)
    protected val errorTextView:      TextView           by bindView(R.id.tv_error)
    protected val emptyDataButton:    Button             by bindView(R.id.btn_empty_data)
    protected val errorRetryButton:   Button             by bindView(R.id.btn_retry)

    protected var iListReach: IListReach? = null
    protected var iScrollGrid: IScrollGrid? = null
    protected var iScrollList: IScrollList? = null
    protected var shadowHolder: ShadowHolder? = null

    protected abstract val isGrid: Boolean

    /* Lifecycle */
    // --------------------------------------------------------------------------------------------
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (IListReach::class.java.isInstance(context))   iListReach   = context as IListReach
        if (IScrollGrid::class.java.isInstance(context))  iScrollGrid  = context as IScrollGrid
        if (IScrollList::class.java.isInstance(context))  iScrollList  = context as IScrollList
        if (ShadowHolder::class.java.isInstance(context)) shadowHolder = context as ShadowHolder
    }

    @DebugLog
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.collection_layout, container, false)
        ButterKnife.bind(this, rootView)
        recyclerView = rootView.findViewById(R.id.rv_items) as RecyclerView

        emptyDataButton.setOnClickListener {
            if (isGrid) {
                iScrollGrid?.onEmptyGrid()
            } else {
                iScrollList?.onEmptyList()
            }
        }

        errorRetryButton.setOnClickListener {
            if (isGrid) {
                iScrollGrid?.retryGrid()
            } else {
                iScrollList?.retryList()
            }
        }

        swipeRefreshLayout.setColorSchemeColors(UiUtility.getAttributeColor(activity, R.attr.colorAccent))
        swipeRefreshLayout.setOnRefreshListener {
            if (isGrid) {
                iScrollGrid?.retryGrid()
            } else {
                iScrollList?.retryList()
            }
        }

        return rootView
    }

    /* View */
    // --------------------------------------------------------------------------------------------
    fun findViewByPosition(position: Int): View {
        return layoutManager.findViewByPosition(position)
    }

    /* Contract */
    // --------------------------------------------------------------------------------------------
    override fun onScroll(itemsLeftToEnd: Int) {
        if (isGrid) {
            iScrollGrid?.onScrollGrid(itemsLeftToEnd)
        } else {
            iScrollList?.onScrollList(itemsLeftToEnd)
        }
        iListReach?.hasReachedBottom(isListReachedBottom)
    }

    override fun onScrollTop() {
        iListReach?.hasReachedTop(isListReachedTop)
    }

    override fun isContentViewVisible(tag: Int): Boolean {
        return UiUtility.isVisible(recyclerView)
    }

    override fun showContent(tag: Int, isEmpty: Boolean) {
        if (!isEmpty && UiUtility.isVisible(recyclerView)) {
            Timber.v("List items are already visible")
            return
        }

        swipeRefreshLayout.isRefreshing = false
        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE

        if (isEmpty) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            emptyView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        shadowHolder?.showShadow(true)
    }

    override fun showEmptyList(tag: Int) {
        showContent(tag, true)
    }

    override fun showError(tag: Int) {
        swipeRefreshLayout.isRefreshing = false
        recyclerView.visibility = View.GONE
        emptyView.visibility = View.GONE
        loadingView.visibility = View.GONE
        errorView.visibility = View.VISIBLE

        shadowHolder?.showShadow(true)
    }

    override fun showLoading(tag: Int) {
        swipeRefreshLayout.isRefreshing = false
        recyclerView.visibility = View.GONE
        emptyView.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
        errorView.visibility = View.GONE

        shadowHolder?.showShadow(false)  // don't overlap with progress bar
    }
}
