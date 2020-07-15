package fr.shyrogan.konfigurate.callback

import fr.shyrogan.konfigurate.setting.Setting

interface SettingCallback {

    /**
     * Method executed when a new [Setting] instance is created.
     */
    fun <T: Any> onCreate(setting: Setting<T>) {}

    companion object {
        val callbacks = mutableListOf<SettingCallback>()
    }

}