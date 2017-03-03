package com.orcchg.dev.maxa.ktmusic.library.domain.executor

import timber.log.Timber
import java.util.concurrent.*
import javax.inject.Inject

class JobExecutor @Inject constructor(): ThreadExecutor {
    val INITIAL_POOL_SIZE = 5
    val MAX_POOL_SIZE = 8
    val KEEP_ALIVE_TIME = 10
    val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS

    var threadPoolExecutor: ThreadPoolExecutor = initThreadPoolExecutor()

    override fun execute(command: Runnable?) {
        if (command == null) {
            throw IllegalArgumentException("Runnable to execute cannot be null")
        }
        threadPoolExecutor.execute(command)
    }

    fun shutdownNow() {
        Timber.tag(javaClass.simpleName)
        Timber.i("Shutting down now Thread Executor")
        threadPoolExecutor.shutdownNow()
        Timber.tag(javaClass.simpleName)
        Timber.i("Re-initialize new Thread Executor")
        threadPoolExecutor = initThreadPoolExecutor()
    }

    class JobThreadFactory : ThreadFactory {
        val THREAD_NAME = "ktmusic_thread_"
        var counter = 0

        override fun newThread(runnable: Runnable): Thread {
            return Thread(runnable, THREAD_NAME + counter++)
        }
    }

    /* Internal */
    // --------------------------------------------------------------------------------------------
    fun createWorkingQueue(): BlockingQueue<Runnable> {
        return LinkedBlockingQueue()
    }

    // {@see http://letslearnjavaj2ee.blogspot.ru/2013/08/threadpoolexecutor-handler-policies-for.html}
    fun createRejectPolicy(): RejectedExecutionHandler {
        return ThreadPoolExecutor.DiscardPolicy()
    }

    fun createThreadFactory(): ThreadFactory {
        return JobThreadFactory()
    }

    fun initThreadPoolExecutor(): ThreadPoolExecutor {
        return ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME.toLong(), KEEP_ALIVE_TIME_UNIT,
                createWorkingQueue(), createThreadFactory(), createRejectPolicy())
    }
}
