package me.enivzoy.cosmeticblocksreborn.setting

import com.google.gson.JsonObject

class StringSetting(
    name: String,
    description: String,
    defaultValue: String,
    val maxLength: Int,
    val placeholder: String,
) : Setting<String>(name, description, defaultValue) {

    override fun coerce(newValue: String): String =
        if (newValue.length > maxLength) newValue.substring(0, maxLength) else newValue

    override fun writeJson(json: JsonObject) {
        json.addProperty(name, value)
    }

    override fun readJson(json: JsonObject) {
        val element = json.get(name) ?: return
        if (element.isJsonPrimitive && element.asJsonPrimitive.isString) {
            value = element.asString
        }
    }
}
