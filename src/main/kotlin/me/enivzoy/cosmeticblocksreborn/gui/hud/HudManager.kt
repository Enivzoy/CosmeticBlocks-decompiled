package me.enivzoy.cosmeticblocksreborn.gui.hud

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.File
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor

object HudManager {
    private val hudElements = ArrayList<HudElement>()
    private val configFile = File("config/cb/hud_positions.json")

    init {
        loadPositions()
    }

    fun registerElement(element: HudElement) {
        hudElements.add(element)
    }

    fun getElements(): List<HudElement> = hudElements

    fun getElement(id: String): HudElement? = hudElements.firstOrNull { it.id == id }

    fun render(graphics: GuiGraphicsExtractor, mc: Minecraft, partialTicks: Float) {
        for (element in hudElements) {
            element.render(graphics, mc, partialTicks)
        }
    }

    fun savePositions() {
        val configDir = File("config/cb")
        if (!configDir.exists()) {
            configDir.mkdirs()
        }
        val json = JsonObject()
        for (element in hudElements) {
            val elementJson = JsonObject()
            elementJson.addProperty("x", element.x)
            elementJson.addProperty("y", element.y)
            elementJson.addProperty("width", element.width)
            elementJson.addProperty("height", element.height)
            json.add(element.id, elementJson)
        }
        try {
            val content = Gson().toJson(json)
            configFile.writeText(content, Charsets.UTF_8)
        } catch (e: Exception) {
            println("CB: Failed to save HUD positions: " + e.message)
        }
    }

    fun loadPositions() {
        if (configFile.exists()) {
            try {
                val json = JsonParser.parseString(configFile.readText(Charsets.UTF_8)).asJsonObject
                for (element in hudElements) {
                    val elementJson = json.getAsJsonObject(element.id)
                    if (elementJson != null) {
                        val x = elementJson.get("x")
                        element.x = if (x != null) x.asInt else element.x
                        val y = elementJson.get("y")
                        element.y = if (y != null) y.asInt else element.y
                        val width = elementJson.get("width")
                        element.width = if (width != null) width.asInt else element.width
                        val height = elementJson.get("height")
                        element.height = if (height != null) height.asInt else element.height
                    }
                }
            } catch (e: Exception) {
                println("CB: Failed to load HUD positions: " + e.message)
            }
        }
    }
}
