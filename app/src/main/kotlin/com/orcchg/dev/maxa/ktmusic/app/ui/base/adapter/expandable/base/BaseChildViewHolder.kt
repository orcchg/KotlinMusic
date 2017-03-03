package com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.expandable.base

import android.view.View
import com.bignerdranch.expandablerecyclerview.ChildViewHolder

abstract class BaseChildViewHolder<C : BaseChildItem>(itemView: View) : ChildViewHolder<C>(itemView) {

    abstract fun bind(model: C)
}
