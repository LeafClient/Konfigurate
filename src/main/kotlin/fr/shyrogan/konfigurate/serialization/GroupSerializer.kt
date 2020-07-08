package fr.shyrogan.konfigurate.serialization

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import fr.shyrogan.konfigurate.Group
import fr.shyrogan.konfigurate.serialization.exclusion.AntiDelegateExclusionStrategy
import fr.shyrogan.konfigurate.setting.Setting
import java.io.StringReader

@Suppress("unchecked_cast")
object GroupSerializer {

    var GSON = GsonBuilder()
            .setPrettyPrinting()
            .setExclusionStrategies(AntiDelegateExclusionStrategy())
            .create()

    private const val SUB_GROUPS_NAME = "subGroups"
    private const val VALUE_NAME = "value"

    /**
     * Deserializes [array] by using [reference] as a reference for types.
     */
    private fun parseSubGroups(array: JsonArray, reference: Group) {
        if(array.size() == 0)
            return

        array.forEach {
            val jObject = it.asJsonObject
            val group = reference.getGroup(jObject["identifier"].asString) ?: return@forEach

            if(group is Setting<*> && jObject.has(VALUE_NAME)) {
                (group as Setting<Any>).value = GSON.fromJson(jObject.get(VALUE_NAME), group.value::class.java)
            }
            if(jObject.has(SUB_GROUPS_NAME)) {
                val subGroups = jObject.get(SUB_GROUPS_NAME).asJsonArray
                parseSubGroups(subGroups, group)
            }
        }
    }

    /**
     * Serializes this group into a writable text
     */
    fun Group.serialize(): String = GSON.toJson(this.subGroups)

    /**
     * Applies the values contained inside of [text] (got using [serialize] method).
     */
    fun Group.deserialize(text: String) {
        val jObject = JsonParser().parse(StringReader(text)).asJsonArray
        parseSubGroups(jObject, this)
    }

}