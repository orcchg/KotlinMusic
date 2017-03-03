package com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.expandable.base

import android.view.View
import com.bignerdranch.expandablerecyclerview.ParentViewHolder

abstract class BaseParentViewHolder<P : BaseParentItem<C>, C : BaseChildItem>(itemView: View) : ParentViewHolder<P, C>(itemView) {

    abstract fun bind(model: P)
}
