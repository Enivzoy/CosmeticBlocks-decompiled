package me.enivzoy.cosmeticblocksreborn.gui.component

import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen
import me.enivzoy.cosmeticblocksreborn.render.Anim
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.render.Theme
import me.enivzoy.cosmeticblocksreborn.setting.BooleanSetting
import me.enivzoy.cosmeticblocksreborn.setting.ButtonSetting
import me.enivzoy.cosmeticblocksreborn.setting.ColorSetting
import me.enivzoy.cosmeticblocksreborn.setting.ConfigListSetting
import me.enivzoy.cosmeticblocksreborn.setting.KeybindSetting
import me.enivzoy.cosmeticblocksreborn.setting.LabelSetting
import me.enivzoy.cosmeticblocksreborn.setting.ModeSetting
import me.enivzoy.cosmeticblocksreborn.setting.NumberSetting
import me.enivzoy.cosmeticblocksreborn.setting.Setting
import me.enivzoy.cosmeticblocksreborn.setting.StringSetting

abstract class SettingComponent<S : Setting<*>>(
    protected val gui: ClickGuiScreen,
    val setting: S,
) {
    var x: Float = 0f
    var y: Float = 0f
    var width: Float = 0f
    protected val hoverAnim: Anim = Anim(0f, 18f)

    open fun getHeight(): Float = 26f

    protected val padLeft: Float
        get() = x + 16f

    protected val padRight: Float
        get() = x + width - 12f

    protected fun rowHovered(mouseX: Float, mouseY: Float): Boolean =
        mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 26f

    fun contains(mouseX: Float, mouseY: Float): Boolean =
        mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + getHeight()

    abstract fun render(mouseX: Float, mouseY: Float, delta: Float)

    open fun mouseClicked(mouseX: Float, mouseY: Float, button: Int): Boolean = false

    open fun mouseReleased(button: Int) {
    }

    open fun mouseDragged(mouseX: Float, mouseY: Float) {
    }

    open fun keyPressed(key: Int, modifiers: Int): Boolean = false

    open fun charTyped(text: String): Boolean = false

    protected fun renderRowBase(mouseX: Float, mouseY: Float, delta: Float) {
        val hovered = rowHovered(mouseX, mouseY)
        hoverAnim.target = if (hovered) 1f else 0f
        hoverAnim.update(delta)
        if (hoverAnim.value > 0.01f) {
            CascadeUi.rect(x + 4f, y + 1f, width - 8f, 24f, Theme.withAlpha(318767103, hoverAnim.value), 5f)
        }
        CascadeUi.text(setting.name, padLeft, y + 13f, 12f, -1184275, CascadeUi.Font.REGULAR)
        if (hovered && setting.description.length > 0) {
            gui.requestTooltip(setting.description)
        }
    }

    companion object {
        fun create(gui: ClickGuiScreen, setting: Setting<*>): SettingComponent<*>? = when (setting) {
            is BooleanSetting -> BooleanComponent(gui, setting)
            is NumberSetting -> SliderComponent(gui, setting)
            is ModeSetting<*> -> ModeComponent(gui, setting)
            is ColorSetting -> ColorComponent(gui, setting)
            is KeybindSetting -> KeybindComponent(gui, setting)
            is StringSetting -> StringComponent(gui, setting)
            is ConfigListSetting -> ConfigListComponent(gui, setting)
            is LabelSetting -> TextComponent(gui, setting)
            is ButtonSetting -> ButtonComponent(gui, setting)
            else -> null
        }
    }
}
