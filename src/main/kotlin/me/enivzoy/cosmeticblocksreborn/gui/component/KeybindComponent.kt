package me.enivzoy.cosmeticblocksreborn.gui.component

import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.setting.KeybindSetting

class KeybindComponent(gui: ClickGuiScreen, setting: KeybindSetting) : SettingComponent<KeybindSetting>(gui, setting) {
    var listening: Boolean = false
        private set

    override fun render(mouseX: Float, mouseY: Float, delta: Float) {
        renderRowBase(mouseX, mouseY, delta)
        val label = if (listening) "\u00e2\u20ac\u00a6" else setting.displayName()
        val chipWidth = CascadeUi.textWidth(label, 12f, CascadeUi.Font.MEDIUM) + 14f
        val chipHeight = 16f
        val chipX = padRight - chipWidth
        val chipY = y + (26f - chipHeight) / 2f
        CascadeUi.rect(chipX, chipY, chipWidth, chipHeight, -14013910, 4f)
        if (listening) {
            CascadeUi.rectOutline(chipX, chipY, chipWidth, chipHeight, -1750963, 4f, 1f)
        }
        CascadeUi.textCentered(
            label,
            chipX + chipWidth / 2f,
            chipY + chipHeight / 2f,
            12f,
            if (listening) -1750963 else if (setting.bound) -1184275 else -10724260,
            CascadeUi.Font.MEDIUM,
        )
    }

    override fun mouseClicked(mouseX: Float, mouseY: Float, button: Int): Boolean {
        if (listening) {
            listening = false
            return rowHovered(mouseX, mouseY)
        } else if (button == 0 && rowHovered(mouseX, mouseY)) {
            listening = true
            return true
        }
        return false
    }

    override fun keyPressed(key: Int, modifiers: Int): Boolean {
        if (!listening) {
            return false
        }
        when (key) {
            256, 261 -> setting.clear()
            else -> setting.value = key
        }
        listening = false
        return true
    }
}
