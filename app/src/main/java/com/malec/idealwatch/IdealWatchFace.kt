package com.malec.idealwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.*
import android.os.BatteryManager
import android.os.Bundle
import android.support.wearable.watchface.CanvasWatchFaceService
import android.support.wearable.watchface.WatchFaceService
import android.support.wearable.watchface.WatchFaceStyle
import android.view.SurfaceHolder
import android.widget.Toast
import com.malec.idealwatch.update.Updatable
import com.malec.idealwatch.vm.FaceViewModel
import com.malec.idealwatch.vm.ViewModelService
import java.util.*

class IdealWatchFace : ViewModelService() {

    override val viewModel = FaceViewModel()

    private var engine: Engine? = null

    private val batteryReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 100)
            viewModel.setBatteryLevel(level)
        }
    }

    override fun onCreateEngine(): Engine {
        engine = Engine()
        viewModel.setUpdatable(engine!!)
        return engine!!
    }

    override fun onCreate() {
        super.onCreate()
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryReceiver)
    }

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
                    Toast.makeText(applicationContext, R.string.message, Toast.LENGTH_SHORT).show()
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