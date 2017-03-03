package com.orcchg.dev.maxa.ktmusic.app.ui.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.orcchg.dev.maxa.ktmusic.app.MusicApplication
import com.orcchg.dev.maxa.ktmusic.app.injection.application.DaggerApplicationComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.application.ApplicationComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.navigation.DaggerNavigationComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.navigation.NavigationComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.permission.DaggerPermissionManagerComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.permission.PermissionManagerComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.permission.PermissionManagerModule
import com.orcchg.dev.maxa.ktmusic.app.injection.shared_prefs.DaggerSharedPrefsManagerComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.shared_prefs.SharedPrefsManagerComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.shared_prefs.SharedPrefsManagerModule
import com.orcchg.dev.maxa.ktmusic.app.navigation.NavigatorHolder
import com.orcchg.dev.maxa.ktmusic.utility.DebugSake
import com.orcchg.dev.maxa.ktmusic.utility.android.MainLooperSpy
import com.orcchg.dev.maxa.ktmusic.utility.ui.UiUtility
import hugo.weaving.DebugLog
import timber.log.Timber

abstract class BaseActivity<in V : MvpView, P : MvpPresenter<V>> : AppCompatActivity(), MvpView {

    @DebugSake protected val mainLooperSpy = MainLooperSpy()

    protected lateinit var presenter: P
    protected lateinit var navigationComponent: NavigationComponent
    protected lateinit var permissionManagerComponent: PermissionManagerComponent
    protected lateinit var sharedPrefsManagerComponent: SharedPrefsManagerComponent

    private val navigatorHolder = NavigatorHolder()

    protected var isStateRestored = false
    protected var isDestroying = false

    protected abstract fun createPresenter(): P

    protected abstract fun injectDependencies()

    @DebugLog
    override fun onCreate(savedInstanceState: Bundle?) {
        isStateRestored = savedInstanceState != null
        isDestroying = false
        super.onCreate(savedInstanceState)
        Timber.tag(javaClass.simpleName)
        Timber.i("onCreate(activity=%s), smallest width: %s", hashCode(), UiUtility.getSmallestWidth(this))
        injectNavigator()
        injectPermissionManager()
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
        Timber.i("onActivityResult(activity=%s)", hashCode())
        presenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(javaClass.simpleName)
        Timber.i("onStart(activity=%s)", hashCode())
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(javaClass.simpleName)
        Timber.i("onResume(activity=%s)", hashCode())
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        Timber.tag(javaClass.simpleName)
        Timber.i("onPause(activity=%s)", hashCode())
        presenter.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.tag(javaClass.simpleName)
        Timber.i("onSaveInstanceState(activity=%s)", hashCode())
        presenter.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        Timber.tag(javaClass.simpleName)
        Timber.i("onStop(activity=%s)", hashCode())
        presenter.onStop()
    }

    override fun onDestroy() {
        isDestroying = true
        super.onDestroy()
        Timber.tag(javaClass.simpleName)
        Timber.i("onDestroy(activity=%s)", hashCode())
        presenter.onDestroy()
        presenter.detachView()
    }

    /* Component */
    // --------------------------------------------------------------------------------------------
    internal val applicationComponent: ApplicationComponent
        get() = (application as MusicApplication).applicationComponent

    /* Internal */
    // --------------------------------------------------------------------------------------------
    private fun injectPermissionManager() {
        permissionManagerComponent = DaggerPermissionManagerComponent.builder()
                .permissionManagerModule(PermissionManagerModule(applicationContext))
                .build()
    }

    private fun injectNavigator() {
        navigationComponent = DaggerNavigationComponent.create()
        navigationComponent.inject(navigatorHolder)
    }

    private fun injectSharedPrefsManager() {
        sharedPrefsManagerComponent = DaggerSharedPrefsManagerComponent.builder()
                .sharedPrefsManagerModule(SharedPrefsManagerModule(this))
                .build()
    }
}
