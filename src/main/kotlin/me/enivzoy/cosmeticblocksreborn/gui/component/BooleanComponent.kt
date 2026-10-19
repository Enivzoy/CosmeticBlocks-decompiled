package me.enivzoy.cosmeticblocksreborn.gui.component

import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen
import me.enivzoy.cosmeticblocksreborn.render.Anim
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.setting.BooleanSetting

class BooleanComponent(gui: ClickGuiScreen, setting: BooleanSetting) : SettingComponent<BooleanSetting>(gui, setting) {
    private val knobAnim = Anim(if (setting.value) 1f else 0f, 16f)

    override fun render(mouseX: Float, mouseY: Float, delta: Float) {
        renderRowBase(mouseX, mouseY, delta)
        knobAnim.target = if (setting.value) 1f else 0f
        val t = knobAnim.update(delta)
        val pillWidth = 28f
        val pillHeight = 15f
        val pillX = padRight - pillWidth
        val pillY = y + (26f - pillHeight) / 2f
        val track = blend(-14013910, -1750963, t)
        CascadeUi.rect(pillX, pillY, pillWidth, pillHeight, track, pillHeight / 2f)
        val knobRadius = pillHeight / 2f - 2.5f
        val knobX = pillX + knobRadius + 2.5f + (pillWidth - 2f * (knobRadius + 2.5f)) * t
        CascadeUi.circle(knobX, pillY + pillHeight / 2f, knobRadius, -1184275)
    }

    override fun mouseClicked(mouseX: Float, mouseY: Float, button: Int): Boolean {
        if (button == 0 && rowHovered(mouseX, mouseY)) {
            setting.toggle()
            return true
        }
        return false
    }

    private fun blend(from: Int, to: Int, t: Float): Int {
        val f = t.coerceIn(0f, 1f)

        fun channel(from: Int, to: Int, f: Float, shift: Int): Int {
            val a = from shr shift and 0xFF
            val b = to shr shift and 0xFF
            return (a + (b - a) * f).toInt().coerceIn(0, 255)
        }

        return (channel(from, to, f, 24) shl 24) or (channel(from, to, f, 16) shl 16) or
            (channel(from, to, f, 8) shl 8) or channel(from, to, f, 0)
    }
}
