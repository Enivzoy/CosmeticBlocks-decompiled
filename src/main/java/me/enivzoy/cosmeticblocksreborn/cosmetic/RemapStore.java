package me.enivzoy.cosmeticblocksreborn.cosmetic;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface RemapStore {
   @NotNull
   List<String> sources();

   @NotNull
   List<GlobalRemap.RemapTarget> targetsOf(@NotNull String source);

   void addTarget(@NotNull String source, @NotNull String targetId, int weight);

   void setWeight(@NotNull String source, int index, int weight);

   void removeTarget(@NotNull String source, int index);

   void removeSource(@NotNull String source);

   void clear();

   @NotNull
   String exportJson();

   boolean importJson(@NotNull String json);
}
