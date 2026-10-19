package me.enivzoy.cosmeticblocksreborn.gui.component

import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen
import me.enivzoy.cosmeticblocksreborn.render.Anim
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.setting.ConfigListSetting

class ConfigListComponent(gui: ClickGuiScreen, setting: ConfigListSetting) :
    SettingComponent<ConfigListSetting>(gui, setting) {
    private var open: Boolean = false
    private val expandAnim: Anim = Anim(0f, 16f)
    private var options: List<String> = emptyList()
    private var labels: List<String> = emptyList()
    private var colors: List<Int> = emptyList()
    private val optionH: Float = 18f

    private val rowH: Float
        get() = 26f

    override fun getHeight(): Float = rowH + expandAnim.value * (options.size * optionH + 4f)

    override fun render(mouseX: Float, mouseY: Float, delta: Float) {
        renderRowBase(mouseX, mouseY, delta)
        val display = headerLabel()
        val shown = if (display.length == 0) "\u00e2\u20ac\u201d" else display
        CascadeUi.textRight(shown, padRight, y + rowH / 2f, 12f, -6513508, CascadeUi.Font.REGULAR)
        expandAnim.target = if (open) 1f else 0f
        expandAnim.update(delta)
        if (expandAnim.value > 0.01f && options.isNotEmpty()) {
            val expandedHeight = expandAnim.value * (options.size * optionH + 4f)
            CascadeUi.save()
            CascadeUi.intersectScissor(x, y + rowH, width, expandedHeight)
            try {
                var oy = y + rowH + 2f
                for (i in options.indices) {
                    val opt = options[i]
                    val label = if (i < labels.size) labels[i] else opt
                    val hovered = mouseX >= x && mouseX <= x + width && mouseY >= oy && mouseY <= oy + optionH
                    val background = if (i < colors.size) colors[i] else 0
                    if (background ushr 24 != 0) {
                        CascadeUi.rect(x + 6f, oy, width - 12f, optionH, background, 4f)
                    }
                    if (hovered) {
                        CascadeUi.rect(x + 6f, oy, width - 12f, optionH, 318767103, 4f)
                    }
                    var color = if (opt == setting.value) -1750963 else -6513508
                    if (background ushr 24 != 0 && isBright(background)) {
                        color = -16777216
                    }
                    CascadeUi.text(label, x + 18f, oy + optionH / 2f, 12f, color, CascadeUi.Font.REGULAR)
                    oy += optionH
                }
            } finally {
                CascadeUi.restore()
            }
        }
    }

    private fun isBright(argb: Int): Boolean {
        val r = argb shr 16 and 255
        val g = argb shr 8 and 255
        val b = argb and 255
        return r * 299 + g * 587 + b * 114 > 70000
    }

    private fun headerLabel(): String {
        val raw = setting.value
        val provider = setting.labelProvider
        if (provider != null) {
            return provider.invoke(raw)
        }
        return raw
    }

    private fun buildOptionLists() {
        options = setting.options()
        val labelProvider = setting.labelProvider
        val colorProvider = setting.colorProvider
        val newLabels = ArrayList<String>()
        val newColors = ArrayList<Int>()
        for (opt in options) {
            var label = opt
            if (labelProvider != null) {
                label = labelProvider.invoke(opt)
            }
            newLabels.add(label)
            newColors.add(if (colorProvider != null) colorProvider.invoke(opt) else 0)
        }
        labels = newLabels
        colors = newColors
    }

    override fun mouseClicked(mouseX: Float, mouseY: Float, button: Int): Boolean {
        if (rowHovered(mouseX, mouseY)) {
            open = !open
            if (open) {
                buildOptionLists()
            }
            return true
        }
        if (open) {
            var oy = y + rowH + 2f
            for (opt in options) {
                if (mouseX >= x && mouseX <= x + width && mouseY >= oy && mouseY <= oy + optionH) {
                    setting.value = opt
                    open = false
                    return true
                }
                oy += optionH
            }
        }
        return false
    }
}
