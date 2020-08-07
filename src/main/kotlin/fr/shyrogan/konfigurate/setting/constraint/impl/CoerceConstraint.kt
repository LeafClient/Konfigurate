package fr.shyrogan.konfigurate.setting.constraint.impl

import fr.shyrogan.konfigurate.setting.Setting
import fr.shyrogan.konfigurate.setting.constraint.Constraint

/**
 * Allows you to coerce a [Setting] between a [minimum] and a [maximum].
 */
class CoerceConstraint<T: Number>(val setting: Setting<T>, val minimum: T, val maximum: T): Constraint<T> {

    /**
     * Returns a double between 0 and 1.
     */
    val progression: Double
        get() =  1.0 - (maximum.toDouble() - setting.value.toDouble()) / (maximum.toDouble() - minimum.toDouble())

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
fun <T: Number> Setting<T>.coerceIn(minimum: T, maximum: T) {
    addConstraint(CoerceConstraint(this, minimum, maximum))
}