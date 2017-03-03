package com.orcchg.dev.maxa.ktmusic.app.ui.base

import android.content.Intent
import android.os.Bundle
import com.orcchg.dev.maxa.ktmusic.app.injection.application.ApplicationComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.shared_prefs.DaggerSharedPrefsManagerComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.shared_prefs.SharedPrefsManagerComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.shared_prefs.SharedPrefsManagerModule
import hugo.weaving.DebugLog
import timber.log.Timber
import java.lang.ref.WeakReference

abstract class BasePresenter<V : MvpView> : MvpPresenter<V> {

    private var viewRef: WeakReference<V>? = null
    protected lateinit var sharedPrefsManagerComponent: SharedPrefsManagerComponent

    private var isOnFreshStart = true
    private var isStateRestored = false
    protected var savedInstanceState: Bundle? = null

    @DebugLog
    override fun attachView(view: V?) {
        viewRef = WeakReference<V>(view)
    }

    protected val view: V?
        get() = if (viewRef == null) null else viewRef!!.get()

    protected val isViewAttached: Boolean
        get() = viewRef != null && viewRef!!.get() != null

    @DebugLog
    protected abstract fun freshStart()   // called only at fresh start in onStart() lifecycle callback

    @DebugLog
    protected abstract fun onRestoreState()   // called only at fresh start after state restoring in onStart()

    @DebugLog
    override fun detachView() {
        if (viewRef != null) {
            viewRef!!.clear()
            viewRef = null
        }
    }

    @DebugLog
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(javaClass.simpleName)
        Timber.i("onCreate(presenter=%s)", hashCode())
        injectSharedPrefsManager()
        isStateRestored = savedInstanceState != null
        this.savedInstanceState = savedInstanceState
        // to override
    }

    @DebugLog
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.tag(javaClass.simpleName)
        Timber.i("onActivityResult(presenter=%s)", hashCode())
        // to override
    }

    override fun onStart() {
        Timber.tag(javaClass.simpleName)
        Timber.i("onStart(presenter=%s)", hashCode())
        if (isOnFreshStart) {
            /**
             * [BasePresenter.isStateRestored] flag is sticky - is doesn't get dropped when the
             * state of the screen has been restored after configuration change. But we must not call
             * initialization logic repeatedly in every [BasePresenter.onStart], we must do this
             * once at fresh start.
             */
            if (isStateRestored) {
                Timber.tag(javaClass.simpleName)
                Timber.i("State restored on fresh start. (presenter=%s)", hashCode())
                onRestoreState()
            } else {
                Timber.tag(javaClass.simpleName)
                Timber.i("Fresh start. (presenter=%s)", hashCode())
                freshStart()
            }
            isOnFreshStart = false
        }
        // to override
    }

    override fun onResume() {
        Timber.tag(javaClass.simpleName)
        Timber.i("onResume(presenter=%s)", hashCode())
        // to override
    }

    override fun onPause() {
        Timber.tag(javaClass.simpleName)
        Timber.i("onPause(presenter=%s)", hashCode())
        // to override
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Timber.tag(javaClass.simpleName)
        Timber.i("onSaveInstanceState(presenter=%s)", hashCode())
        // to override
    }

    override fun onStop() {
        Timber.tag(javaClass.simpleName)
        Timber.i("onStop(presenter=%s)", hashCode())
        // to override
    }

    override fun onDestroy() {
        Timber.tag(javaClass.simpleName)
        Timber.i("onDestroy(presenter=%s)", hashCode())
        // to override
    }

    /* Component */
    // --------------------------------------------------------------------------------------------
    protected val applicationComponent: ApplicationComponent?
        get() {
            val view = view
            if (BaseActivity::class.java.isInstance(view)) {
                return (view as BaseActivity<*, *>).applicationComponent
            }
            if (BaseFragment::class.java.isInstance(view)) {
                return (view as BaseFragment<*, *>).applicationComponent
            }
            Timber.tag(javaClass.simpleName)
            Timber.d("Application component is null - either view is not attached or it is not an instance of Base* class. (presenter=%s)", hashCode())
            return null
        }

    /* Internal */
    // --------------------------------------------------------------------------------------------
    private fun injectSharedPrefsManager() {
        // app component isn't null, because onCreate() is followed by attachView()
        val context = applicationComponent!!.context()
        sharedPrefsManagerComponent = DaggerSharedPrefsManagerComponent.builder()
                .sharedPrefsManagerModule(SharedPrefsManagerModule(context))
                .build()
    }
}
