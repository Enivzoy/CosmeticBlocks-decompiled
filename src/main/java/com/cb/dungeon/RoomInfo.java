package com.cb.dungeon;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class RoomInfo {
   @NotNull
   private final String name;
   @NotNull
   private final RoomType type;
   @NotNull
   private final List<Integer> cores;
   private final int secrets;
   @NotNull
   private final RoomShape shape;

   public RoomInfo(@NotNull String name, @NotNull RoomType type, @NotNull List<Integer> cores, int secrets, @NotNull RoomShape shape) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(type, "type");
      Intrinsics.checkNotNullParameter(cores, "cores");
      Intrinsics.checkNotNullParameter(shape, "shape");
      super();
      this.name = name;
      this.type = type;
      this.cores = cores;
      this.secrets = secrets;
      this.shape = shape;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   @NotNull
   public final RoomType getType() {
      return this.type;
   }

   @NotNull
   public final List<Integer> getCores() {
      return this.cores;
   }

   public final int getSecrets() {
      return this.secrets;
   }

   @NotNull
   public final RoomShape getShape() {
      return this.shape;
   }

   @NotNull
   public final String component1() {
      return this.name;
   }

   @NotNull
   public final RoomType component2() {
      return this.type;
   }

   @NotNull
   public final List<Integer> component3() {
      return this.cores;
   }

   public final int component4() {
      return this.secrets;
   }

   @NotNull
   public final RoomShape component5() {
      return this.shape;
   }

   @NotNull
   public final RoomInfo copy(@NotNull String name, @NotNull RoomType type, @NotNull List<Integer> cores, int secrets, @NotNull RoomShape shape) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(type, "type");
      Intrinsics.checkNotNullParameter(cores, "cores");
      Intrinsics.checkNotNullParameter(shape, "shape");
      return new RoomInfo(name, type, cores, secrets, shape);
   }

   @NotNull
   @Override
   public String toString() {
      return "RoomInfo(name=" + this.name + ", type=" + this.type + ", cores=" + this.cores + ", secrets=" + this.secrets + ", shape=" + this.shape + ")";
   }

   @Override
   public int hashCode() {
      int result = this.name.hashCode();
      result = result * 31 + this.type.hashCode();
      result = result * 31 + this.cores.hashCode();
      result = result * 31 + Integer.hashCode(this.secrets);
      return result * 31 + this.shape.hashCode();
   }

   @Override
   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof RoomInfo that)) {
         return false;
      } else if (!Intrinsics.areEqual(this.name, that.name)) {
         return false;
      } else if (this.type != that.type) {
         return false;
      } else if (!Intrinsics.areEqual(this.cores, that.cores)) {
         return false;
      } else {
         return this.secrets != that.secrets ? false : this.shape == that.shape;
      }
   }
}
