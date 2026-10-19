package me.enivzoy.cosmeticblocksreborn.gui.component

import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.setting.LabelSetting

class TextComponent(gui: ClickGuiScreen, setting: LabelSetting) : SettingComponent<LabelSetting>(gui, setting) {
    private val isHeader: Boolean = setting.text.startsWith("---", false)
    private val lines: List<String> = wrap(setting.text)

    override fun getHeight(): Float =
        (if (setting.spaceBefore) 8f else 0f) + lines.size * 13f + 4f

    override fun render(mouseX: Float, mouseY: Float, delta: Float) {
        var top = y + (if (setting.spaceBefore) 8f else 0f) + 6.5f + 1f
        val color = if (isHeader) -1184275 else -6513508
        val font = if (isHeader) CascadeUi.Font.BOLD else CascadeUi.Font.REGULAR
        for (line in lines) {
            CascadeUi.text(line, x + 16f, top, 12f, color, font)
            top += 13f
        }
    }

    private fun wrap(text: String): List<String> {
        val avail = 212f
        val maxChars = (avail / 7.44f).toInt().coerceAtLeast(8)
        if (text.length <= maxChars) {
            return listOf(text)
        }
        val out = ArrayList<String>()
        val current = StringBuilder()
        for (word in text.split(' ')) {
            if (current.length == 0) {
                current.append(word)
            } else if (current.length + 1 + word.length <= maxChars) {
                current.append(' ').append(word)
            } else {
                out.add(current.toString())
                current.setLength(0)
                current.append(word)
            }
        }
        if (current.length > 0) {
            out.add(current.toString())
        }
        return out
    }

    companion object {
        @Deprecated("")
        const val LINE_HEIGHT: Float = 13f

        @Deprecated("")
        const val CHAR_WIDTH_RATIO: Float = 0.62f
    }
}
