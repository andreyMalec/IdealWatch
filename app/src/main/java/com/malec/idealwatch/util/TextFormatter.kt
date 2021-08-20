package com.malec.idealwatch.util

import android.util.Log

object TextFormatter {
    fun formatTime(hours: Int, minutes: Int, second: Int? = null): String {
        val h = twoDigitsFormat(hours)
        val m = twoDigitsFormat(minutes)
        val s = second?.let {
            ":${twoDigitsFormat(second)}"
        } ?: ""

        return "$h:$m$s"
    }

    private fun twoDigitsFormat(value: Int) = if (value > 9) "$value" else "0$value"
}