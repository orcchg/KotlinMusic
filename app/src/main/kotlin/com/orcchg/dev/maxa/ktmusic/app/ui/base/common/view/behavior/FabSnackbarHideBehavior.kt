package com.orcchg.dev.maxa.ktmusic.app.ui.base.common.view.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.View

class FabSnackbarHideBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is Snackbar.SnackbarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        val scaleFactor = dependency.translationY / dependency.height
        child.scaleX = scaleFactor
        child.scaleY = scaleFactor
        return false
    }
}
