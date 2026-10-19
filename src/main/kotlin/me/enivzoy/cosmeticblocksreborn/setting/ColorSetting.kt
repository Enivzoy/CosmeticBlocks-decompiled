package me.enivzoy.cosmeticblocksreborn.setting

import com.google.gson.JsonObject
import java.awt.Color

class ColorSetting(
    name: String,
    description: String,
    defaultValue: Int,
) : Setting<Int>(name, description, defaultValue) {

    var hue: Float = 0f
        private set
    var saturation: Float = 0f
        private set
    var brightness: Float = 0f
        private set
    var alpha: Float = 1f
        private set

    private var syncingFromHsba = false

    init {
        decompose(defaultValue)
    }

    override fun coerce(newValue: Int): Int {
        if (!syncingFromHsba) {
            decompose(newValue)
        }
        return newValue
    }

    fun setHsba(hue: Float, saturation: Float, brightness: Float, alpha: Float) {
        this.hue = hue.coerceIn(0f, 1f)
        this.saturation = saturation.coerceIn(0f, 1f)
        this.brightness = brightness.coerceIn(0f, 1f)
        this.alpha = alpha.coerceIn(0f, 1f)
        val rgb = Color.HSBtoRGB(this.hue, this.saturation, this.brightness) and 0xFFFFFF
        val a = (this.alpha * 255f).toInt().coerceIn(0, 255)
        val packed = (a shl 24) or rgb
        if (packed != value) {
            val listener = onChanged
            onChanged = null
            value = packed
            onChanged = listener
        }
    }

    val opaqueRgb: Int
        get() = value or -0x1000000

    private fun decompose(argb: Int) {
        val hsb = Color.RGBtoHSB((argb shr 16) and 0xFF, (argb shr 8) and 0xFF, argb and 0xFF, null)
        if (hsb[1] > 0.0f) {
            hue = hsb[0]
        }
        saturation = hsb[1]
        brightness = hsb[2]
        alpha = ((argb ushr 24) and 0xFF) / 255.0f
    }

    override fun writeJson(json: JsonObject) {
        json.addProperty(name, value)
    }

    override fun readJson(json: JsonObject) {
        val element = json.get(name) ?: return
        if (element.isJsonPrimitive && element.asJsonPrimitive.isNumber) {
            value = element.asInt
            decompose(value)
        }
    }
}
