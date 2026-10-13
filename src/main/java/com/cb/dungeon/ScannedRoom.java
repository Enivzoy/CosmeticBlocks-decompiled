package com.cb.dungeon;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0004\b\u000b\u0010\fJ\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0007HÆ\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\tHÆ\u0003J7\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tHÆ\u0001J\u0014\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010!\u001a\u00020\"HÖ\u0081\u0004J\n\u0010#\u001a\u00020$HÖ\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006%",
   d2 = {
         "Lcom/cb/dungeon/ScannedRoom;",
         "",
         "data",
         "Lcom/cb/dungeon/RoomInfo;",
         "rotation",
         "Lcom/cb/dungeon/Rotation;",
         "clayPos",
         "Lnet/minecraft/core/BlockPos;",
         "components",
         "",
         "Lcom/cb/dungeon/Vec2i;",
         "<init>",
         "(Lcom/cb/dungeon/RoomInfo;Lcom/cb/dungeon/Rotation;Lnet/minecraft/core/BlockPos;Ljava/util/Set;)V",
         "getData",
         "()Lcom/cb/dungeon/RoomInfo;",
         "getRotation",
         "()Lcom/cb/dungeon/Rotation;",
         "setRotation",
         "(Lcom/cb/dungeon/Rotation;)V",
         "getClayPos",
         "()Lnet/minecraft/core/BlockPos;",
         "setClayPos",
         "(Lnet/minecraft/core/BlockPos;)V",
         "getComponents",
         "()Ljava/util/Set;",
         "component1",
         "component2",
         "component3",
         "component4",
         "copy",
         "equals",
         "",
         "other",
         "hashCode",
         "",
         "toString",
         "",
         "cb_client"
   }
)
public final class ScannedRoom {
   @NotNull
   private final RoomInfo data;
   @NotNull
   private Rotation rotation;
   @NotNull
   private BlockPos clayPos;
   @NotNull
   private final Set<Vec2i> components;

   public ScannedRoom(@NotNull RoomInfo data, @NotNull Rotation rotation, @NotNull BlockPos clayPos, @NotNull Set<Vec2i> components) {
      Intrinsics.checkNotNullParameter(data, "data");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      Intrinsics.checkNotNullParameter(clayPos, "clayPos");
      Intrinsics.checkNotNullParameter(components, "components");
      super();
      this.data = data;
      this.rotation = rotation;
      this.clayPos = clayPos;
      this.components = components;
   }

   @NotNull
   public final RoomInfo getData() {
      return this.data;
   }

   @NotNull
   public final Rotation getRotation() {
      return this.rotation;
   }

   public final void setRotation(@NotNull Rotation var1) {
      Intrinsics.checkNotNullParameter(var1/* $VF was: <set-?> */, "<set-?>");
      this.rotation = var1/* $VF was: <set-?> */;
   }

   @NotNull
   public final BlockPos getClayPos() {
      return this.clayPos;
   }

   public final void setClayPos(@NotNull BlockPos var1) {
      Intrinsics.checkNotNullParameter(var1/* $VF was: <set-?> */, "<set-?>");
      this.clayPos = var1/* $VF was: <set-?> */;
   }

   @NotNull
   public final Set<Vec2i> getComponents() {
      return this.components;
   }

   @NotNull
   public final RoomInfo component1() {
      return this.data;
   }

   @NotNull
   public final Rotation component2() {
      return this.rotation;
   }

   @NotNull
   public final BlockPos component3() {
      return this.clayPos;
   }

   @NotNull
   public final Set<Vec2i> component4() {
      return this.components;
   }

   @NotNull
   public final ScannedRoom copy(@NotNull RoomInfo data, @NotNull Rotation rotation, @NotNull BlockPos clayPos, @NotNull Set<Vec2i> components) {
      Intrinsics.checkNotNullParameter(data, "data");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      Intrinsics.checkNotNullParameter(clayPos, "clayPos");
      Intrinsics.checkNotNullParameter(components, "components");
      return new ScannedRoom(data, rotation, clayPos, components);
   }

   @NotNull
   @Override
   public String toString() {
      return "ScannedRoom(data=" + this.data + ", rotation=" + this.rotation + ", clayPos=" + this.clayPos + ", components=" + this.components + ")";
   }

   @Override
   public int hashCode() {
      int result = this.data.hashCode();
      result = result * 31 + this.rotation.hashCode();
      result = result * 31 + this.clayPos.hashCode();
      return result * 31 + this.components.hashCode();
   }

   @Override
   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof ScannedRoom var2)) {
         return false;
      } else if (!Intrinsics.areEqual(this.data, var2.data)) {
         return false;
      } else if (this.rotation != var2.rotation) {
         return false;
      } else {
         return !Intrinsics.areEqual(this.clayPos, var2.clayPos) ? false : Intrinsics.areEqual(this.components, var2.components);
      }
   }
}
