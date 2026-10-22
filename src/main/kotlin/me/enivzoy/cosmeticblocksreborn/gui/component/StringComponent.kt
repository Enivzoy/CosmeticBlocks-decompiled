package me.enivzoy.cosmeticblocksreborn.gui.component

import com.mojang.blaze3d.platform.InputConstants
import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.setting.StringSetting
import me.enivzoy.cosmeticblocksreborn.util.InputCompat
import net.minecraft.client.Minecraft

class StringComponent(gui: ClickGuiScreen, setting: StringSetting) : SettingComponent<StringSetting>(gui, setting) {
    var focused: Boolean = false
        private set
    private var cursor: Int = 0
    private var blinkTimer: Float = 0f

    override fun render(mouseX: Float, mouseY: Float, delta: Float) {
        renderRowBase(mouseX, mouseY, delta)
        blinkTimer += delta
        val value = setting.value
        val empty = value.length == 0
        val display = if (empty && !focused) setting.placeholder else value
        val color = if (focused) -1184275 else if (empty) -10724260 else -6513508
        val nameWidth = CascadeUi.textWidth(setting.name, 12f, CascadeUi.Font.REGULAR) + 12f
        val maxTextWidth = (padRight - padLeft - nameWidth).coerceAtLeast(20f)
        val midY = y + 13f
        CascadeUi.save()
        CascadeUi.intersectScissor(padRight - maxTextWidth, y, maxTextWidth, 26f)
        try {
            CascadeUi.textRight(display, padRight, midY, 12f, color, CascadeUi.Font.REGULAR)
            if (focused && blinkTimer % 1f < 0.55f) {
                val textWidth = CascadeUi.textWidth(value, 12f, CascadeUi.Font.REGULAR)
                val textStart = padRight - textWidth
                val prefix = value.substring(0, cursor)
                val caretX = textStart + CascadeUi.textWidth(prefix, 12f, CascadeUi.Font.REGULAR)
                CascadeUi.rect(caretX.coerceAtMost(padRight), midY - 6f, 1.2f, 12f, -1750963, 0f)
            }
        } finally {
            CascadeUi.restore()
        }
        if (focused) {
            CascadeUi.rect(padRight - maxTextWidth, y + 26f - 4f, maxTextWidth, 1.5f, 1088768077, 0.75f)
        }
    }

    override fun mouseClicked(mouseX: Float, mouseY: Float, button: Int): Boolean {
        val inside = rowHovered(mouseX, mouseY)
        if (button == InputConstants.MOUSE_BUTTON_LEFT && inside) {
            focused = true
            cursor = setting.value.length
            blinkTimer = 0f
            return true
        }
        if (focused && !inside) {
            focused = false
        }
        return false
    }

    override fun keyPressed(key: Int, modifiers: Int): Boolean {
        if (!focused) {
            return false
        }
        blinkTimer = 0f
        when (key) {
            InputConstants.KEY_V -> if (modifiers and InputConstants.MOD_CONTROL != 0) {
                val clipboard = Minecraft.getInstance().keyboardHandler.clipboard
                insert(clipboard)
            }
            InputConstants.KEY_ESCAPE, InputConstants.KEY_RETURN, InputCompat.KEY_KP_ENTER -> focused = false
            InputConstants.KEY_BACKSPACE -> if (cursor > 0) {
                setting.value = setting.value.removeRange(cursor - 1, cursor)
                cursor--
            }
            InputConstants.KEY_DELETE -> if (cursor < setting.value.length) {
                setting.value = setting.value.removeRange(cursor, cursor + 1)
            }
            InputConstants.KEY_RIGHT -> cursor = (cursor + 1).coerceAtMost(setting.value.length)
            InputConstants.KEY_LEFT -> cursor = (cursor - 1).coerceAtLeast(0)
            InputConstants.KEY_HOME -> cursor = 0
            InputConstants.KEY_END -> cursor = setting.value.length
        }
        return true
    }

    override fun charTyped(text: String): Boolean {
        if (!focused) {
            return false
        }
        insert(text)
        return true
    }

    private fun insert(text: String) {
        val filtered = StringBuilder()
        for (index in text.indices) {
            val ch = text[index]
            if (ch >= ' ' && ch != '\u007f') {
                filtered.append(ch)
            }
        }
        val clean = filtered.toString()
        if (clean.length != 0) {
            val original = setting.value
            setting.value = StringBuilder(original).insert(cursor, clean).toString()
            cursor = (cursor + (setting.value.length - original.length)).coerceIn(0, setting.value.length)
            blinkTimer = 0f
        }
    }
}
