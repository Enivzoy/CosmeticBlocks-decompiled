package me.enivzoy.cosmeticblocksreborn.gui.hud

import me.enivzoy.cosmeticblocksreborn.cosmetic.ReplacementCache
import me.enivzoy.cosmeticblocksreborn.cosmetic.ReplacementStore
import me.enivzoy.cosmeticblocksreborn.cosmetic.SaveScheduler
import me.enivzoy.cosmeticblocksreborn.cosmetic.SectionIndex
import me.enivzoy.cosmeticblocksreborn.render.BlockIconStore

object DebugStats {
   @Volatile
   var enabled: Boolean = false

   @Volatile
   private var lastSaveStart = 0L

   @Volatile
   private var lastSaveEnd = 0L

   @Volatile
   private var lastSaveMillis = 0L

   @Volatile
   private var saveCount = 0L

   fun noteSaveStart() {
      lastSaveStart = System.currentTimeMillis()
   }

   fun noteSaveEnd() {
      lastSaveEnd = System.currentTimeMillis()
      if (lastSaveStart > 0L) {
         lastSaveMillis = lastSaveEnd - lastSaveStart
         saveCount++
      }
   }

   fun lines(): List<String> {
      val lines = ArrayList<String>()
      lines.add("replacement count: " + ReplacementStore.countReplacements())
      lines.add("active cache: " + ReplacementCache.activeCache.size)
      lines.add("sections: " + SectionIndex.sectionCount() + " (" + SectionIndex.entryCount() + " entries)")
      lines.add("section index version: " + SectionIndex.currentVersion())
      lines.add("phantom: " + ReplacementCache.phantomPositions.size)
      lines.add("retexture: " + ReplacementCache.retextureCache.size)
      lines.add("shaped: " + ReplacementCache.shapedPositions.size)
      lines.add("needsSave: " + SaveScheduler.needsSave + " inFlight: " + SaveScheduler.saveInFlight)
      lines.add("last save: " + lastSaveMillis + " ms (total " + saveCount + ")")
      lines.add("icons registered: " + BlockIconStore.registeredCount())
      lines.add("queued dirty: " + if (me.enivzoy.cosmeticblocksreborn.cosmetic.DirtySections.hasPending()) "yes" else "no")
      return lines
   }
}
