package com.cb.cosmetic;

import com.cb.dungeon.RoomScanner;
import com.cb.dungeon.Rotation;
import com.cb.dungeon.ScannedRoom;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0017\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\nH\u0000¢\u0006\u0002\b\u000bJ \u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\n2\b\b\u0002\u0010\u0015\u001a\u00020\u0016J \u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u0013J\u001e\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u001dJ\u0006\u0010 \u001a\u00020\u001dJ\u0006\u0010!\u001a\u00020\u001dJ\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\n0#J\u0006\u0010$\u001a\u00020\u001dJ%\u0010%\u001a\u001a\u0012\u0004\u0012\u00020\n\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0&0&H\u0000¢\u0006\u0002\b'R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R,\u0010\f\u001a\u001a\u0012\u0004\u0012\u00020\n\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\r0\rX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006(",
   d2 = {
         "Lcom/cb/cosmetic/ReplacementStore;",
         "",
         "<init>",
         "()V",
         "configDir",
         "Ljava/io/File;",
         "getConfigDir$cb_client",
         "()Ljava/io/File;",
         "getConfigFile",
         "name",
         "",
         "getConfigFile$cb_client",
         "replacements",
         "",
         "getReplacements$cb_client",
         "()Ljava/util/Map;",
         "setReplacement",
         "",
         "pos",
         "Lnet/minecraft/core/BlockPos;",
         "blockId",
         "phantom",
         "",
         "setReplacementState",
         "state",
         "Lnet/minecraft/world/level/block/state/BlockState;",
         "removeReplacement",
         "removeAt",
         "x",
         "",
         "y",
         "z",
         "clearDimension",
         "clearAll",
         "listReplacements",
         "",
         "getReplacementCount",
         "snapshotReplacements",
         "",
         "snapshotReplacements$cb_client",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nReplacementStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReplacementStore.kt\ncom/cb/cosmetic/ReplacementStore\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,183:1\n410#2,7:184\n410#2,7:191\n1586#3:198\n1661#3,3:199\n1586#3:202\n1661#3,3:203\n*S KotlinDebug\n*F\n+ 1 ReplacementStore.kt\ncom/cb/cosmetic/ReplacementStore\n*L\n30#1:184,7\n66#1:191,7\n154#1:198\n154#1:199,3\n160#1:202\n160#1:203,3\n*E\n"
)
public final class ReplacementStore {
   @NotNull
   public static final ReplacementStore INSTANCE = new ReplacementStore();
   @NotNull
   private static final File configDir = new File("config/cb/cb");
   @NotNull
   private static final Map<String, Map<String, String>> replacements = new LinkedHashMap<>();

   private ReplacementStore() {
   }

   @NotNull
   public final File getConfigDir$cb_client() {
      return configDir;
   }

   @NotNull
   public final File getConfigFile$cb_client(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      return new File(configDir, name + ".json");
   }

   @NotNull
   public final Map<String, Map<String, String>> getReplacements$cb_client() {
      return replacements;
   }

   public final void setReplacement(@NotNull BlockPos pos, @NotNull String blockId, boolean phantom) {
      Intrinsics.checkNotNullParameter(pos, "pos");
      Intrinsics.checkNotNullParameter(blockId, "blockId");
      ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
      if (LocationTracker.INSTANCE.getInDungeon() && room != null && room.getRotation() != Rotation.NONE) {
         RoomStore.INSTANCE.setRoomReplacement$cb_client(pos, BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(blockId), phantom, room);
      } else {
         String dim = LocationTracker.INSTANCE.getCurrentDimension();
         if (dim != null) {
            Map byDim = replacements;
            Object dimKey = dim;
            Object existing = byDim.get(dimKey);
            Object dimMapValue;
            if (existing == null) {
               Object created = new LinkedHashMap();
               byDim.put(dimKey, created);
               dimMapValue = created;
            } else {
               dimMapValue = existing;
            }

            Map dimMap = (Map)dimMapValue;
            String key = pos.getX() + "," + pos.getY() + "," + pos.getZ();
            String normalizedId = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(blockId);
            String storeId = EditMode.INSTANCE.getCurrentShape() != null ? normalizedId + "|shape=" + EditMode.INSTANCE.getCurrentShape() : normalizedId;
            dimMap.put(key, phantom ? "!" + storeId : storeId);
            long packed = BlockPos.asLong(pos.getX(), pos.getY(), pos.getZ());
            if (EditMode.INSTANCE.getCurrentShape() != null) {
               BlockStateCodec codec = BlockStateCodec.INSTANCE;
               String shape = EditMode.INSTANCE.getCurrentShape();
               Intrinsics.checkNotNull(shape);
               BlockState shapeState = codec.resolveShapeState$cb_client(shape);
               BlockState textureState = BlockStateCodec.INSTANCE.resolveBlockState(normalizedId);
               if (shapeState != null && textureState != null) {
                  ReplacementCache.INSTANCE.getActiveCache$cb_client().put(Long.valueOf(packed), shapeState);
                  ReplacementCache.INSTANCE.getRetextureCache$cb_client().put(Long.valueOf(packed), textureState);
                  ReplacementCache.INSTANCE.getShapedPositions$cb_client().add(Long.valueOf(packed));
               }
            } else {
               BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(normalizedId);
               if (state != null) {
                  ReplacementCache.INSTANCE.getActiveCache$cb_client().put(Long.valueOf(packed), state);
               }

               ReplacementCache.INSTANCE.getShapedPositions$cb_client().remove(Long.valueOf(packed));
               ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(Long.valueOf(packed));
            }

            if (phantom) {
               ReplacementCache.INSTANCE.getPhantomPositions$cb_client().add(Long.valueOf(packed));
            } else {
               ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(Long.valueOf(packed));
            }

            DirtySections.INSTANCE.markDirty$cb_client(pos);
            SaveScheduler.INSTANCE.scheduleSave$cb_client();
         }
      }
   }

