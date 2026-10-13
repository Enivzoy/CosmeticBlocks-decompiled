package com.cb.features;

import com.cb.events.EventBus;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

public abstract class Feature {
   @NotNull
   private final String name;
   @NotNull
   private final String description;
   private final boolean alwaysActive;
   private boolean enabled;

   public Feature(@NotNull String name, @NotNull String description, boolean toggled, boolean alwaysActive) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      super();
      this.name = name;
      this.description = description;
      this.alwaysActive = alwaysActive;
      this.enabled = toggled;
      if (this.alwaysActive) {
         EventBus.INSTANCE.subscribe(this);
      }
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   @NotNull
   public final String getDescription() {
      return this.description;
   }

   public final boolean getAlwaysActive() {
      return this.alwaysActive;
   }

   @NotNull
   protected final Minecraft getMc() {
      Minecraft minecraft = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(minecraft, "getInstance(...)");
      return minecraft;
   }

   public final boolean getEnabled() {
      return this.enabled;
   }

   public void onEnable() {
      if (!this.alwaysActive) {
         EventBus.INSTANCE.subscribe(this);
      }
   }

   public void onDisable() {
      if (!this.alwaysActive) {
         EventBus.INSTANCE.unsubscribe(this);
      }
   }

   public final void toggle() {
      this.enabled = !this.enabled;
      if (this.enabled) {
         this.onEnable();
      } else {
         this.onDisable();
      }
   }

   public final void setEnabled(boolean state) {
      if (state != this.enabled) {
         this.toggle();
      }
   }
}
