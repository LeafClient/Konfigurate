package fr.shyrogan.konfigurate.setting.constraint.impl

import fr.shyrogan.konfigurate.setting.SettingBuilder
import fr.shyrogan.konfigurate.setting.constraint.Constraint

/**
 * Allows you to coerce a [Setting] between a [minimum] and a [maximum].
 */
class CoerceConstraint<T: Number>(val minimum: T, val maximum: T): Constraint<T> {

    override fun invoke(current: T, future: T): T {
        if(future.toDouble() > maximum.toDouble())
            return maximum

        if(future.toDouble() < minimum.toDouble())
            return minimum

        return future
    }

}

/**
 * Creates and adds a new [CoerceConstraint]
 */
fun <T: Number> SettingBuilder<T>.coerceIn(minimum: T, maximum: T) {
    constraints += CoerceConstraint(minimum, maximum)
}