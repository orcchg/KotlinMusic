package com.orcchg.dev.maxa.ktmusic.app.ui.base.adapter.viewholder

import android.view.View
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.orcchg.dev.maxa.ktmusic.R

class ErrorViewHolder(view: View, private val listener: View.OnClickListener?) : BaseViewHolder(view) {

    @BindView(R.id.btn_retry) lateinit var retryButton: Button
    @OnClick(R.id.btn_retry)
    fun onRetryClick() {
        listener?.onClick(retryButton)
    }

    init {
        ButterKnife.bind(this, view)
    }
}
