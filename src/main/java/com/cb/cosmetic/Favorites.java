package com.cb.cosmetic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\nJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\bJ\u000e\u0010\u000f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\bJ\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u0011J\u000e\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013",
   d2 = {
         "Lcom/cb/cosmetic/Favorites;",
         "",
         "<init>",
         "()V",
         "favoritesFile",
         "Ljava/io/File;",
         "favorites",
         "",
         "",
         "loadFavorites",
         "",
         "saveFavorites",
         "addFavorite",
         "",
         "blockId",
         "removeFavorite",
         "getFavorites",
         "",
         "isFavorite",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nFavorites.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Favorites.kt\ncom/cb/cosmetic/Favorites\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,54:1\n1915#2,2:55\n*S KotlinDebug\n*F\n+ 1 Favorites.kt\ncom/cb/cosmetic/Favorites\n*L\n17#1:55,2\n*E\n"
)
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
            Iterable $this$forEach$iv = FilesKt.readLines(favoritesFile, kotlin.text.Charsets.UTF_8);
            int $i$f$forEach = 0;

            for (Object element$iv : $this$forEach$iv) {
               String line = (String)element$iv;
               int var6/* $VF was: $i$a$-forEach-Favorites$loadFavorites$1 */ = 0;
               String trimmed = StringsKt.trim(line).toString();
               if (trimmed.length() > 0) {
                  favorites.add(trimmed);
               }
            }
         } catch (Exception var8) {
         }
      }
   }

   public final void saveFavorites() {
      ArrayList snapshot = new ArrayList<>(favorites);
      SaveScheduler.INSTANCE.getSaveExecutor$cb_client().execute(() -> saveFavorites$lambda$0(snapshot));
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

   private static final void saveFavorites$lambda$0(ArrayList $snapshot) {
      try {
         File var1 = favoritesFile.getParentFile();
         if (var1 != null) {
            var1.mkdirs();
         }

         FilesKt.writeText(favoritesFile, CollectionsKt.joinToString($snapshot, "\n", "", "", -1, "...", null), kotlin.text.Charsets.UTF_8);
      } catch (Exception var2) {
      }
   }
}
