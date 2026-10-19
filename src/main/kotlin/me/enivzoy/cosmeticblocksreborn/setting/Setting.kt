package me.enivzoy.cosmeticblocksreborn.setting

import com.google.gson.JsonObject

/**
 * Base class for every configurable option. A setting renders as a row in the
 * click GUI and serialises itself to the settings `JsonObject` (`cb-ui.json`).
 */
abstract class Setting<T>(
    val name: String,
    val description: String,
    val defaultValue: T,
) {
    var onChanged: ((T) -> Unit)? = null
    var visibility: () -> Boolean = { true }
    var subcategory: String = ""

    var value: T = defaultValue
        set(newValue) {
            val coerced = coerce(newValue)
            if (coerced != field) {
                field = coerced
                onChanged?.invoke(coerced)
            }
        }

    val visible: Boolean
        get() = visibility()

    fun reset() {
        value = defaultValue
    }

    protected open fun coerce(newValue: T): T = newValue

    abstract fun writeJson(json: JsonObject)

    abstract fun readJson(json: JsonObject)
}

fun <S : Setting<*>> S.visibleWhen(predicate: () -> Boolean): S {
    visibility = predicate
    return this
}

fun <T, S : Setting<T>> S.onChange(listener: (T) -> Unit): S {
    onChanged = listener
    return this
}

fun ConfigListSetting.optionLabel(provider: (String) -> String): ConfigListSetting {
    labelProvider = provider
    return this
}

fun ConfigListSetting.optionColor(provider: (String) -> Int): ConfigListSetting {
    colorProvider = provider
    return this
}
