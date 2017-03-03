package com.orcchg.dev.maxa.ktmusic.app.ui.base.common.content

interface IScrollList {
    fun retryList()
    fun onEmptyList()
    fun onScrollList(itemsLeftToEnd: Int)
}
