package me.enivzoy.cosmeticblocksreborn.setting

import com.google.gson.JsonObject
import java.util.Locale

class ModeSetting<E : Enum<E>>(
    name: String,
    description: String,
    defaultValue: E,
) : Setting<E>(name, description, defaultValue) {

    val options: List<E> = defaultValue.declaringJavaClass.enumConstants?.toList() ?: emptyList()

    fun cycle() {
        value = options[(options.indexOf(value) + 1) % options.size]
    }

    fun label(option: Enum<*>): String =
        option.name.lowercase(Locale.ROOT)
            .split('_')
            .joinToString(" ") { capitalizeWord(it) }

    fun currentLabel(): String = label(value)

    fun select(option: Enum<*>) {
        require(options.contains(option)) { "$name: ${option.name} is not a valid option" }
        @Suppress("UNCHECKED_CAST")
        value = option as E
    }

    override fun writeJson(json: JsonObject) {
        json.addProperty(name, value.name)
    }

    override fun readJson(json: JsonObject) {
        val element = json.get(name) ?: return
        if (element.isJsonPrimitive && element.asJsonPrimitive.isString) {
            val stored = element.asString
            val match = options.firstOrNull { it.name == stored }
            if (match != null) {
                value = match
            }
        }
    }

    private fun capitalizeWord(word: String): String =
        if (word.isNotEmpty()) word[0].uppercase(Locale.ROOT) + word.substring(1) else word
}
