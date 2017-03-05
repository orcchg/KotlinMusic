package com.orcchg.dev.maxa.ktmusic.app.ui.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.orcchg.dev.maxa.ktmusic.app.MusicApplication
import com.orcchg.dev.maxa.ktmusic.app.injection.application.ApplicationComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.navigation.DaggerNavigationComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.navigation.NavigationComponent
import com.orcchg.dev.maxa.ktmusic.app.navigation.NavigatorHolder
import hugo.weaving.DebugLog
import timber.log.Timber

abstract class BaseDialogFragment<in V : MvpView, P : MvpPresenter<V>> : DialogFragment(), MvpView {

    protected lateinit var presenter: P
    protected lateinit var navigationComponent: NavigationComponent

    private val navigatorHolder = NavigatorHolder()

    private var isStateRestored = false

    protected abstract fun createPresenter(): P

    protected abstract fun injectDependencies()

    @DebugLog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(javaClass.simpleName)
        Timber.i("onCreate(dialog fragment=%s)", hashCode())
        isStateRestored = savedInstanceState != null
        injectNavigator()
        injectDependencies()
        presenter = createPresenter()
        presenter.attachView(this as V)
        presenter.onCreate(savedInstanceState)
    }

    @DebugLog
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.tag(javaClass.simpleName)
        Timber.i("onActivityResult(dialog fragment=%s)", hashCode())
        presenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(javaClass.simpleName)
        Timber.i("onStart(dialog fragment=%s)", hashCode())
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(javaClass.simpleName)
        Timber.i("onResume(dialog fragment=%s)", hashCode())
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        Timber.tag(javaClass.simpleName)
        Timber.i("onPause(dialog fragment=%s)", hashCode())
        presenter.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.tag(javaClass.simpleName)
        Timber.i("onSaveInstanceState(dialog fragment=%s)", hashCode())
        presenter.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        Timber.tag(javaClass.simpleName)
        Timber.i("onStop(dialog fragment=%s)", hashCode())
        presenter.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(javaClass.simpleName)
        Timber.i("onDestroy(dialog fragment=%s)", hashCode())
        presenter.onDestroy()
        presenter.detachView()
    }

    /* Component */
    // --------------------------------------------------------------------------------------------
    protected val applicationComponent: ApplicationComponent
        get() = (activity.application as MusicApplication).applicationComponent

    /* Internal */
    // --------------------------------------------------------------------------------------------
    private fun injectNavigator() {
        navigationComponent = DaggerNavigationComponent.create()
        navigationComponent.inject(navigatorHolder)
    }
}
