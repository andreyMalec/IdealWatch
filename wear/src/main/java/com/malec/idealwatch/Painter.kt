package com.malec.idealwatch

import android.graphics.*
import com.malec.idealwatch.storage.StateStorage
import com.malec.idealwatch.util.TextFormatter
import com.malec.idealwatch.util.Watcher

class Painter(private val storage: StateStorage) {
    private var batteryLevel = 100
    private val batteryLevelBounds = RectF(15f, 15f, 0f, 0f)

    private val bounds = RectF(0f, 0f, 0f, 0f)

    private val timePaint = Paint().apply {
        color = Color.WHITE
        isAntiAlias = !storage.isAmbientMode
        strokeWidth = 2f
        textSize = 90f
    }

    private val datePaint = Paint().apply {
        color = Color.WHITE
        isAntiAlias = !storage.isAmbientMode
        strokeWidth = 4f
        textSize = 30f
    }

    private val backgroundPaint = Paint().apply {
        color = Color.BLACK
    }

    private val batteryPaint = Paint().apply {
        color = Color.WHITE
        isAntiAlias = !storage.isAmbientMode
        strokeWidth = 4f
        style = Paint.Style.STROKE
    }

    private val batteryTextPaint = Paint().apply {
        color = Color.WHITE
        isAntiAlias = !storage.isAmbientMode
        strokeWidth = 2f
        textSize = 30f
    }

    fun setBounds(width: Int, height: Int) {
        bounds.right = width.toFloat()
        bounds.bottom = height.toFloat()

        batteryLevelBounds.right = bounds.right - 15
        batteryLevelBounds.bottom = bounds.bottom - 15
    }

    fun setBatteryLevel(level: Int) {
        batteryLevel = level
    }

    fun draw(canvas: Canvas) {
        canvas.clear()

        setAntiAlias()
        if (isActiveMode())
            drawBatteryLevel(canvas)
        drawTime(canvas)
        drawDate(canvas)
    }

    private fun Canvas.clear() = drawColor(backgroundPaint.color)

    private fun drawBatteryLevel(canvas: Canvas) {
        batteryPaint.color = Color.GRAY
        canvas.drawArc(batteryLevelBounds, 157.5f, 45f, false, batteryPaint)
        batteryPaint.color = if (batteryLevel <= 15) Color.RED else Color.WHITE
        canvas.drawArc(batteryLevelBounds, 157.5f, 45f * batteryLevel / 100, false, batteryPaint)

        var offset = 0f
        val level = "$batteryLevel%".toList()
        for (i in level.indices) {
            val dif = batteryTextPaint.descent() - batteryTextPaint.ascent() / 6 * 5
            offset += dif
            canvas.drawText(
                "${level[i]}",
                bounds.centerX() / 5,
                bounds.centerY() - level.size * dif / 2 + offset,
                batteryTextPaint
            )
        }
    }

    private fun isActiveMode() = !storage.isAmbientMode

    private fun setAntiAlias() {
        val enabled = isActiveMode()
        timePaint.isAntiAlias = enabled
        batteryPaint.isAntiAlias = enabled
        batteryTextPaint.isAntiAlias = enabled
        datePaint.isAntiAlias = enabled
    }

    private fun drawTime(canvas: Canvas) {
        val seconds = Watcher.seconds
        val minutes = Watcher.minutes
        val hours = Watcher.hours
        val time = TextFormatter.formatTime(hours, minutes)
        timePaint.getTextBounds(timeMax, 0, timeMax.length, rect)
        val x = bounds.centerX() - rect.width() / 2
        val y = bounds.centerY()
        disableColorIfNight(timePaint)
        canvas.drawText(time, x, y, timePaint)

        if (isActiveMode()) {
            val w = rect.width() / 10
            for (i in 0 until seconds % 10) {
                canvas.drawRect(
                    x + i * w + i * 2,
                    y + 12,
                    x + i * w + w - 4 + i * 2,
                    y + 17,
                    timePaint
                )
            }
        }
    }

    private fun drawDate(canvas: Canvas) {
        if (isActiveMode()) {
            val dayOfWeek = Watcher.dayOfWeek
            val dayOfMonth = Watcher.dayOfMonth
            val month = Watcher.month
            val time = TextFormatter.formatDate(dayOfWeek, dayOfMonth, month)
            datePaint.getTextBounds(time, 0, time.length, rect)
            val x = bounds.centerX() - rect.width() / 2
            val y = bounds.centerY() - rect.height() * 4
            disableColorIfNight(datePaint)
            canvas.drawText(time, x, y, datePaint)
        }
    }

    private fun disableColorIfNight(paint: Paint) {
        paint.color = when {
            isActiveMode() -> Color.WHITE
            Watcher.hours in 6..23 -> Color.GRAY
            else -> Color.BLACK
        }
    }

    companion object {
        private val rect = Rect()
        private const val timeMax = "99:99"
    }
}