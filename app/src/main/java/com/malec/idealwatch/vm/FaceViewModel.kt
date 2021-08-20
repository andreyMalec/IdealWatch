package com.malec.idealwatch.vm

import android.graphics.Canvas
import com.malec.idealwatch.Painter
import com.malec.idealwatch.update.EngineHandler
import com.malec.idealwatch.update.EngineLooper
import com.malec.idealwatch.update.Updatable

class FaceViewModel: ViewModel() {
    private var isVisible = false
    private var isAmbientMode = false
    private var isMuteMode = false
    private var lowBitAmbient = false
    private var burnInProtection = false

    private var batteryLevel = 100

    private val looper = EngineLooper()
    private val handler = EngineHandler(looper)
    private val painter = Painter()

    init {
        looper.setHandler(handler)
    }

    fun draw(canvas: Canvas) {
        painter.draw(canvas)
    }

    fun setBounds(width: Int, height: Int) {
        painter.setBounds(width, height)
    }

    fun update() {
        looper.update()
    }

    fun setUpdatable(updatable: Updatable) {
        looper.setUpdatable(updatable)
    }

    fun setBatteryLevel(level: Int) {
        if (level != batteryLevel) {
            batteryLevel = level
            painter.setBatteryLevel(level)
        }
    }

    fun setVisibility(isVisible: Boolean) {
        this.isVisible = isVisible
        looper.setVisibility(isVisible)
    }

    fun setAmbientMode(isAmbientMode: Boolean) {
        this.isAmbientMode = isAmbientMode
        looper.setAmbientMode(isAmbientMode)
        painter.setAmbientMode(isAmbientMode)
    }

    fun setMuteMode(isMuteMode: Boolean) {
        this.isMuteMode = isMuteMode
    }

    fun setLowBitAmbient(lowBitAmbient: Boolean) {
        this.lowBitAmbient = lowBitAmbient
        painter.setLowBitAmbient(lowBitAmbient)
    }

    fun setBurnInProtection(burnInProtection: Boolean) {
        this.burnInProtection = burnInProtection
        painter.setBurnInProtection(burnInProtection)
    }

    override fun onCleared() {
        looper.clear()
    }
}