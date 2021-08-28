package com.malec.idealwatch.util

object TextFormatter {
    fun formatTime(hours: Int, minutes: Int, second: Int? = null): String {
        val h = twoDigitsFormat(hours)
        val m = twoDigitsFormat(minutes)
        val s = second?.let {
            ":${twoDigitsFormat(second)}"
        } ?: ""

        return "$h:$m$s"
    }

    fun formatDate(dayOfWeek: Int, dayOfMonth: Int, month: Int) =
        "${formatDayOfWeek(dayOfWeek)} ${twoDigitsFormat(dayOfMonth)}/${twoDigitsFormat(month)}"

    private fun formatDayOfWeek(dayOfWeek: Int) =
        when (dayOfWeek) {
            1 -> "ПН"
            2 -> "ВТ"
            3 -> "СР"
            4 -> "ЧТ"
            5 -> "ПТ"
            6 -> "СБ"
            else -> "ВС"
        }

    private fun twoDigitsFormat(value: Int) = if (value > 9) "$value" else "0$value"
}