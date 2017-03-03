package com.orcchg.dev.maxa.ktmusic.utility.android

import android.app.Activity
import android.os.Build

class ContextUtility {

    fun isActivityDestroyed(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity.isDestroyed
        }
//        if (BaseActivity::class.java!!.isInstance(activity)) {
//            return (activity as BaseActivity).isDestroying()
//        }
        return activity.isFinishing
    }
}
