package com.malec.idealwatch.storage

interface StateStorage {
    var isVisible: Boolean

    var isAmbientMode: Boolean

    var isMuteMode: Boolean

    var isLowBitAmbient: Boolean

    var isBurnInProtection: Boolean

    fun clear()
}