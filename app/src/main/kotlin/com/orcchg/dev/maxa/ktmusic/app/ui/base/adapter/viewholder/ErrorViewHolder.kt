package com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.viewholder

import android.view.View
import android.widget.Button
import butterknife.ButterKnife
import butterknife.bindView
import com.orcchg.dev.maxa.ktmusic.R

class ErrorViewHolder(view: View, private val listener: View.OnClickListener?) : BaseViewHolder(view) {

    val retryButton: Button by bindView(R.id.btn_retry)

    init {
        ButterKnife.bind(this, view)
        retryButton.setOnClickListener { listener?.onClick(retryButton) }
    }
}
