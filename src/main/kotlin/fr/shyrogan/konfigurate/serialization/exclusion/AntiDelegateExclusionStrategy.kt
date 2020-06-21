package fr.shyrogan.konfigurate.serialization.exclusion

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

class AntiDelegateExclusionStrategy: ExclusionStrategy {

    override fun shouldSkipClass(clazz: Class<*>?) = false

    override fun shouldSkipField(f: FieldAttributes) = f.name.endsWith("\$delegate")

}