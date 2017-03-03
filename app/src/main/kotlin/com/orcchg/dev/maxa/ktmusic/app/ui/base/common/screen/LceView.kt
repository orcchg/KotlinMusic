package com.orcchg.dev.maxa.ktmusic.app.ui.base.common.screen

interface LceView {
    fun isContentViewVisible(tag: Int): Boolean
    fun showContent(tag: Int, isEmpty: Boolean)
    fun showEmptyList(tag: Int)
    fun showError(tag: Int)
    fun showLoading(tag: Int)
}