   public final void setReplacementState(@NotNull BlockPos pos, @NotNull BlockState state, boolean phantom) {
      Intrinsics.checkNotNullParameter(pos, "pos");
      Intrinsics.checkNotNullParameter(state, "state");
      ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
      if (LocationTracker.INSTANCE.getInDungeon() && room != null && room.getRotation() != Rotation.NONE) {
         RoomStore.INSTANCE.setRoomReplacementState$cb_client(pos, state, phantom, room);
      } else {
         String dim = LocationTracker.INSTANCE.getCurrentDimension();
         if (dim != null) {
            Map byDim = replacements;
            Object dimKey = dim;
            Object existing = byDim.get(dimKey);
            Object dimMapValue;
            if (existing == null) {
               Object created = new LinkedHashMap();
               byDim.put(dimKey, created);
               dimMapValue = created;
            } else {
               dimMapValue = existing;
            }

            Map dimMap = (Map)dimMapValue;
            String key = pos.getX() + "," + pos.getY() + "," + pos.getZ();
            String serialized = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(state);
            dimMap.put(key, phantom ? "!" + serialized : serialized);
            long packed = BlockPos.asLong(pos.getX(), pos.getY(), pos.getZ());
            ReplacementCache.INSTANCE.getActiveCache$cb_client().put(packed, state);
            if (phantom) {
               ReplacementCache.INSTANCE.getPhantomPositions$cb_client().add(packed);
            } else {
               ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(packed);
            }

            DirtySections.INSTANCE.markDirty$cb_client(pos);
            SaveScheduler.INSTANCE.scheduleSave$cb_client();
         }
      }
   }

   public final boolean removeReplacement(@NotNull BlockPos pos) {
      Intrinsics.checkNotNullParameter(pos, "pos");
      ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
      if (LocationTracker.INSTANCE.getInDungeon() && room != null && room.getRotation() != Rotation.NONE) {
         return RoomStore.INSTANCE.removeRoomReplacement$cb_client(pos, room);
      }

      String dim = LocationTracker.INSTANCE.getCurrentDimension();
      if (dim == null) {
         return false;
      }

      Map dimMap = replacements.get(dim);
      if (dimMap == null) {
         return false;
      }

      String key = pos.getX() + "," + pos.getY() + "," + pos.getZ();
      boolean removed = dimMap.remove(key) != null;
      if (removed) {
         if (dimMap.isEmpty()) {
            replacements.remove(dim);
         }

         long packed = BlockPos.asLong(pos.getX(), pos.getY(), pos.getZ());
         ReplacementCache.INSTANCE.getActiveCache$cb_client().remove(packed);
         ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(packed);
         ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
         ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
         ReplacementCache.INSTANCE.getShapedPositions$cb_client().remove(packed);
         DirtySections.INSTANCE.markDirtyImmediate$cb_client(pos);
         SaveScheduler.INSTANCE.scheduleSave$cb_client();
      }

      return removed;
   }

   public final boolean removeAt(int x, int y, int z) {
      return this.removeReplacement(new BlockPos(x, y, z));
   }

