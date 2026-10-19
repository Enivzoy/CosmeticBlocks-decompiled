package me.enivzoy.cosmeticblocksreborn.features

import me.enivzoy.cosmeticblocksreborn.events.EventBus
import net.minecraft.client.Minecraft

abstract class Feature(
   val name: String,
   val description: String,
   toggled: Boolean,
   val alwaysActive: Boolean
) {
   private var enabledState: Boolean = toggled

   val enabled: Boolean
      get() = enabledState

   protected val mc: Minecraft
      get() = Minecraft.getInstance()

   init {
      if (this.alwaysActive) {
         EventBus.subscribe(this)
      }
   }

   open fun onEnable() {
      if (!this.alwaysActive) {
         EventBus.subscribe(this)
      }
   }

   open fun onDisable() {
      if (!this.alwaysActive) {
         EventBus.unsubscribe(this)
      }
   }

   fun toggle() {
      this.enabledState = !this.enabledState
      if (this.enabledState) {
         this.onEnable()
      } else {
         this.onDisable()
      }
   }

   fun setEnabled(state: Boolean) {
      if (state != this.enabledState) {
         this.toggle()
      }
   }
}
