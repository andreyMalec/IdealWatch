package com.malec.idealwatch.vm

import android.support.wearable.watchface.CanvasWatchFaceService

abstract class ViewModelService: CanvasWatchFaceService() {
    protected abstract val viewModel: ViewModel

    override fun onDestroy() {
        viewModel.clear()
        super.onDestroy()
    }
}