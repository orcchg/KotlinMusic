package com.orcchg.dev.maxa.ktmusic.app.ui.base.common.content

/**
 * Scrolling events to top and to bottom and the ability to detect
 * whether the topmost or bottommost positions were reached.
 */
interface IListReach {
    fun hasReachedTop(reached: Boolean)
    fun hasReachedBottom(reached: Boolean)
}
