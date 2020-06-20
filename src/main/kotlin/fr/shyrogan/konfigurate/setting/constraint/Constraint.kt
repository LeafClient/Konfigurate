package fr.shyrogan.konfigurate.setting.constraint

/**
 * [Constraint] are invoked when a [Setting] value is changed and provide
 * an efficient way to restrict the values of a [Setting]
 */
interface Constraint<T: Any> {

    /**
     * Method invoked when a [Setting] changes his value.
     * [current] represent the current value of the setting
     * [future] represents the future value of the setting
     * The return value defines the value set to the [Setting]
     */
    operator fun invoke(current: T, future: T): T

}