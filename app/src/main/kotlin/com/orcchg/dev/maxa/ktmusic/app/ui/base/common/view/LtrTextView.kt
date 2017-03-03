package com.orcchg.dev.maxa.ktmusic.app.ui.base.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class LtrTextView constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
    : TextView(context, attrs, defStyleAttr) {

    override fun onRtlPropertiesChanged(layoutDirection: Int) {
        // override
    }
}
