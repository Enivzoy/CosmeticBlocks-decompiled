package me.enivzoy.cosmeticblocksreborn.gui.component

import com.mojang.blaze3d.platform.InputConstants
import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.setting.ButtonSetting

class ButtonComponent(gui: ClickGuiScreen, setting: ButtonSetting) : SettingComponent<ButtonSetting>(gui, setting) {
    private var pillX: Float = 0f
    private var pillY: Float = 0f
    private var pillW: Float = 0f
    private var pillH: Float = 0f

    override fun render(mouseX: Float, mouseY: Float, delta: Float) {
        renderRowBase(mouseX, mouseY, delta)
        pillW = (CascadeUi.textWidth(setting.actionLabel, 12f, CascadeUi.Font.REGULAR) + 18f).coerceAtLeast(44f)
        pillH = 15f
        pillX = padRight - pillW
        pillY = y + (26f - pillH) / 2f
        val hovered = isPillHovered(mouseX, mouseY)
        val bg = if (hovered) -1750963 else -14013910
        CascadeUi.rect(pillX, pillY, pillW, pillH, bg, pillH / 2f)
        CascadeUi.textCentered(
            setting.actionLabel,
            pillX + pillW / 2f,
            pillY + pillH / 2f,
            12f,
            -1184275,
            CascadeUi.Font.REGULAR,
        )
    }

    private fun isPillHovered(mouseX: Float, mouseY: Float): Boolean =
        mouseX >= pillX && mouseX <= pillX + pillW && mouseY >= pillY && mouseY <= pillY + pillH

    override fun mouseClicked(mouseX: Float, mouseY: Float, button: Int): Boolean {
        if (button == InputConstants.MOUSE_BUTTON_LEFT && isPillHovered(mouseX, mouseY)) {
            setting.onClick.invoke()
            return true
        }
        return false
    }
}
