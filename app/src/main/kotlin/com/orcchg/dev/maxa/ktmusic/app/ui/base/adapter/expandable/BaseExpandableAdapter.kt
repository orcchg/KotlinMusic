package com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.expandable

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.expandable.base.BaseChildItem
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.expandable.base.BaseChildViewHolder
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.expandable.base.BaseParentItem
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.expandable.base.BaseParentViewHolder

abstract class BaseExpandableAdapter<P : BaseParentItem<C>, C : BaseChildItem,
                                     PVH : BaseParentViewHolder<P, C>,
                                     CVH : BaseChildViewHolder<C>>(parentItems: List<P>)
    : ExpandableRecyclerAdapter<P, C, PVH, CVH>(parentItems)
