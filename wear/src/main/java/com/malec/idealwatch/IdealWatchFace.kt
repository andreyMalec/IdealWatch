package com.malec.idealwatch

import android.graphics.*
import android.os.BatteryManager
import android.os.Bundle
import android.support.wearable.watchface.CanvasWatchFaceService
import android.support.wearable.watchface.WatchFaceService
import android.support.wearable.watchface.WatchFaceStyle
import android.view.SurfaceHolder
import com.malec.idealwatch.update.Updatable
import com.malec.idealwatch.vm.FaceViewModel
import com.malec.idealwatch.vm.ViewModelService
import java.util.*

class IdealWatchFace : ViewModelService() {

    override val viewModel by lazy {
        FaceViewModel(applicationContext)
    }

    private val batteryManager = object : Updatable {
        override fun update() {
            viewModel.setBatteryLevel(currentBatteryLevel())
        }
    }

    override fun onCreateEngine(): Engine {
        val engine = Engine()
        viewModel.addUpdatable(batteryManager)
        viewModel.addUpdatable(engine)
        return engine
    }

    fun currentBatteryLevel() =
        (getSystemService(BATTERY_SERVICE) as BatteryManager)
            .getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

    inner class Engine : CanvasWatchFaceService.Engine(), Updatable {

        override fun onCreate(holder: SurfaceHolder) {
            super.onCreate(holder)

            setWatchFaceStyle(
                WatchFaceStyle.Builder(this@IdealWatchFace)
                    .setAcceptsTapEvents(true)
                    .build()
            )
        }

        override fun onPropertiesChanged(properties: Bundle) {
            super.onPropertiesChanged(properties)
            viewModel.setLowBitAmbient(
                properties.getBoolean(WatchFaceService.PROPERTY_LOW_BIT_AMBIENT, false)
            )
            viewModel.setBurnInProtection(
                properties.getBoolean(WatchFaceService.PROPERTY_BURN_IN_PROTECTION, false)
            )
        }

        override fun onTimeTick() {
            super.onTimeTick()
            invalidate()
        }

        override fun onAmbientModeChanged(inAmbientMode: Boolean) {
            super.onAmbientModeChanged(inAmbientMode)
            viewModel.setAmbientMode(inAmbientMode)

            viewModel.update()
        }

        override fun onInterruptionFilterChanged(interruptionFilter: Int) {
            super.onInterruptionFilterChanged(interruptionFilter)
            viewModel.setMuteMode(interruptionFilter == WatchFaceService.INTERRUPTION_FILTER_NONE)
            invalidate()
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            viewModel.setBounds(width, height)
        }

        override fun onTapCommand(tapType: Int, x: Int, y: Int, eventTime: Long) {
            when (tapType) {
                WatchFaceService.TAP_TYPE_TOUCH -> {
                }
                WatchFaceService.TAP_TYPE_TOUCH_CANCEL -> {
                }
                WatchFaceService.TAP_TYPE_TAP ->
                    viewModel.onTap()
            }
            invalidate()
        }

        override fun onDraw(canvas: Canvas, bounds: Rect) {
            viewModel.draw(canvas)
        }

        override fun onVisibilityChanged(isVisible: Boolean) {
            super.onVisibilityChanged(isVisible)
            viewModel.setVisibility(isVisible)

            viewModel.update()
        }

        override fun update() {
            invalidate()
        }
    }
}