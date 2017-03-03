package com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.expandable.base

import com.bignerdranch.expandablerecyclerview.model.Parent

abstract class BaseParentItem<C : BaseChildItem> : Parent<C> {

    override fun isInitiallyExpanded(): Boolean {
        return false
    }
}
