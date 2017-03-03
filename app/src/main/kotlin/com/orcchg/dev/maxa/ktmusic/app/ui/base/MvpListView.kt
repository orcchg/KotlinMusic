package com.orcchg.dev.maxa.ktmusic.app.ui.base

import android.support.v7.widget.RecyclerView

interface MvpListView : MvpView {
    fun getListView(tag: Int): RecyclerView
}
