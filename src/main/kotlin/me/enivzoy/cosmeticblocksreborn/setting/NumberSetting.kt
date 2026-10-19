package me.enivzoy.cosmeticblocksreborn.setting

import com.google.gson.JsonObject
import java.util.Locale
import kotlin.math.ceil
import kotlin.math.log10
import kotlin.math.roundToLong

class NumberSetting(
    name: String,
    description: String,
    defaultValue: Double,
    val min: Double,
    val max: Double,
    val step: Double,
    val unit: String,
) : Setting<Double>(name, description, defaultValue) {

    private val decimals: Int

    init {
        require(min < max) { "$name: min ($min) must be < max ($max)" }
        require(step > 0.0) { "$name: step must be positive" }
        decimals = if (step >= 1.0) 0 else ceil(-log10(step)).toInt().coerceIn(1, 6)
    }

    override fun coerce(newValue: Double): Double {
        val snapped = (newValue / step).roundToLong() * step
        return snapped.coerceIn(min, max)
    }

    val fraction: Float
        get() = ((value - min) / (max - min)).toFloat()

    fun setFromFraction(f: Float) {
        value = min + (max - min) * f.coerceIn(0.0f, 1.0f)
    }

    fun displayValue(): String {
        val number = if (decimals == 0) {
            value.roundToLong().toString()
        } else {
            String.format(Locale.ROOT, "%.${decimals}f", value)
        }
        return if (unit.isEmpty()) number else number + unit
    }

    override fun writeJson(json: JsonObject) {
        json.addProperty(name, value)
    }

    override fun readJson(json: JsonObject) {
        val element = json.get(name) ?: return
        if (element.isJsonPrimitive && element.asJsonPrimitive.isNumber) {
            value = element.asDouble
        }
    }
}
