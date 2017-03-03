package com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.expandable.base.stub

import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.expandable.base.BaseParentItem
import java.util.*

class ParentItemStub : BaseParentItem<ChildItemStub>() {

    private val childList: MutableList<ChildItemStub> = ArrayList()  // empty list

    override fun getChildList(): MutableList<ChildItemStub> {
        return childList
    }

    override fun isInitiallyExpanded(): Boolean {
        return false
    }
}
