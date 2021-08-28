package com.malec.idealwatch.storage

import android.content.Context

class StateSharedPref(context: Context) : StateStorage {
    private val storage =
        context.getSharedPreferences(StoragePreferences.PREFERENCES, Context.MODE_PRIVATE)

    override var isVisible: Boolean
        get() = storage[StoragePreferences.IS_VISIBLE]
        set(value) {
            storage[StoragePreferences.IS_VISIBLE] = value
        }
    override var isAmbientMode: Boolean
        get() = storage[StoragePreferences.IS_AMBIENT_MODE]
        set(value) {
            storage[StoragePreferences.IS_AMBIENT_MODE] = value
        }
    override var isMuteMode: Boolean
        get() = storage[StoragePreferences.IS_MUTE_MODE]
        set(value) {
            storage[StoragePreferences.IS_MUTE_MODE] = value
        }
    override var isLowBitAmbient: Boolean
        get() = storage[StoragePreferences.IS_LOW_BIT_AMBIENT]
        set(value) {
            storage[StoragePreferences.IS_LOW_BIT_AMBIENT] = value
        }
    override var isBurnInProtection: Boolean
        get() = storage[StoragePreferences.IS_BURN_IN_PROTECTION]
        set(value) {
            storage[StoragePreferences.IS_BURN_IN_PROTECTION] = value
        }

    override fun clear() {
        storage.clear()
    }
}