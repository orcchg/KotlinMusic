package com.orcchg.dev.maxa.ktmusic.app.ui.music.list.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.orcchg.dev.maxa.ktmusic.R
import com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.viewholder.NormalViewHolder
import com.orcchg.dev.maxa.ktmusic.app.ui.music.list.ItemClickListener
import com.orcchg.dev.maxa.ktmusic.app.ui.viewobject.ArtistListItemVO

class ArtistViewHolder(view: View, val artistClickListener: ItemClickListener) : NormalViewHolder<ArtistListItemVO>(view) {

    @BindView(R.id.fl_grid_item) lateinit var gridItemView: View  // only on large screens
    @BindView(R.id.pb_loading) lateinit var progressBar: ProgressBar
    @BindView(R.id.iv_cover) lateinit var iconView: ImageView
    @BindView(R.id.tv_musician_title) lateinit var titleView: TextView

    init {
        ButterKnife.bind(this, view)
    }

    override fun bind(viewObject: ArtistListItemVO) {
        itemView.setOnClickListener({ view -> artistClickListener.onItemClick(iconView as View, viewObject.id) })
        gridItemView?.setOnClickListener { view -> artistClickListener.onItemClick(iconView as View, viewObject.id) }

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
