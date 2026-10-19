package me.enivzoy.cosmeticblocksreborn.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.HashMap
import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab
import net.fabricmc.loader.api.FabricLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Config private constructor() {
   val panelState = HashMap<String, PanelState>()

   private fun getConfigFile(): Path = FabricLoader.getInstance().configDir.resolve("cb").resolve("cb-ui.json")

   fun load() {
      val file = this.getConfigFile()
      if (Files.exists(file)) {
         try {
            val root: JsonElement = Files.newBufferedReader(file).use { reader -> JsonParser.parseReader(reader) }
            if (!root.isJsonObject) {
               return
            }

            val json = root.asJsonObject
            val tabsElement = json.get("tabs")
            if (tabsElement is JsonObject) {
               DungeonConfigTab.beginSync()
               try {
                  for (tab in Tabs.all) {
                     val tabElement = tabsElement.get(tab.name)
                     val source: JsonObject
                     if (tabElement is JsonObject) {
                        source = tabElement
                     } else {
                        source = JsonObject()
                        val rules = legacyMigration[tab.name]
                        if (rules != null) {
                           for (rule in rules) {
                              val legacyTabElement = tabsElement.get(rule[1])
                              if (legacyTabElement is JsonObject && legacyTabElement.has(rule[2])) {
                                 source.add(rule[0], legacyTabElement.get(rule[2]))
                              }
                           }
                        }
                     }

                     for (setting in tab.settings) {
                        setting.readJson(source)
                     }
                  }
               } finally {
                  DungeonConfigTab.endSync()
               }
            }

            val panelsElement = json.get("panels")
            if (panelsElement is JsonObject) {
               for (entry in panelsElement.entrySet()) {
                  val name = entry.key
                  val element = entry.value
                  if (element is JsonObject) {
                     val xElement = element.get("x")
                     val x = if (xElement != null) xElement.asFloat else 0.0f
                     val yElement = element.get("y")
                     val y = if (yElement != null) yElement.asFloat else 0.0f
                     val collapsedElement = element.get("collapsed")
                     panelState[name] = PanelState(
                        x, y, collapsedElement != null && collapsedElement.asBoolean
                     )
                  }
               }
            }

            logger.info("Loaded config from {}", file)
         } catch (t: Throwable) {
            logger.error("Failed to load config \u00e2\u20ac\u201d keeping defaults", t)
         }
      }
   }

   fun save() {
      try {
         val json = JsonObject()
         val tabsJson = JsonObject()

         for (tab in Tabs.all) {
            val tabJson = JsonObject()

            for (setting in tab.settings) {
               setting.writeJson(tabJson)
            }

            tabsJson.add(tab.name, tabJson)
         }

         json.add("tabs", tabsJson)
         val panelsJson = JsonObject()

         for ((name, state) in panelState) {
            val panel = JsonObject()
            panel.addProperty("x", state.x)
            panel.addProperty("y", state.y)
            panel.addProperty("collapsed", state.collapsed)
            panelsJson.add(name, panel)
         }

         json.add("panels", panelsJson)
         val file = this.getConfigFile()
         Files.createDirectories(file.parent)
         val temp = file.resolveSibling("cb-ui.json.tmp")
         Files.newBufferedWriter(temp).use { writer -> gson.toJson(json, writer) }
         Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
      } catch (t: Throwable) {
         logger.error("Failed to save config", t)
      }
   }

   data class PanelState(var x: Float, var y: Float, var collapsed: Boolean)

   companion object {
      private val logger: Logger = LoggerFactory.getLogger("cb/config")
      private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
      private val legacyMigration = HashMap<String, Array<Array<String>>>()

      init {
         legacyMigration["Settings"] = arrayOf(
            arrayOf("Auto-load Floor Config", "Cosmetic Blocks", "Auto-load Floor Config")
         )
         legacyMigration["Global Cosmetics"] = arrayOf(
            arrayOf("Allow Water Color", "Cosmetic Blocks", "Custom Water Color"),
            arrayOf("RGB Water Color", "Cosmetic Blocks", "Water Color"),
            arrayOf("Lava as Water", "Cosmetic Blocks", "Lava as Water"),
            arrayOf("Enabled", "Block Remaps", "Enabled"),
            arrayOf("Keep Block Shape", "Block Remaps", "Keep Block Shape")
         )
         legacyMigration["Dungeon Config"] = arrayOf(
            arrayOf("Active Config", "Cosmetic Blocks", "Active Config"),
            arrayOf("Config Floor", "Cosmetic Blocks", "Config Floor"),
            arrayOf("Allow Water Color", "Cosmetic Blocks", "Custom Water Color"),
            arrayOf("RGB Water Color", "Cosmetic Blocks", "Water Color"),
            arrayOf("Lava as Water", "Cosmetic Blocks", "Lava as Water")
         )
      }

      @JvmField
      val INSTANCE: Config = Config()
   }
}
