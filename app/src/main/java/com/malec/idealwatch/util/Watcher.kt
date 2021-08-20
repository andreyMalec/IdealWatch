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

    val hours: Int
        get() {
            update()
            return calendar.get(Calendar.HOUR_OF_DAY)
        }

    private fun update() {
        val now = System.currentTimeMillis()
        calendar.timeInMillis = now
    }
}