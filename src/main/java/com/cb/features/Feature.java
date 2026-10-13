package com.cb.features;

import com.cb.events.EventBus;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tJ\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0017H\u0016J\u0006\u0010\u0019\u001a\u00020\u0017J\u000e\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000e¨\u0006\u001c",
   d2 = {
         "Lcom/cb/features/Feature;",
         "",
         "name",
         "",
         "description",
         "toggled",
         "",
         "alwaysActive",
         "<init>",
         "(Ljava/lang/String;Ljava/lang/String;ZZ)V",
         "getName",
         "()Ljava/lang/String;",
         "getDescription",
         "getAlwaysActive",
         "()Z",
         "mc",
         "Lnet/minecraft/client/Minecraft;",
         "getMc",
         "()Lnet/minecraft/client/Minecraft;",
         "value",
         "enabled",
         "getEnabled",
         "onEnable",
         "",
         "onDisable",
         "toggle",
         "setEnabled",
         "state",
         "cb_client"
   }
)
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
      Minecraft var10000 = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(var10000, "getInstance(...)");
      return var10000;
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
