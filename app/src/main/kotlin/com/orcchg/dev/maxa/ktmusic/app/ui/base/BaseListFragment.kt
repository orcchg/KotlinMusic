package com.orcchg.dev.maxa.ktmusic.app.ui.base

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import hugo.weaving.DebugLog
import timber.log.Timber

abstract class BaseListFragment<in V : MvpView, P : MvpPresenter<V>> : BaseFragment<V, P>(), MvpListView {

    protected lateinit var recyclerView: RecyclerView
    protected lateinit var layoutManager: LinearLayoutManager

    private var lastVisible = -1

    protected class Memento {
        var layoutManagerState: Parcelable? = null

        fun toBundle(outState: Bundle) {
            outState.putParcelable(BUNDLE_KEY_LM_STATE, layoutManagerState)
        }

        companion object {
            protected val BUNDLE_KEY_LM_STATE = "bundle_key_lm_state"

            fun fromBundle(savedInstanceState: Bundle): Memento {
                val memento = Memento()
                memento.layoutManagerState = savedInstanceState.getParcelable<Parcelable>(BUNDLE_KEY_LM_STATE)
                return memento
            }
        }
    }

    protected lateinit var memento: Memento

    protected fun createMemento(): Memento {
        return Memento()
    }

    protected fun restoreMemento(savedInstanceState: Bundle): Memento {
        return Memento.fromBundle(savedInstanceState)
    }

    @DebugLog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            memento = restoreMemento(savedInstanceState)
        } else {
            memento = createMemento()
        }
        layoutManager = createLayoutManager()
    }

    override fun onStart() {
        super.onStart()
        if (isStateRestored() && memento.layoutManagerState != null) {
            Timber.tag(javaClass.simpleName)
            Timber.i("Restored state of layout manager")
            layoutManager.onRestoreInstanceState(memento.layoutManagerState)
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                processListScroll(recyclerView, dx, dy)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        memento.layoutManagerState = layoutManager.onSaveInstanceState()
        memento.toBundle(outState)
        super.onSaveInstanceState(outState)
    }

    /* List helpers */
    // --------------------------------------------------------------------------------------------
    @DebugLog
    override fun getListView(tag: Int): RecyclerView {
        return recyclerView
    }

    // {@see http://stackoverflow.com/questions/27841740/how-to-know-whether-a-recyclerview-linearlayoutmanager-is-scrolled-to-top-or-b/33515549#33515549}
    // ------------------------------------------
    protected val isListReachedTop: Boolean
        get() {
            val position = layoutManager.findFirstVisibleItemPosition()
            return position == 0 && layoutManager.findViewByPosition(position).top == 0
        }

    protected val isListReachedBottom: Boolean
        get() {
            val adapter = recyclerView!!.adapter
            if (adapter != null) {
                val totalItems = adapter.itemCount
                return layoutManager.findLastVisibleItemPosition() == totalItems - 1
            }
            Timber.tag(javaClass.simpleName)
            Timber.w("Adapter must be supplied for the list to check whether bottom has been reached properly !")
            return false
        }

    /* Internal */
    // --------------------------------------------------------------------------------------------
    protected abstract fun createLayoutManager(): LinearLayoutManager

    internal fun processListScroll(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy <= 0) {
            onScrollTop()
            return   // skip scroll up
        }

        val last = layoutManager.findLastVisibleItemPosition()
        if (lastVisible == last) {
            return   // skip scroll due to layout
        }

        lastVisible = last
        val total = layoutManager.itemCount
        onScroll(total - last)
    }

    protected abstract fun onScroll(itemsLeftToEnd: Int)
    protected abstract fun onScrollTop()
}
