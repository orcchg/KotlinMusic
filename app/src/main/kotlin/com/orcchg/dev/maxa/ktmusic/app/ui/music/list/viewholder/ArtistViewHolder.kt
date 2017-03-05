package com.orcchg.dev.maxa.ktmusic.app.ui.music.list.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.bindOptionalView
import butterknife.bindView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.orcchg.dev.maxa.ktmusic.R
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.viewholder.NormalViewHolder
import com.orcchg.dev.maxa.ktmusic.app.ui.music.list.ItemClickListener
import com.orcchg.dev.maxa.ktmusic.app.ui.viewobject.ArtistListItemVO

class ArtistViewHolder(view: View, val artistClickListener: ItemClickListener) : NormalViewHolder<ArtistListItemVO>(view) {

    val gridItemView: View? by bindOptionalView(R.id.fl_grid_item)  // only on large screens
    val progressBar: ProgressBar by bindView(R.id.pb_loading)
    val iconView: ImageView by bindView(R.id.iv_cover)
    val titleView: TextView by bindView(R.id.tv_musician_title)

    init {
        ButterKnife.bind(this, view)
    }

    override fun bind(viewObject: ArtistListItemVO) {
        itemView.setOnClickListener({ view -> artistClickListener.onItemClick(view, viewObject.id) })
        gridItemView?.setOnClickListener { view -> artistClickListener.onItemClick(view, viewObject.id) }

        titleView.text = viewObject.name

        Glide.with(itemView.context)
            .load(viewObject.coverSmall)
            .listener(object : RequestListener<String, GlideDrawable> {
                override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(iconView)
    }
}
