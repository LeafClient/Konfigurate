package fr.shyrogan.konfigurate

import com.leafclient.trunk.Identifiable
import fr.shyrogan.konfigurate.setting.Setting

/**
 * Represents a [Group] of settings to the library and provide
 * utilities to manipulate them.
 */
interface Group: Identifiable {

    val subGroups: MutableList<Group>

    /**
     * Returns the group contained by this group with [identifier]
     */
    fun getGroup(identifier: String) = subGroups.firstOrNull { it.identifier.equals(identifier, true) }

}

/**
 * Creates a new [Setting] instance inside of this [Group]
 */
inline fun <T: Any> Group.setting(
    identifier: String, description: String, defaultValue: T,
    crossinline apply: Setting<T>.() -> Unit = {}
): Setting<T> {
    val setting = Setting(identifier, description, this, defaultValue)
    apply(setting)
    subGroups += setting
    return setting
}