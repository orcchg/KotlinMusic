package com.orcchg.dev.maxa.ktmusic.app.ui.base.common.view.misc

import android.content.Context
import com.orcchg.dev.maxa.ktmusic.R

object ViewUtility {

    fun isLargeScreen(context: Context): Boolean {
        return context.resources.getBoolean(R.bool.isLargeScreen)
    }
}
