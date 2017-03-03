package com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.expandable.base.stub

import android.view.View
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.expandable.base.BaseChildViewHolder

class SimpleBaseChildViewHolder(itemView: View) : BaseChildViewHolder<ChildItemStub>(itemView) {

    override fun bind(model: ChildItemStub) {
        // override in subclasses
    }
}
