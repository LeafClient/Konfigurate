package fr.shyrogan.konfigurate.setting

import com.leafclient.trunk.Describable
import fr.shyrogan.konfigurate.Group
import fr.shyrogan.konfigurate.callback.SettingCallback
import fr.shyrogan.konfigurate.setting.constraint.Constraint
import java.util.*
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.isAccessible

/**
 * A [Setting] is basically an extension of [Group] that holds a [value] and has
 * some extra stuff such as [Constraint]
 */
class Setting<T: Any>(
        override val identifier: String,
        override val description: String,

        /**
         * Returns the parent group of this [Setting]
         */
        @Transient var parent: Group,

        /**
         * Returns the value contained by this setting and allows you to modify
         * it.
         */
        value: T,

        override val subGroups: MutableList<Group> = LinkedList(),

        /**
         * Returns the constraint for this setting
         */
        @Transient var constraints: MutableMap<Class<out Constraint<T>>, Constraint<T>> = linkedMapOf()
): Describable, Group {

        var value: T = value
                set(value) {
                        var future = value
                        constraints.forEach { (_, it) ->
                                future = it(this.value, future)
                        }
                        field = future
                }

        /**
         * Returns the [Constraint] instance (if it exists) contained by this [Setting]
         */
        @Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
        inline fun <C : Constraint<T>> getConstraint(clazz: Class<C>) = constraints[clazz] as C?

        /**
         * Returns the [Constraint] instance (if it exists) contained by this [Setting]
         */
        inline fun <reified C : Constraint<T>> getConstraint() = getConstraint(C::class.java)

        /**
         * Adds specified [constraint] to the constrains of this [Setting]
         */
        @Suppress("NOTHING_TO_INLINE")
        inline fun addConstraint(constraint: Constraint<T>) = constraints.put(constraint::class.java, constraint)

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
         * Modifies the parent of this setting to [group]
         */
        fun childOf(group: Group) {
                parent.subGroups.remove(this)
                parent = group
                parent.subGroups.add(this)
        }

        /**
         * Returns the value (delegation).
         */
        operator fun getValue(thisRef: Any, property: KProperty<*>) = value

        /**
         * Defines the value (delegation).
         */
        operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
                this.value = value
        }

        /**
         * Returns a string form of this [Setting]
         */
        override fun toString(): String {
                return "Setting(name=$identifier, value=$value)"
        }

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Setting<*>

                if (identifier.equals(other.identifier, true)) return false
                if (value != other.value) return false

                return true
        }

        override fun hashCode(): Int {
                var result = identifier.hashCode()
                result = 31 * result + value.hashCode()
                return result
        }


        init {
                SettingCallback.callbacks
                        .forEach { it.onCreate(this) }
        }

}