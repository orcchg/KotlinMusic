package com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.expandable.base.stub

import android.view.View
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.expandable.base.BaseParentViewHolder

class SimpleBaseParentViewHolder(itemView: View) : BaseParentViewHolder<ParentItemStub, ChildItemStub>(itemView) {

    override fun bind(model: ParentItemStub) {
        // override in subclasses
    }
}
