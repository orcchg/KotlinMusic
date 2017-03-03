package com.orcchg.dev.maxa.ktmusic.app.ui.base.common.screen

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
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

    @BindView(R.id.swipe_refresh_layout) protected lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.empty_view) protected lateinit var emptyView: View
    @BindView(R.id.error_view) protected lateinit var errorView: View
    @BindView(R.id.loading_view) protected lateinit var loadingView: View
    @BindView(R.id.tv_error) protected lateinit var errorTextView: TextView
    @BindView(R.id.tv_empty_data) protected lateinit var emptyDataTextView: TextView
    @BindView(R.id.btn_retry) protected lateinit var errorRetryButton: Button
    @BindView(R.id.btn_empty_data) protected lateinit var emptyDataButton: Button
    @OnClick(R.id.btn_empty_data)
    protected fun onEmptyDataClick() {
        if (isGrid) {
            if (iScrollGrid != null) iScrollGrid!!.onEmptyGrid()
        } else {
            if (iScrollList != null) iScrollList!!.onEmptyList()
        }
    }

    @OnClick(R.id.btn_retry)
    internal fun onRetryClick() {
        if (isGrid) {
            if (iScrollGrid != null) iScrollGrid!!.retryGrid()
        } else {
            if (iScrollList != null) iScrollList!!.retryList()
        }
    }

    protected var iListReach: IListReach? = null
    protected var iScrollGrid: IScrollGrid? = null
    protected var iScrollList: IScrollList? = null
    protected var shadowHolder: ShadowHolder? = null

    protected abstract val isGrid: Boolean
    protected fun autoFit(): Boolean {
        return false
    }

    /* Lifecycle */
    // --------------------------------------------------------------------------------------------
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (IListReach::class.java.isInstance(context)) iListReach = context as IListReach
        if (IScrollGrid::class.java.isInstance(context)) iScrollGrid = context as IScrollGrid
        if (IScrollList::class.java.isInstance(context)) iScrollList = context as IScrollList
        if (ShadowHolder::class.java.isInstance(context)) shadowHolder = context as ShadowHolder
    }

    @DebugLog
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        @LayoutRes val layout = if (autoFit()) R.layout.collection_layout_autofit else R.layout.collection_layout
        val rootView = inflater.inflate(layout, container, false)
        ButterKnife.bind(this, rootView)
        recyclerView = rootView.findViewById(R.id.rv_items) as RecyclerView

        swipeRefreshLayout.setColorSchemeColors(UiUtility.getAttributeColor(activity, R.attr.colorAccent))
        swipeRefreshLayout.setOnRefreshListener {
            if (isGrid) {
                if (iScrollGrid != null) iScrollGrid!!.retryGrid()
            } else {
                if (iScrollList != null) iScrollList!!.retryList()
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
            if (iScrollGrid != null) iScrollGrid!!.onScrollGrid(itemsLeftToEnd)
        } else {
            if (iScrollList != null) iScrollList!!.onScrollList(itemsLeftToEnd)
        }
        if (iListReach != null) iListReach!!.hasReachedBottom(isListReachedBottom)
    }

    override fun onScrollTop() {
        if (iListReach != null) iListReach!!.hasReachedTop(isListReachedTop)
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

        if (shadowHolder != null) shadowHolder!!.showShadow(true)
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

        if (shadowHolder != null) shadowHolder!!.showShadow(true)
    }

    override fun showLoading(tag: Int) {
        swipeRefreshLayout.isRefreshing = false
        recyclerView.visibility = View.GONE
        emptyView.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
        errorView.visibility = View.GONE

        if (shadowHolder != null) shadowHolder!!.showShadow(false)  // don't overlap with progress bar
    }
}
