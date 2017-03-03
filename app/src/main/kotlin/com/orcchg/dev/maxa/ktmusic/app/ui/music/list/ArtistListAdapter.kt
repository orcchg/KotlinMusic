package com.orcchg.dev.maxa.ktmusic.app.ui.music.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.orcchg.dev.maxa.ktmusic.R
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.BaseAdapter
import com.orcchg.dev.maxa.ktmusic.app.ui.music.list.viewholder.ArtistViewHolder
import com.orcchg.dev.maxa.ktmusic.app.ui.viewobject.ArtistListItemVO

internal class ArtistListAdapter(private val listener: ItemClickListener)
    : BaseAdapter<ArtistViewHolder, ArtistListItemVO>() {

    override fun createModelViewHolder(parent: ViewGroup): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_musician, parent, false)
        return ArtistViewHolder(view, listener)
    }
}
