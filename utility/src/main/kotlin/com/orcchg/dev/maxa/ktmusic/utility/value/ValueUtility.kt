package com.orcchg.dev.maxa.ktmusic.utility.value

import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class ValueUtility {

    fun isAllTrue(flags: BooleanArray): Boolean {
        for (b in flags) if (!b) return false
        return true
    }

    fun <Model> merge(splitModels: List<List<Model>>): List<Model> {
        val list = ArrayList<Model>()
        for (models in splitModels) {
            list.addAll(models)
        }
        return list
    }

    fun time(): String {
        return time(System.currentTimeMillis())
    }

    fun time(millis: Long): String {
        val xdays = TimeUnit.MILLISECONDS.toDays(millis)
        val xhours = TimeUnit.MILLISECONDS.toHours(millis)
        val xminutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val xseconds = TimeUnit.MILLISECONDS.toSeconds(millis)

        val days = xdays
        val hours = xhours - TimeUnit.DAYS.toHours(xdays)
        val minutes = xminutes - TimeUnit.HOURS.toMinutes(xhours)
        val seconds = xseconds - TimeUnit.MINUTES.toSeconds(xminutes)
        return String.format(Locale.ENGLISH, "%02d %02d:%02d:%02d", days, hours, minutes, seconds)
    }

    fun random(min: Long, max: Long): Long {
        if (min == max) {
            return min
        }
        if (min > max) {
            throw IllegalArgumentException("Min is greater than max!")
        }
        val rng = Random()
        return rng.nextLong() % (max - min) + min
    }

    fun <T> contains(item: T, vararg items: T): Boolean {
        for (i in items.indices) {
            if (item == items[i]) return true
        }
        return false
    }

    fun containsClass(item: Any, items: Array<Class<*>>): Boolean {
        Timber.v("Item class: %s", item.javaClass.simpleName)
        for (i in items.indices) {
            if (items[i].isInstance(item)) return true
        }
        return false
    }

    fun <T> sizeOf(vararg items: T): Int {
        return items.size
    }

    fun <T> sizeOf(items: List<T>?): Int {
        return items?.size ?: 0
    }
}
