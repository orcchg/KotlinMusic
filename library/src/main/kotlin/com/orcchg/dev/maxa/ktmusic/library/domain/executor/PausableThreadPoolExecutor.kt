package com.orcchg.dev.maxa.ktmusic.library.domain.executor

import java.util.concurrent.*
import java.util.concurrent.locks.ReentrantLock

class PausableThreadPoolExecutor : ThreadPoolExecutor {

    private var isPaused: Boolean = false
    private val pauseLock = ReentrantLock()
    private val unpaused = pauseLock.newCondition()

    constructor(corePoolSize: Int, maximumPoolSize: Int, keepAliveTime: Long, unit: TimeUnit,
                workQueue: BlockingQueue<Runnable>)
       : super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)

    constructor(corePoolSize: Int, maximumPoolSize: Int, keepAliveTime: Long, unit: TimeUnit,
                workQueue: BlockingQueue<Runnable>, threadFactory: ThreadFactory)
       : super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory)

    constructor(corePoolSize: Int, maximumPoolSize: Int, keepAliveTime: Long, unit: TimeUnit,
                workQueue: BlockingQueue<Runnable>, handler: RejectedExecutionHandler)
       : super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler)

    constructor(corePoolSize: Int, maximumPoolSize: Int, keepAliveTime: Long, unit: TimeUnit,
                workQueue: BlockingQueue<Runnable>, threadFactory: ThreadFactory,
                handler: RejectedExecutionHandler)
       : super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler)

    override fun beforeExecute(t: Thread, r: Runnable) {
        super.beforeExecute(t, r)
        pauseLock.lock()
        try {
            while (isPaused) unpaused.await()
        } catch (ie: InterruptedException) {
            t.interrupt()
        } finally {
            pauseLock.unlock()
        }
    }

    fun pause() {
        pauseLock.lock()
        try {
            isPaused = true
        } finally {
            pauseLock.unlock()
        }
    }

    fun resume() {
        pauseLock.lock()
        try {
            isPaused = false
            unpaused.signalAll()
        } finally {
            pauseLock.unlock()
        }
    }

    @Synchronized
    fun isPaused(): Boolean {
        return isPaused
    }
}
