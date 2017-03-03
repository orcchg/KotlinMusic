package com.orcchg.dev.maxa.ktmusic.app.injection.application

import android.content.Context
import com.orcchg.dev.maxa.ktmusic.app.MusicApplication
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.JobExecutor
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.PostExecuteScheduler
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.ThreadExecutor
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.UiThread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MusicApplication) {

    /* Context */
    // ------------------------------------------
    @Provides
    internal fun provideApplicationContext(): Context {
        return application.applicationContext
    }

    /* Execution */
    // ------------------------------------------
    @Provides @Singleton
    internal fun provideThreadExecutor(executor: JobExecutor): ThreadExecutor {
        return executor
    }

    @Provides @Singleton
    internal fun providePostExecuteScheduler(uiThread: UiThread): PostExecuteScheduler {
        return uiThread
    }
}