   public final int clearDimension() {
      ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
      if (LocationTracker.INSTANCE.getInDungeon() && room != null && room.getRotation() != Rotation.NONE) {
         return RoomStore.INSTANCE.clearRoom(room.getData().getName());
      }

      String dim = LocationTracker.INSTANCE.getCurrentDimension();
      if (dim == null) {
         return 0;
      }

      Map<String, String> dimMap = replacements.remove(dim);
      if (dimMap == null) {
         return 0;
      }

      int count = dimMap.size();

      for (String key : dimMap.keySet()) {
         String[] delimiters = new String[]{","};
         List parts = StringsKt.split(key, delimiters, false, 0);
         if (parts.size() == 3) {
            try {
               DirtySections.INSTANCE
                  .markDirty$cb_client(
                     new BlockPos(Integer.parseInt((String)parts.get(0)), Integer.parseInt((String)parts.get(1)), Integer.parseInt((String)parts.get(2)))
                  );
            } catch (NumberFormatException ignored) {
            }
         }
      }

      ReplacementCache.INSTANCE.getActiveCache$cb_client().clear();
      ReplacementCache.INSTANCE.getPhantomPositions$cb_client().clear();
      ReplacementCache.INSTANCE.getRetextureCache$cb_client().clear();
      ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().clear();
      ReplacementIO.INSTANCE.save();
      return count;
   }

   public final int clearAll() {
      int count = 0;

      for (Entry<String, Map<String, String>> entry : replacements.entrySet()) {
         Map<String, String> dimMap = entry.getValue();
         count += dimMap.size();

         for (String key : dimMap.keySet()) {
            String[] delimiters = new String[]{","};
            List parts = StringsKt.split(key, delimiters, false, 0);
            if (parts.size() == 3) {
               try {
                  DirtySections.INSTANCE
                     .markDirty$cb_client(
                        new BlockPos(Integer.parseInt((String)parts.get(0)), Integer.parseInt((String)parts.get(1)), Integer.parseInt((String)parts.get(2)))
                     );
               } catch (NumberFormatException ignored) {
               }
            }
         }
      }

      replacements.clear();
      ReplacementCache.INSTANCE.getActiveCache$cb_client().clear();
      ReplacementCache.INSTANCE.getPhantomPositions$cb_client().clear();
      ReplacementCache.INSTANCE.getRetextureCache$cb_client().clear();
      ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().clear();
      ReplacementIO.INSTANCE.save();
      return count;
   }

   @NotNull
   public final List<String> listReplacements() {
      ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
      if (LocationTracker.INSTANCE.getInDungeon() && room != null) {
         Map roomMap = RoomStore.INSTANCE.getRoomBlocks$cb_client().get(room.getData().getName());
         if (roomMap == null) {
            return CollectionsKt.emptyList();
         }

         Iterable entries = roomMap.entrySet();
         var result = new ArrayList(CollectionsKt.collectionSizeOrDefault(entries, 10));

         for (Object item : entries) {
            Entry entry = (Entry)item;
            String pos = (String)entry.getKey();
            String block = (String)entry.getValue();
            result.add("[" + room.getData().getName() + "] " + pos + " -> " + StringsKt.removePrefix(StringsKt.removePrefix(block, "!"), "minecraft:"));
         }

         return (List<String>)result;
      } else {
         String dim = LocationTracker.INSTANCE.getCurrentDimension();
         if (dim == null) {
            return CollectionsKt.emptyList();
         }

         Map dimMap = replacements.get(dim);
         if (dimMap == null) {
            return CollectionsKt.emptyList();
         }

         Iterable entries = dimMap.entrySet();
         var result = new ArrayList(CollectionsKt.collectionSizeOrDefault(entries, 10));

         for (Object item : entries) {
            Entry entry = (Entry)item;
            String pos = (String)entry.getKey();
            String block = (String)entry.getValue();
            result.add(pos + " -> " + StringsKt.removePrefix(StringsKt.removePrefix(block, "!"), "minecraft:"));
         }

         return (List<String>)result;
      }
   }

   public final int getReplacementCount() {
      ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
      if (LocationTracker.INSTANCE.getInDungeon() && room != null) {
         Map roomMap = RoomStore.INSTANCE.getRoomBlocks$cb_client().get(room.getData().getName());
         return roomMap != null ? roomMap.size() : 0;
      }

      String dim = LocationTracker.INSTANCE.getCurrentDimension();
      if (dim == null) {
         return 0;
      }

      Map dimMap = replacements.get(dim);
      return dimMap != null ? dimMap.size() : 0;
   }

   @NotNull
   public final Map<String, Map<String, String>> snapshotReplacements$cb_client() {
      HashMap snapshot = new HashMap(replacements.size() * 2);

      for (Entry entry : replacements.entrySet()) {
         String dim = (String)entry.getKey();
         Map dimMap = (Map)entry.getValue();
         HashMap copy = new HashMap(dimMap.size() * 2);
         copy.putAll(dimMap);
         snapshot.put(dim, copy);
      }

      return snapshot;
   }
}
