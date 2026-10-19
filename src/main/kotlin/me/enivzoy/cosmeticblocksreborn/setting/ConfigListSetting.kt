package me.enivzoy.cosmeticblocksreborn.setting

import com.google.gson.JsonObject

class ConfigListSetting(
    name: String,
    description: String,
    defaultValue: String,
    private val optionsProvider: () -> List<String>,
) : Setting<String>(name, description, defaultValue) {

    var labelProvider: ((String) -> String)? = null
    var colorProvider: ((String) -> Int)? = null

    fun options(): List<String> = optionsProvider()

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
