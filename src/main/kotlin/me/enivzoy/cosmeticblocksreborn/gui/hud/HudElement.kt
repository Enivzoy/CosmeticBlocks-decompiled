package me.enivzoy.cosmeticblocksreborn.gui.hud

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor

abstract class HudElement(
    val id: String,
    var x: Int,
    var y: Int,
    var width: Int,
    var height: Int,
) {
    var isDragging: Boolean = false
    var dragOffsetX: Int = 0
    var dragOffsetY: Int = 0

    abstract fun render(graphics: GuiGraphicsExtractor, mc: Minecraft, partialTicks: Float)

    open fun renderInEditMode(graphics: GuiGraphicsExtractor, mc: Minecraft) {
        graphics.fill(x, y, x + width, y + height, Int.MIN_VALUE)
        graphics.fill(x, y, x + width, y + 1, -1)
        graphics.fill(x, y + height - 1, x + width, y + height, -1)
        graphics.fill(x, y, x + 1, y + height, -1)
        graphics.fill(x + width - 1, y, x + width, y + height, -1)
        render(graphics, mc, 0f)
    }

    fun contains(mouseX: Int, mouseY: Int): Boolean =
        mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height

    fun startDrag(mouseX: Int, mouseY: Int) {
        isDragging = true
        dragOffsetX = mouseX - x
        dragOffsetY = mouseY - y
    }

    fun updateDrag(mouseX: Int, mouseY: Int, screenWidth: Int, screenHeight: Int) {
        if (isDragging) {
            x = (mouseX - dragOffsetX).coerceIn(0, screenWidth - width)
            y = (mouseY - dragOffsetY).coerceIn(0, screenHeight - height)
        }
    }

    fun stopDrag() {
        isDragging = false
    }

    fun snapToGrid(gridSize: Int) {
        x = x / gridSize * gridSize
        y = y / gridSize * gridSize
    }
}
