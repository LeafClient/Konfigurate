package fr.shyrogan.konfigurate.setting

import com.leafclient.trunk.Describable
import com.leafclient.trunk.Descriptions
import com.leafclient.trunk.Identifiable
import com.leafclient.trunk.checkIfIdentifiable
import fr.shyrogan.konfigurate.Group
import fr.shyrogan.konfigurate.callback.SettingCallback.Companion.callbacks
import fr.shyrogan.konfigurate.setting.constraint.Constraint
import java.util.*
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.isAccessible

data class SettingBuilder<T: Any>(
    override val identifier: String,
    override val description: String = Descriptions.UNPROVIDED,
    var parent: Group,
    var defaultValue: T
): Identifiable, Describable {

    init {
        checkIfIdentifiable()
    }

    val constraints = mutableListOf<Constraint<T>>()

    /**
     * Defines the delegate of specified [property] as the parent if it's a group.
     * [property] needs to be a property of [parent] otherwise this method will not work.
     */
    @Suppress("unchecked_cast")
    fun childOf(property: KProperty1<out Group, Any>) {
        property.isAccessible = true
        property as KProperty1<Group, Any>
        childOf(property.getDelegate(parent) as Group)
    }

    /**
     * Defines the parent of this future [Setting] as [group]
     */
    fun childOf(group: Group) {
        this.parent = group
    }

    val build: Setting<T>
        get() {
            callbacks.forEach {
                it.onCreate(this)
            }

            return Setting(
                identifier,
                description,
                parent,
                defaultValue,
                LinkedList(),
                constraints.toTypedArray()
            )
        }

}