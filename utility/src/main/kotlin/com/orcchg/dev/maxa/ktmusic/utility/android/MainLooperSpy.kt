package com.orcchg.dev.maxa.ktmusic.utility.android

import android.os.Looper
import android.os.Message
import android.os.MessageQueue
import com.orcchg.dev.maxa.ktmusic.utility.DebugSake
import timber.log.Timber
import java.lang.reflect.Field

@DebugSake
class MainLooperSpy {
    private val messagesField: Field
    private val nextField: Field
    private val mainMessageQueue: MessageQueue

    init {
        try {
            val queueField = Looper::class.java.getDeclaredField("mQueue")
            queueField.isAccessible = true
            messagesField = MessageQueue::class.java.getDeclaredField("mMessages")
            messagesField.isAccessible = true
            nextField = Message::class.java.getDeclaredField("next")
            nextField.isAccessible = true
            val mainLooper = Looper.getMainLooper()
            mainMessageQueue = queueField.get(mainLooper) as MessageQueue
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    fun dumpQueue() {
        try {
            val nextMessage = messagesField.get(mainMessageQueue) as Message
            Timber.v("Begin dumping queue")
            dumpMessages(nextMessage)
            Timber.v("End dumping queue")
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }

    }

    @Throws(IllegalAccessException::class)
    private fun dumpMessages(message: Message?) {
        if (message != null) {
            Timber.v(message.toString())
            val next = nextField.get(message) as Message
            dumpMessages(next)
        }
    }
}
