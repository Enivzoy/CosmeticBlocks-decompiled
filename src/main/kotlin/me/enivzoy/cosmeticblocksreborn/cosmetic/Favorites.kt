package me.enivzoy.cosmeticblocksreborn.cosmetic

import java.io.File

object Favorites {
   private val favoritesFile = File("config/cb/cb_favorites.txt")
   private val favorites = mutableListOf<String>()

   fun loadFavorites() {
      favorites.clear()
      if (!favoritesFile.exists()) {
         return
      }

      try {
         for (line in favoritesFile.readLines(Charsets.UTF_8)) {
            val trimmed = line.trim()
            if (trimmed.isNotEmpty()) {
               favorites.add(trimmed)
            }
         }
      } catch (_: Exception) {
      }
   }

   fun saveFavorites() {
      val snapshot = ArrayList(favorites)
      SaveScheduler.saveExecutor.execute { saveSnapshot(snapshot) }
   }

   fun addFavorite(blockId: String): Boolean {
      val normalized = BlockStateCodec.normalizeBlockId(blockId)
      if (BlockStateCodec.resolveBlockState(normalized) == null) {
         return false
      }

      if (favorites.contains(normalized)) {
         return false
      }

      favorites.add(normalized)
      this.saveFavorites()
      return true
   }

   fun removeFavorite(blockId: String): Boolean {
      val normalized = BlockStateCodec.normalizeBlockId(blockId)
      val removed = favorites.remove(normalized)
      if (removed) {
         this.saveFavorites()
      }

      return removed
   }

   fun getFavorites(): MutableList<String> = ArrayList(favorites)

   fun isFavorite(blockId: String): Boolean = favorites.contains(BlockStateCodec.normalizeBlockId(blockId))

   private fun saveSnapshot(snapshot: List<String>) {
      try {
         favoritesFile.parentFile?.mkdirs()
         favoritesFile.writeText(snapshot.joinToString("\n"), Charsets.UTF_8)
      } catch (_: Exception) {
      }
   }
}
