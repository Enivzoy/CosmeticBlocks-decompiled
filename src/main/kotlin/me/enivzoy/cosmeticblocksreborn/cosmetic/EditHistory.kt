package me.enivzoy.cosmeticblocksreborn.cosmetic

import me.enivzoy.cosmeticblocksreborn.dungeon.ScannedRoom

object EditHistory {
   private const val MAX_ENTRIES = 64

   internal class Operation(
      val label: String,
      val dimension: String?,
      val room: ScannedRoom?,
      val before: Map<String, String?>,
      val after: Map<String, String?>
   ) {
      val isRoom: Boolean
         get() = this.room != null
   }

   private val undoStack = ArrayDeque<Operation>()
   private val redoStack = ArrayDeque<Operation>()

   internal fun record(operation: Operation) {
      undoStack.addLast(operation)
      while (undoStack.size > MAX_ENTRIES) {
         undoStack.removeFirst()
      }

      redoStack.clear()
   }

   internal fun peekUndo(): Operation? = undoStack.lastOrNull()

   internal fun peekRedo(): Operation? = redoStack.lastOrNull()

   internal fun popUndo(): Operation? {
      val op = undoStack.removeLastOrNull() ?: return null
      redoStack.addLast(op)
      while (redoStack.size > MAX_ENTRIES) {
         redoStack.removeFirst()
      }

      return op
   }

   internal fun popRedo(): Operation? {
      val op = redoStack.removeLastOrNull() ?: return null
      undoStack.addLast(op)
      return op
   }

   internal fun canUndo(): Boolean = undoStack.isNotEmpty()

   internal fun canRedo(): Boolean = redoStack.isNotEmpty()

   internal fun undoDepth(): Int = undoStack.size

   internal fun redoDepth(): Int = redoStack.size

   internal fun clear() {
      undoStack.clear()
      redoStack.clear()
   }
}
