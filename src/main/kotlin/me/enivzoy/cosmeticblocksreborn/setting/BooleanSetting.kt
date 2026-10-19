package me.enivzoy.cosmeticblocksreborn.setting

import com.google.gson.JsonObject

class BooleanSetting(
    name: String,
    description: String,
    defaultValue: Boolean,
) : Setting<Boolean>(name, description, defaultValue) {

    fun toggle() {
        value = !value
    }

    override fun writeJson(json: JsonObject) {
        json.addProperty(name, value)
    }

    override fun readJson(json: JsonObject) {
        val element = json.get(name) ?: return
        if (element.isJsonPrimitive && element.asJsonPrimitive.isBoolean) {
            value = element.asBoolean
        }
    }
}
