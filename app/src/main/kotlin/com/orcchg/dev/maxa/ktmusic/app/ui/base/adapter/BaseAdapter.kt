package com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orcchg.dev.maxa.ktmusic.R
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.viewholder.BaseViewHolder
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.viewholder.ErrorViewHolder
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.viewholder.LoadingViewHolder
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.viewholder.NormalViewHolder
import java.util.*

abstract class BaseAdapter<out ModelViewHolder : NormalViewHolder<Model>, Model> : RecyclerView.Adapter<BaseViewHolder>() {

    interface OnItemClickListener<in Model> {
        fun onItemClick(view: View, model: Model, position: Int)
    }

    interface OnItemLongClickListener<in Model> {
        fun onItemLongClick(view: View, model: Model, position: Int)
    }

    companion object {
        protected val VIEW_TYPE_NORMAL = 0
        protected val VIEW_TYPE_LOADING = 1
        protected val VIEW_TYPE_ERROR = 2
    }

    protected val models = ArrayList<Model>()
    protected var isThereMore = false
    protected var isInError = false

    var onItemClickListener: OnItemClickListener<Model>? = null
    var onItemLongClickListener: OnItemLongClickListener<Model>? = null
    var onErrorClickListener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder? {
        when (viewType) {
            VIEW_TYPE_NORMAL -> return createModelViewHolder(parent)
            VIEW_TYPE_LOADING -> return createLoadingViewHolder(parent)
            VIEW_TYPE_ERROR -> return createErrorViewHolder(parent)
            else -> return null
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val type = getItemViewType(position)
        when (type) {
            VIEW_TYPE_NORMAL -> (holder as ModelViewHolder).bind(getItemAtPosition(position))
        }
    }

    protected fun getItemAtPosition(position: Int): Model {
        return models[position]
    }

    override fun getItemCount(): Int {
        return if (models.isEmpty()) 0 else models.size + if (isThereMore) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        val isLoading = isThereMore && position == itemCount - 1
        return if (isLoading) if (isInError) VIEW_TYPE_ERROR else VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
    }

    /* Error state */
    // --------------------------------------------------------------------------------------------
    fun onError(isInError: Boolean) {
        if (!isThereMore) return
        this.isInError = isInError
        notifyItemChanged(itemCount - 1)
    }

    /* Data access */
    // --------------------------------------------------------------------------------------------
    fun add(item: Model?) {
        if (item != null) {
            models.add(item)
            notifyItemInserted(models.size)
        }
    }

    fun addInverse(item: Model?) {
        if (item != null) {
            models.add(0, item)  // shifting insertion
            notifyItemInserted(0)
        }
    }

    fun populate(items: List<Model>?, isThereMore: Boolean) {
        isInError = false
        if (items != null && !items.isEmpty()) {
            this.models.addAll(items)
            this.isThereMore = isThereMore
            notifyDataSetChanged()
        }
    }

    fun populateInverse(items: List<Model>, isThereMore: Boolean) {
        Collections.reverse(items)
        populate(items, isThereMore)
    }

    fun clear() {
        isInError = false
        if (!models.isEmpty()) {
            models.clear()
            notifyDataSetChanged()
        }
    }

    /**
     * This method silently clears internal list of items. The following scenario is quite possible:

     * onActivityResult() finishes before onStart() and issues a request for data in repository,
     * which could also had finished before onStart() will happen. On low-memory old devices such
     * onActivityResult() could always follows complete destruction-creation cycle and then in onStart()
     * a stored state of some Presenter could be restored, populating Adapter with some preserved data.
     * But there is data already in Adapter, so it will be duplicated. To avoid this - such silent
     * cleaning should be made in such scenario.

     * This method should not be called in load-more scenario.
     */
    fun clearSilent() {
        isInError = false
        models.clear()
    }

    fun remove(position: Int) {
        if (models.size > position && position >= 0) {
            models.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    /* Customization */
    // --------------------------------------------------------------------------------------------
    protected fun createLoadingViewHolder(parent: ViewGroup): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_loading, parent, false)
        return LoadingViewHolder(view)
    }

    protected fun createErrorViewHolder(parent: ViewGroup): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_error, parent, false)
        return ErrorViewHolder(view, onErrorClickListener)
    }

    protected abstract fun createModelViewHolder(parent: ViewGroup): ModelViewHolder
}
