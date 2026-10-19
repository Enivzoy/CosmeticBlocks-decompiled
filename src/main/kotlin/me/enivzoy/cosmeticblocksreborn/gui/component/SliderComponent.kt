package me.enivzoy.cosmeticblocksreborn.gui.component

import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen
import me.enivzoy.cosmeticblocksreborn.render.Anim
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.setting.NumberSetting

class SliderComponent(gui: ClickGuiScreen, setting: NumberSetting) : SettingComponent<NumberSetting>(gui, setting) {
    private val fillAnim: Anim = Anim(setting.fraction, 22f)
    private var dragging: Boolean = false
    private val trackHeight: Float = 3f

    override fun getHeight(): Float = 32f

    private val trackY: Float
        get() = y + 26f + 1f

    override fun render(mouseX: Float, mouseY: Float, delta: Float) {
        renderRowBase(mouseX, mouseY, delta)
        CascadeUi.textRight(setting.displayValue(), padRight, y + 13f, 12f, -6513508, CascadeUi.Font.MEDIUM)
        if (dragging) {
            updateFromMouse(mouseX)
        }
        fillAnim.target = setting.fraction
        val fill = fillAnim.update(delta)
        val trackWidth = padRight - padLeft
        CascadeUi.rect(padLeft, trackY, trackWidth, trackHeight, -14013910, trackHeight / 2f)
        if (fill > 0.001f) {
            CascadeUi.rect(padLeft, trackY, trackWidth * fill, trackHeight, -1750963, trackHeight / 2f)
        }
        val knobRadius = if (dragging) 5f else 4f
        CascadeUi.circle(padLeft + trackWidth * fill, trackY + trackHeight / 2f, knobRadius, -1184275)
    }

    override fun mouseClicked(mouseX: Float, mouseY: Float, button: Int): Boolean {
        if (button != 0) {
            return false
        } else if (contains(mouseX, mouseY)) {
            dragging = true
            updateFromMouse(mouseX)
            return true
        }
        return false
    }

    override fun mouseDragged(mouseX: Float, mouseY: Float) {
        if (dragging) {
            updateFromMouse(mouseX)
        }
    }

    override fun mouseReleased(button: Int) {
        if (button == 0) {
            dragging = false
        }
    }

    private fun updateFromMouse(mouseX: Float) {
        val trackWidth = padRight - padLeft
        if (trackWidth > 0f) {
            setting.setFromFraction((mouseX - padLeft) / trackWidth)
        }
    }
}
