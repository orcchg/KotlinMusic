package com.orcchg.dev.maxa.ktmusic.app

import android.app.Application
import com.orcchg.dev.maxa.ktmusic.BuildConfig
import com.orcchg.dev.maxa.ktmusic.app.injection.application.DaggerApplicationComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.application.ApplicationComponent
import com.orcchg.dev.maxa.ktmusic.app.injection.application.ApplicationModule
import io.realm.Realm
import timber.log.Timber

class MusicApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        Timber.i("Application onCreate")
        initializeCrashlytics()
        initializeInjector()
        initializeLeakDetection()
        initializeLogger()
        initializeRealmEngine()
    }

    /* Initialization */
    // --------------------------------------------------------------------------------------------
    private fun initializeCrashlytics() {
//        val core = CrashlyticsCore.Builder()
//                .disabled(BuildConfig.DEBUG)
//                .build()
//        Fabric.with(this, Crashlytics.Builder().core(core).build())
    }

    private fun initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    private fun initializeLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return packageName + ":" + super.createStackElementTag(element) + ":" + element.lineNumber
                }
            })
        } else {
//            Timber.plant(CrashlyticsTree())
        }
    }

    private fun initializeLeakDetection() {
//        if (BuildConfig.DEBUG) {
//            LeakCanary.install(this)
//        }
    }

    private fun initializeRealmEngine() {
        Realm.init(this)
    }

    /* Crashlytics */
    // --------------------------------------------------------------------------------------------
    /**
     * {@see https://blog.xmartlabs.com/2015/07/09/Android-logging-with-Crashlytics-and-Timber/}

     * Comment: [Timber.Tree] only supplies the tag when it was explicitly set.
     * In most cases, tag will be null. If you want the tag to be extracted from the log,
     * you need to extend [Timber.DebugTree] instead.
     */
//    inner class CrashlyticsTree : Timber.DebugTree() {
//
//        override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
//            if (priority == Log.VERBOSE) {
//                return
//            }
//
//            Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority)
//            Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag)
//            Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message)
//
//            if (t == null) {
//                Crashlytics.log(priority, tag, message)
//            } else {
//                Crashlytics.logException(t)
//            }
//        }
//
//        companion object {
//            private val CRASHLYTICS_KEY_PRIORITY = "priority"
//            private val CRASHLYTICS_KEY_TAG = "tag"
//            private val CRASHLYTICS_KEY_MESSAGE = "message"
//        }
//    }
}
