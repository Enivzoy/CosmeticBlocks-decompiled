package me.enivzoy.cosmeticblocksreborn.setting

import com.google.gson.JsonObject
import me.enivzoy.cosmeticblocksreborn.util.KeyNames

class KeybindSetting(
    name: String,
    description: String,
    defaultValue: Int,
) : Setting<Int>(name, description, defaultValue) {

    val bound: Boolean
        get() = value != -1

    fun displayName(): String = KeyNames.name(value)

    fun clear() {
        value = -1
    }

    override fun writeJson(json: JsonObject) {
        json.addProperty(name, value)
    }

    override fun readJson(json: JsonObject) {
        val element = json.get(name) ?: return
        if (element.isJsonPrimitive && element.asJsonPrimitive.isNumber) {
            value = element.asInt
        }
    }
}
