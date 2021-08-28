package com.malec.idealwatch.util

import java.util.*

object Watcher {
    private var calendar = Calendar.getInstance()

    val seconds: Int
        get() {
            update()
            return calendar.get(Calendar.SECOND)
        }

    val minutes: Int
        get() {
            update()
            return calendar.get(Calendar.MINUTE)
        }

    /**
     * Часы в 24-ом формате
     */
    val hours: Int
        get() {
            update()
            return calendar.get(Calendar.HOUR_OF_DAY)
        }

    /**
     * День недели начиная с понедельника
     */
    val dayOfWeek: Int
        get() {
            update()
            return calendar.get(Calendar.DAY_OF_WEEK) - 1
        }

    val dayOfMonth: Int
        get() {
            update()
            return calendar.get(Calendar.DAY_OF_MONTH)
        }

    val month: Int
        get() {
            update()
            return calendar.get(Calendar.MONTH) + 1
        }

    val currentDayAt0: Long
        get() {
            val now = System.currentTimeMillis()
            return now - (hours * millisInHour) - (minutes * millisInMinute)
        }

    private fun update() {
        val now = System.currentTimeMillis()
        calendar.timeInMillis = now
    }

    private const val millisInMinute = 1000 * 60
    private const val millisInHour = millisInMinute * 60
}