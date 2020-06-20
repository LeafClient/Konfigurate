<h1 align="center">Konfigurate</h1>
<div align="center">
  <strong>A small yet complete Setting library for Kotlin</strong>
</div>

### Getting started

Once you added the library into your project, you can define your settings
by defining a Group implementation

````kotlin
class Settings: Group()
````
You'll then need to define your settings using the delegation such as
````kotlin
val cheese by setting("Cheetos?", false)
````
You can also use the lambda expression to provide more specifications such as
constraints.

### Serialization

The ``GroupSerializer`` provides two method to easily write and read your Group instances.
They are written as extension function, you should import them such as:
````kotlin
import fr.shyrogan.konfigurate.serialization.GroupSerializer.serialize
import fr.shyrogan.konfigurate.serialization.GroupSerializer.deserialize
````

You can also modify the ``Gson`` used by modifying the value of the ``GroupSerializer.GSON`` property.  
**[!]** The ``.excludeFieldsWithoutExposeAnnotation()`` should always be used otherwise GSON might serialize our delegate properties

### Specifications

#### Using Constraints

The huge difference with other settings libraries are constraints, they allow
you to restrict the values possibilities for a Setting, Konfigurate provides 2 examples:  
- **RestrictionConstraint**: Restricts the values to an array of choices.
- **CoerceConstraint**: Coerces a number setting between two values.

They can also be used as a way to implement interfaces for example a ``Toggleable`` interface
which defines a ``isRunning`` boolean can be implemented using:
````kotlin
class ToggleableConstraint(defaultValue: Boolean): Constraint<Boolean> {

    override var isRunning: Boolean = defaultValue

    override fun invoke(current: Boolean, future: Boolean) = future.also {
        isRunning = it
    }

}

fun SettingBuilder<Boolean>.toggleable() {
    constraints += ToggleableConstraint(defaultValue)
}
````

#### Using the SettingCallback interface

We might want to add automatically the implementation previously written when a Setting is created.
To do this, we'll implement the ``SettingCallback`` interface

````kotlin
class MySettingCallback: SettingCallback {

    override fun <T: Any> onCreate(setting: SettingBuilder<T>)
        = setting.toggleable()

}
````