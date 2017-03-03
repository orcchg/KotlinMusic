package com.orcchg.dev.maxa.ktmusic.app.ui.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.orcchg.dev.maxa.ktmusic.app.MusicApplication
import com.orcchg.dev.maxa.ktmusic.app.injection.application.ApplicationComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.navigation.DaggerNavigationComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.navigation.NavigationComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.shared_prefs.DaggerSharedPrefsManagerComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.shared_prefs.SharedPrefsManagerComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.shared_prefs.SharedPrefsManagerModule
import com.orcchg.dev.maxa.ktmusic.app.navigation.NavigatorHolder
import hugo.weaving.DebugLog
import timber.log.Timber

abstract class BaseFragment<in V : MvpView, P : MvpPresenter<V>> : Fragment(), MvpView {

    protected lateinit var presenter: P
    protected lateinit var navigationComponent: NavigationComponent
    protected lateinit var sharedPrefsManagerComponent: SharedPrefsManagerComponent

    private val navigatorHolder = NavigatorHolder()

    private var isStateRestored = false

    protected abstract fun createPresenter(): P

    protected abstract fun injectDependencies()

    @DebugLog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(javaClass.simpleName)
        Timber.i("onCreate(fragment=%s)", hashCode())
        isStateRestored = savedInstanceState != null
        injectNavigator()
        injectSharedPrefsManager()
        injectDependencies()
        presenter = createPresenter()
        presenter.attachView(this as V)
        presenter.onCreate(savedInstanceState)
    }

    @DebugLog
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.tag(javaClass.simpleName)
        Timber.i("onActivityResult(fragment=%s)", hashCode())
        presenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(javaClass.simpleName)
        Timber.i("onStart(fragment=%s)", hashCode())
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(javaClass.simpleName)
        Timber.i("onResume(fragment=%s)", hashCode())
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        Timber.tag(javaClass.simpleName)
        Timber.i("onPause(fragment=%s)", hashCode())
        presenter.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.tag(javaClass.simpleName)
        Timber.i("onSaveInstanceState(fragment=%s)", hashCode())
        presenter.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        Timber.tag(javaClass.simpleName)
        Timber.i("onStop(fragment=%s)", hashCode())
        presenter.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(javaClass.simpleName)
        Timber.i("onDestroy(fragment=%s)", hashCode())
        presenter.onDestroy()
        presenter.detachView()
    }

    /* Component */
    // --------------------------------------------------------------------------------------------
    internal val applicationComponent: ApplicationComponent
        get() = (activity.application as MusicApplication).applicationComponent

    /* Internal */
    // --------------------------------------------------------------------------------------------
    @DebugLog
    protected fun isStateRestored(): Boolean {
        return isStateRestored
    }

    private fun injectNavigator() {
        navigationComponent = DaggerNavigationComponent.create()
        navigationComponent.inject(navigatorHolder)
    }

    private fun injectSharedPrefsManager() {
        sharedPrefsManagerComponent = DaggerSharedPrefsManagerComponent.builder()
                .sharedPrefsManagerModule(SharedPrefsManagerModule(activity))
                .build()
    }
}
