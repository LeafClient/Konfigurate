package fr.shyrogan.konfigurate.callback

import fr.shyrogan.konfigurate.setting.Setting
import fr.shyrogan.konfigurate.setting.SettingBuilder

interface SettingCallback {

    /**
     * Method executed when a new [Setting] instance is created.
     */
    fun <T: Any> onCreate(setting: SettingBuilder<T>) {}

    companion object {
        val callbacks = mutableListOf<SettingCallback>()
    }

}