package com.malec.idealwatch.vm

import android.content.Context
import android.graphics.Canvas
import com.malec.idealwatch.Painter
import com.malec.idealwatch.storage.StateSharedPref
import com.malec.idealwatch.storage.StateStorage
import com.malec.idealwatch.update.EngineLooper
import com.malec.idealwatch.update.Updatable

class FaceViewModel(context: Context) : ViewModel() {

    private var batteryLevel = 100

    private val storage: StateStorage = StateSharedPref(context)
    private val looper = EngineLooper(storage, viewModelScope)
    private val painter = Painter(storage)

    fun draw(canvas: Canvas) {
        painter.draw(canvas)
    }

    fun setBounds(width: Int, height: Int) {
        painter.setBounds(width, height)
    }

    fun update() {
        looper.start()
    }

    fun addUpdatable(updatable: Updatable) {
        looper.addUpdatable(updatable)
    }

    fun removeUpdatable(updatable: Updatable) {
        looper.removeUpdatable(updatable)
    }

    fun onTap() {

    }

    fun setBatteryLevel(level: Int) {
        if (level != batteryLevel) {
            batteryLevel = level
            painter.setBatteryLevel(level)
        }
    }

    fun setVisibility(isVisible: Boolean) {
        storage.isVisible = isVisible
    }

    fun setAmbientMode(isAmbientMode: Boolean) {
        storage.isAmbientMode = isAmbientMode
    }

    fun setMuteMode(isMuteMode: Boolean) {
        storage.isMuteMode = isMuteMode
    }

    fun setLowBitAmbient(isLowBitAmbient: Boolean) {
        storage.isLowBitAmbient = isLowBitAmbient
    }

    fun setBurnInProtection(isBurnInProtection: Boolean) {
        storage.isBurnInProtection = isBurnInProtection
    }

    override fun onCleared() {
        looper.stop()
    }
}