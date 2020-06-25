package fr.shyrogan.konfigurate

import com.leafclient.trunk.structure.Labelable
import fr.shyrogan.konfigurate.setting.Setting
import fr.shyrogan.konfigurate.setting.SettingBuilder

/**
 * Represents a [Group] of settings to the library and provide
 * utilities to manipulate them.
 */
interface Group: Labelable {

    val subGroups: MutableList<Group>

    /**
     * Returns the group contained by this group with the name [name]
     */
    fun getGroup(name: String)
        = subGroups.firstOrNull { it.label.equals(name, true) }

}

/**
 * Creates a new [Setting] instance inside of this [Group]
 */
inline fun <T: Any> Group.setting(
    name: String, defaultValue: T, crossinline apply: SettingBuilder<T>.() -> Unit = {}
): Setting<T> {
    val builder = SettingBuilder(name, this, defaultValue)
    apply(builder)

    val built = builder.build
    built.parent.subGroups += built
    return built
}