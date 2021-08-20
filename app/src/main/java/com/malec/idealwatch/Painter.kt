package com.malec.idealwatch

import android.graphics.*
import com.malec.idealwatch.util.TextFormatter
import com.malec.idealwatch.util.Watcher

class Painter {
    private var isAmbientMode = false
    private var lowBitAmbient = false
    private var burnInProtection = false

    private var batteryLevel = 100
    private val batteryLevelBounds = RectF(15f, 15f, 0f, 0f)

    private val bounds = RectF(0f, 0f, 0f, 0f)

    fun setAmbientMode(isAmbientMode: Boolean) {
        this.isAmbientMode = isAmbientMode
    }

    fun setLowBitAmbient(lowBitAmbient: Boolean) {
        this.lowBitAmbient = lowBitAmbient
    }

    fun setBurnInProtection(burnInProtection: Boolean) {
        this.burnInProtection = burnInProtection
    }

    private val timePaint = Paint().apply {
        color = Color.WHITE
        isAntiAlias = !isAmbientMode
        strokeWidth = 2f
        textSize = 90f
    }

    private val backgroundPaint = Paint().apply {
        color = Color.BLACK
    }

    private val batteryPaint = Paint().apply {
        color = Color.WHITE
        isAntiAlias = !isAmbientMode
        strokeWidth = 4f
        style = Paint.Style.STROKE
    }

    private val batteryTextPaint = Paint().apply {
        color = Color.WHITE
        isAntiAlias = !isAmbientMode
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

//        drawBackground(canvas)
        drawBatteryLevel(canvas)
        drawTime(canvas)
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
            canvas.drawText("${level[i]}", bounds.centerX() / 5, bounds.centerY() - level.size * dif / 2 + offset, batteryTextPaint)
        }
    }

    private fun drawBackground(canvas: Canvas) {
        if (isAmbientMode && (lowBitAmbient || burnInProtection)) {
            canvas.drawColor(backgroundPaint.color)
        } else if (isAmbientMode) {
            canvas.drawColor(backgroundPaint.color)
        }
    }

    private fun drawTime(canvas: Canvas) {
        val seconds = Watcher.seconds
        val minutes = Watcher.minutes
        val hours = Watcher.hours
        val time = TextFormatter.formatTime(hours, minutes)
        timePaint.getTextBounds(timeMax, 0, timeMax.length, rect)
        val x = bounds.centerX() - rect.width() / 2
        val y = bounds.centerY()
        canvas.drawText(time, x, y, timePaint)

        val w = rect.width() / 10
        for (i in 0 until seconds % 10) {
            canvas.drawRect(
                RectF(
                    x + i * w + i * 2,
                    y + 12,
                    x + i * w + w - 4 + i * 2,
                    y + 17
                ),
                timePaint
            )
        }
    }

    companion object {
        private val rect = Rect()
        private const val timeMax = "99:99"
    }
}