package com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.viewholder

import android.view.View
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.BaseAdapter

abstract class NormalViewHolder<Model>(view: View) : BaseViewHolder(view) {

    protected lateinit var listener: BaseAdapter.OnItemClickListener<Model>
    protected lateinit var longListener: BaseAdapter.OnItemLongClickListener<Model>

    abstract fun bind(viewObject: Model)
}
