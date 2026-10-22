package me.enivzoy.cosmeticblocksreborn.gui.component

import com.mojang.blaze3d.platform.InputConstants
import java.awt.Color
import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen
import me.enivzoy.cosmeticblocksreborn.render.Anim
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.setting.ColorSetting

class ColorComponent(gui: ClickGuiScreen, setting: ColorSetting) : SettingComponent<ColorSetting>(gui, setting) {
    private var open: Boolean = false
    private var drag: Drag = Drag.NONE
    private val openAnim: Anim = Anim(0f, 16f)

    override fun getHeight(): Float = 26f + openAnim.value * 134f

    private val svY: Float
        get() = y + 26f + 7f

    private val hueY: Float
        get() = svY + 86f + 7f

    private val alphaY: Float
        get() = hueY + 10f + 7f

    private val innerWidth: Float
        get() = padRight - padLeft

    override fun render(mouseX: Float, mouseY: Float, delta: Float) {
        renderRowBase(mouseX, mouseY, delta)
        openAnim.target = if (open) 1f else 0f
        openAnim.update(delta)
        val swatchWidth = 26f
        val swatchHeight = 14f
        val swatchX = padRight - swatchWidth
        val swatchY = y + (26f - swatchHeight) / 2f
        CascadeUi.rect(swatchX, swatchY, swatchWidth, swatchHeight, setting.value, 4f)
        CascadeUi.rectOutline(swatchX, swatchY, swatchWidth, swatchHeight, 352321535, 4f, 1f)
        if (openAnim.value > 0.01f) {
            if (drag != Drag.NONE) {
                updateDrag(mouseX, mouseY)
            }
            CascadeUi.save()
            CascadeUi.intersectScissor(x, y + 26f, width, openAnim.value * 134f)
            try {
                renderSvBox()
                renderHueBar()
                renderAlphaBar()
            } finally {
                CascadeUi.restore()
            }
        }
    }

    private fun renderSvBox() {
        val hueRgb = (Color.HSBtoRGB(setting.hue, 1f, 1f) and 16777215) or -0x1000000
        CascadeUi.gradientRect(padLeft, svY, innerWidth, 86f, 5f, -1, hueRgb, true)
        CascadeUi.gradientRect(padLeft, svY, innerWidth, 86f, 5f, 0, -16777216, false)
        CascadeUi.rectOutline(padLeft, svY, innerWidth, 86f, 352321535, 5f, 1f)
        val selX = padLeft + setting.saturation * innerWidth
        val selY = svY + (1f - setting.brightness) * 86f
        CascadeUi.circle(selX, selY, 5f, -16777216)
        CascadeUi.circle(selX, selY, 4f, -1)
        CascadeUi.circle(selX, selY, 2.5f, setting.opaqueRgb)
    }

    private fun renderHueBar() {
        val segments = 6
        val segmentWidth = innerWidth / segments
        for (i in 0 until segments) {
            val from = (Color.HSBtoRGB(i.toFloat() / segments, 1f, 1f) and 16777215) or -0x1000000
            val to = (Color.HSBtoRGB((i + 1).toFloat() / segments, 1f, 1f) and 16777215) or -0x1000000
            CascadeUi.gradientRect(padLeft + i * segmentWidth, hueY, segmentWidth + 0.5f, 10f, 0f, from, to, true)
        }
        CascadeUi.rectOutline(padLeft, hueY, innerWidth, 10f, 352321535, 3f, 1f)
        selectorLine(padLeft + setting.hue * innerWidth, hueY)
    }

    private fun renderAlphaBar() {
        CascadeUi.rect(padLeft, alphaY, innerWidth, 10f, -14013910, 3f)
        CascadeUi.gradientRect(
            padLeft,
            alphaY,
            innerWidth,
            10f,
            3f,
            setting.opaqueRgb and 16777215,
            setting.opaqueRgb,
            true,
        )
        CascadeUi.rectOutline(padLeft, alphaY, innerWidth, 10f, 352321535, 3f, 1f)
        selectorLine(padLeft + setting.alpha * innerWidth, alphaY)
    }

    private fun selectorLine(selX: Float, barY: Float) {
        val clamped = selX.coerceIn(padLeft + 1f, padRight - 1f)
        CascadeUi.rect(clamped - 1.5f, barY - 1.5f, 3f, 13f, -16777216, 1.5f)
        CascadeUi.rect(clamped - 0.75f, barY - 0.75f, 1.5f, 11.5f, -1, 0.75f)
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
            drag = if (mouseY >= svY && mouseY <= svY + 86f) {
                Drag.SV
            } else if (mouseY >= hueY && mouseY <= hueY + 10f) {
                Drag.HUE
            } else if (mouseY >= alphaY && mouseY <= alphaY + 10f) {
                Drag.ALPHA
            } else {
                Drag.NONE
            }
            if (drag != Drag.NONE) {
                updateDrag(mouseX, mouseY)
            }
            return true
        }
        return false
    }

    override fun mouseDragged(mouseX: Float, mouseY: Float) {
        if (drag != Drag.NONE) {
            updateDrag(mouseX, mouseY)
        }
    }

    override fun mouseReleased(button: Int) {
        if (button == InputConstants.MOUSE_BUTTON_LEFT) {
            drag = Drag.NONE
        }
    }

    private fun updateDrag(mouseX: Float, mouseY: Float) {
        if (innerWidth <= 0f) {
            return
        }
        val fx = ((mouseX - padLeft) / innerWidth).coerceIn(0f, 1f)
        when (drag) {
            Drag.SV -> setting.setHsba(
                setting.hue,
                fx,
                1f - ((mouseY - svY) / 86f).coerceIn(0f, 1f),
                setting.alpha,
            )
            Drag.HUE -> setting.setHsba(fx, setting.saturation, setting.brightness, setting.alpha)
            Drag.ALPHA -> setting.setHsba(setting.hue, setting.saturation, setting.brightness, fx)
            Drag.NONE -> {
            }
        }
    }

    private enum class Drag {
        NONE,
        SV,
        HUE,
        ALPHA,
    }

    companion object {
        @Deprecated("")
        const val SV_HEIGHT: Float = 86f

        @Deprecated("")
        const val BAR_HEIGHT: Float = 10f

        @Deprecated("")
        const val GAP: Float = 7f

        @Deprecated("")
        const val EXTRA: Float = 134f
    }
}
