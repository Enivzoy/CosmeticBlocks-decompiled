package me.enivzoy.cosmeticblocksreborn.gui.component

import com.mojang.blaze3d.platform.InputConstants
import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen
import me.enivzoy.cosmeticblocksreborn.render.Anim
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.setting.ModeSetting

class ModeComponent(gui: ClickGuiScreen, setting: ModeSetting<*>) : SettingComponent<ModeSetting<*>>(gui, setting) {
    private var open: Boolean = false
    private val openAnim: Anim = Anim(0f, 16f)

    private val expandedExtra: Float
        get() = setting.options.size * 22f + 4f

    override fun getHeight(): Float = 26f + openAnim.value * expandedExtra

    override fun render(mouseX: Float, mouseY: Float, delta: Float) {
        renderRowBase(mouseX, mouseY, delta)
        openAnim.target = if (open) 1f else 0f
        openAnim.update(delta)
        val midY = y + 13f
        CascadeUi.textRight(setting.currentLabel(), padRight - 14f, midY, 12f, -6513508, CascadeUi.Font.MEDIUM)
        chevron(padRight - 5f, midY, openAnim.value)
        if (openAnim.value > 0.01f) {
            val listTop = y + 26f
            CascadeUi.save()
            CascadeUi.intersectScissor(x, listTop, width, openAnim.value * expandedExtra)
            try {
                var index = 0
                for (option in setting.options) {
                    val currentIndex = index++
                    val rowY = listTop + currentIndex * 22f
                    val selected = option == setting.value
                    val hovered = mouseX >= padLeft && mouseX <= padRight && mouseY >= rowY && mouseY < rowY + 22f
                    if (hovered) {
                        CascadeUi.rect(x + 10f, rowY + 1f, width - 20f, 20f, 318767103, 4f)
                    }
                    if (selected) {
                        CascadeUi.rect(padLeft, rowY + 6f, 2.5f, 10f, -1750963, 1.25f)
                    }
                    CascadeUi.text(
                        setting.label(option),
                        padLeft + 10f,
                        rowY + 11f,
                        12f,
                        if (selected) -1184275 else -6513508,
                        CascadeUi.Font.REGULAR,
                    )
                }
            } finally {
                CascadeUi.restore()
            }
        }
    }

    override fun mouseClicked(mouseX: Float, mouseY: Float, button: Int): Boolean {
        if (button != InputConstants.MOUSE_BUTTON_LEFT) {
            return false
        }
        if (rowHovered(mouseX, mouseY)) {
            open = !open
            return true
        }
        if (open && contains(mouseX, mouseY)) {
            val index = ((mouseY - (y + 26f)) / 22f).toInt()
            if (index >= 0 && index < setting.options.size) {
                setting.select(setting.options[index])
                open = false
            }
            return true
        }
        return false
    }

    private fun chevron(cx: Float, cy: Float, t: Float) {
        val dir = 1f - 2f * t
        val w = 3.5f
        val h = 2f * dir
        CascadeUi.line(cx - w, cy - h / 2f, cx, cy + h / 2f, -6513508, 1.5f)
        CascadeUi.line(cx, cy + h / 2f, cx + w, cy - h / 2f, -6513508, 1.5f)
    }

    companion object {
        @Deprecated("")
        const val OPTION_HEIGHT: Float = 22f
    }
}
