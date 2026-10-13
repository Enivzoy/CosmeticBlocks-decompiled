package com.cb.cosmetic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class Favorites {
   @NotNull
   public static final Favorites INSTANCE = new Favorites();
   @NotNull
   private static final File favoritesFile = new File("config/cb/cb_favorites.txt");
   @NotNull
   private static final List<String> favorites = new ArrayList<>();

   private Favorites() {
   }

   public final void loadFavorites() {
      favorites.clear();
      if (favoritesFile.exists()) {
         try {
            Iterable lines = FilesKt.readLines(favoritesFile, kotlin.text.Charsets.UTF_8);

            for (Object element : lines) {
               String line = (String)element;
               int inlineMarker = 0;
               String trimmed = StringsKt.trim(line).toString();
               if (trimmed.length() > 0) {
                  favorites.add(trimmed);
               }
            }
         } catch (Exception ignored) {
         }
      }
   }

   public final void saveFavorites() {
      ArrayList snapshot = new ArrayList<>(favorites);
      SaveScheduler.INSTANCE.getSaveExecutor$cb_client().execute(() -> saveSnapshot(snapshot));
   }

   public final boolean addFavorite(@NotNull String blockId) {
      Intrinsics.checkNotNullParameter(blockId, "blockId");
      String normalized = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(blockId);
      if (BlockStateCodec.INSTANCE.resolveBlockState(normalized) == null) {
         return false;
      }

      if (favorites.contains(normalized)) {
         return false;
      }

      favorites.add(normalized);
      this.saveFavorites();
      return true;
   }

   public final boolean removeFavorite(@NotNull String blockId) {
      Intrinsics.checkNotNullParameter(blockId, "blockId");
      String normalized = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(blockId);
      boolean removed = favorites.remove(normalized);
      if (removed) {
         this.saveFavorites();
      }

      return removed;
   }

   @NotNull
   public final List<String> getFavorites() {
      return new ArrayList<>(favorites);
   }

   public final boolean isFavorite(@NotNull String blockId) {
      Intrinsics.checkNotNullParameter(blockId, "blockId");
      return favorites.contains(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(blockId));
   }

   private static final void saveSnapshot(ArrayList snapshot) {
      try {
         File parent = favoritesFile.getParentFile();
         if (parent != null) {
            parent.mkdirs();
         }

         FilesKt.writeText(favoritesFile, CollectionsKt.joinToString(snapshot, "\n", "", "", -1, "...", null), kotlin.text.Charsets.UTF_8);
      } catch (Exception ignored) {
      }
   }
}
