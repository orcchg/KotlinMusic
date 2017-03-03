package com.orcchg.dev.maxa.ktmusic.app.ui.base

import android.os.Bundle
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.BaseAdapter
import com.orcchg.dev.maxa.ktmusic.app.ui.base.common.screen.ListPresenter
import hugo.weaving.DebugLog
import timber.log.Timber

abstract class BaseListPresenter<V : MvpListView> : BasePresenter<V>(), ListPresenter {

    protected var listAdapter: BaseAdapter<*, *>? = null

    protected abstract fun createListAdapter(): BaseAdapter<*, *>
    protected abstract val listTag: Int
    protected abstract fun onLoadMore()

    protected class Memento {
        var currentSize = 0
        var currentOffset = 0
        var totalItems = 0

        fun toBundle(outState: Bundle) {
            outState.putInt(BUNDLE_KEY_CURRENT_SIZE, currentSize)
            outState.putInt(BUNDLE_KEY_CURRENT_OFFSET, currentOffset)
            outState.putInt(BUNDLE_KEY_TOTAL_ITEMS, totalItems)
        }

        companion object {
            private val BUNDLE_KEY_CURRENT_SIZE = "bundle_key_current_size"
            private val BUNDLE_KEY_CURRENT_OFFSET = "bundle_key_current_offset"
            private val BUNDLE_KEY_TOTAL_ITEMS = "bundle_key_total_items"

            fun fromBundle(savedInstanceState: Bundle): Memento {
                val memento = Memento()
                memento.currentSize = savedInstanceState.getInt(BUNDLE_KEY_CURRENT_SIZE)
                memento.currentOffset = savedInstanceState.getInt(BUNDLE_KEY_CURRENT_OFFSET)
                memento.totalItems = savedInstanceState.getInt(BUNDLE_KEY_TOTAL_ITEMS)
                return memento
            }
        }
    }

    protected lateinit var listMemento: Memento

    protected fun createMemento(): Memento {
        return Memento()
    }

    protected fun restoreMemento(savedInstanceState: Bundle): Memento {
        return Memento.fromBundle(savedInstanceState)
    }

    /* Lifecycle */
    // --------------------------------------------------------------------------------------------
    @DebugLog
    override fun onCreate(savedInstanceState: Bundle?) {
        if (listAdapter == null) {
            val message = "Concrete method createListAdapter() must be called from subclass Ctor first!"
            Timber.e(message)
            throw IllegalStateException(message)
        }
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            listMemento = restoreMemento(savedInstanceState)
        } else {
            listMemento = createMemento()
        }
    }

    override fun onStart() {
        super.onStart()
        if (isViewAttached) {
            val list = view?.getListView(listTag)
            if (list?.adapter == null) {
                list?.adapter = listAdapter
            }
        } else {
            Timber.w("No View is attached")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        listMemento.toBundle(outState)
        super.onSaveInstanceState(outState)
    }

    /* Contract */
    // --------------------------------------------------------------------------------------------
    override fun onScroll(itemsLeftToEnd: Int) {
        if (isThereMore && itemsLeftToEnd <= 20) {
            listMemento.currentOffset += 20
            onLoadMore()
        }
    }

    /* Internal */
    // --------------------------------------------------------------------------------------------
    protected fun dropListStat() {
        listMemento.currentSize = 0
        listMemento.currentOffset = 0
        listMemento.totalItems = 0
    }

    protected val isThereMore: Boolean
        @DebugLog
        get() = listMemento.totalItems > listMemento.currentSize + listMemento.currentOffset
}
