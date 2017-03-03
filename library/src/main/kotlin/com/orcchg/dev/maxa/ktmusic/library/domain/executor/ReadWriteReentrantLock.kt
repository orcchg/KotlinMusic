package com.orcchg.dev.maxa.ktmusic.library.domain.executor

import java.util.*

/**
 * Fully reentrant read-write lock
 *
 * {@see http://tutorials.jenkov.com/java-concurrency/read-write-locks.html}
 */
class ReadWriteReentrantLock {
    val readingThreads = HashMap<Thread, Int>()

    var writeAccesses = 0
    var writeRequests = 0
    var writingThread: Thread? = null

    @Synchronized @Throws(InterruptedException::class)
    fun lockRead() {
        val callingThread = Thread.currentThread()
        while (!canGrantReadAccess(callingThread)) {
            (this as java.lang.Object).wait()
        }
        readingThreads.put(callingThread, getReadAccessCount(callingThread) + 1)
    }

    @Synchronized
    fun unlockRead() {
        val callingThread = Thread.currentThread()
        if (!isReader(callingThread)) {
            throw IllegalMonitorStateException("Calling Thread does not" + " hold a read lock on this ReadWriteLock")
        }
        val accessCount = getReadAccessCount(callingThread)
        if (accessCount == 1) {
            readingThreads.remove(callingThread)
        } else {
            readingThreads.put(callingThread, accessCount - 1)
        }
        (this as java.lang.Object).notifyAll()
    }

    @Synchronized @Throws(InterruptedException::class)
    fun lockWrite() {
        ++writeRequests
        val callingThread = Thread.currentThread()
        while (!canGrantWriteAccess(callingThread)) {
            (this as java.lang.Object).wait()
        }
        --writeRequests
        ++writeAccesses
        writingThread = callingThread
    }

    @Synchronized @Throws(InterruptedException::class)
    fun unlockWrite() {
        if (!isWriter(Thread.currentThread())) {
            throw IllegalMonitorStateException("Calling Thread does not" + " hold the write lock on this ReadWriteLock")
        }
        --writeAccesses
        if (writeAccesses == 0) writingThread = null
        (this as java.lang.Object).notifyAll()
    }

    /* Internal */
    // --------------------------------------------------------------------------------------------
    private fun canGrantReadAccess(callingThread: Thread): Boolean {
        if (isWriter(callingThread)) return true
        if (hasWriter()) return false
        if (isReader(callingThread)) return true
        if (hasWriteRequests()) return false
        return true
    }

    private fun canGrantWriteAccess(callingThread: Thread): Boolean {
        if (isOnlyReader(callingThread)) return true
        if (hasReaders()) return false
        if (writingThread == null) return true
        if (!isWriter(callingThread)) return false
        return true
    }

    private fun getReadAccessCount(callingThread: Thread): Int {
        val accessCount = readingThreads[callingThread] ?: return 0
        return accessCount
    }


    private fun hasReaders(): Boolean {
        return readingThreads.size > 0
    }

    private fun isReader(callingThread: Thread): Boolean {
        return readingThreads[callingThread] != null
    }

    private fun isOnlyReader(callingThread: Thread): Boolean {
        return readingThreads.size == 1 && readingThreads[callingThread] != null
    }

    private fun hasWriter(): Boolean {
        return writingThread != null
    }

    private fun isWriter(callingThread: Thread): Boolean {
        return writingThread === callingThread
    }

    private fun hasWriteRequests(): Boolean {
        return this.writeRequests > 0
    }
}
