package fr.shyrogan.konfigurate

import com.google.gson.annotations.Expose
import fr.shyrogan.konfigurate.setting.Setting
import fr.shyrogan.konfigurate.setting.SettingBuilder
import java.util.*

/**
 * Represents a [Group] of settings to the library and provide
 * utilities to manipulate them.
 */
open class Group(

    /**
     * Returns the name of this [Group].
     */
    open val name: String,

    /**
     * Returns each sub group for this [Group] instance
     */
    open val subGroups: MutableList<Group> = LinkedList()
) {

    /**
     * Returns the group contained by this group with the name [name]
     */
    fun getGroup(name: String)
        = subGroups.firstOrNull { it.name.equals(name, true) }

    /**
     * Creates a new [Setting] instance inside of this [Group]
     */
    inline fun <T: Any> setting(
        name: String, defaultValue: T, crossinline apply: SettingBuilder<T>.() -> Unit = {}
    ): Setting<T> {
        val builder = SettingBuilder(name, this, defaultValue)
        apply(builder)

        val built = builder.build
        built.parent.subGroups += built
        return built
    }

    /**
     * @inheritDoc
     */
    override fun toString(): String {
        return "Group(name='$name', subGroups=$subGroups)"
    }


}