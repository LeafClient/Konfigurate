package fr.shyrogan.konfigurate.setting

import com.leafclient.trunk.Describable
import fr.shyrogan.konfigurate.Group
import fr.shyrogan.konfigurate.setting.constraint.Constraint
import java.util.*
import kotlin.reflect.KProperty

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
        @Transient val parent: Group,

        /**
         * Returns the value contained by this setting and allows you to modify
         * it.
         */
        value: T,

        override val subGroups: MutableList<Group> = LinkedList(),

        /**
         * Returns the constraint for this setting
         */
        @Transient private val constraints: Array<Constraint<T>>
): Describable, Group {

        var value: T = value
                set(value) {
                        var future = value
                        constraints.forEach {
                                future = it(this.value, future)
                        }
                        field = value
                }

        /**
         * Returns the [Constraint] instance (if it exists) contained by this [Setting]
         */
        fun <C: Constraint<T>> getConstraint(clazz: Class<C>)
                = constraints.firstOrNull { clazz == it::class.java }

        /**
         * Returns the [Constraint] instance (if it exists) contained by this [Setting]
         */
        inline fun <reified C: Constraint<T>> getConstraint()
                = getConstraint(C::class.java)

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

}