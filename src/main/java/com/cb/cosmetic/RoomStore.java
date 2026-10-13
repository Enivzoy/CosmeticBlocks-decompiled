package com.cb.cosmetic;

import com.cb.dungeon.RoomDataKt;
import com.cb.dungeon.RoomInfo;
import com.cb.dungeon.RoomScanner;
import com.cb.dungeon.Rotation;
import com.cb.dungeon.ScannedRoom;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001DB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0018\u001a\u00020\u0019J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\nH\u0002J\r\u0010\u001d\u001a\u00020\u0019H\u0000¢\u0006\u0002\b\u001eJ\u0006\u0010\u001f\u001a\u00020\u0019J \u0010 \u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060!0!H\u0002J(\u0010\"\u001a\u00020\u00192\u001e\u0010#\u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060!0!H\u0002J\u0017\u0010$\u001a\u00020\u00192\b\u0010%\u001a\u0004\u0018\u00010&H\u0000¢\u0006\u0002\b'J\u0015\u0010(\u001a\u00020\u00192\u0006\u0010)\u001a\u00020\u0006H\u0000¢\u0006\u0002\b*J\u0006\u0010+\u001a\u00020\u0019J-\u0010,\u001a\u00020\u00192\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u001b2\u0006\u00101\u001a\u00020&H\u0000¢\u0006\u0002\b2J-\u00103\u001a\u00020\u00192\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u001b2\u0006\u00101\u001a\u00020&H\u0000¢\u0006\u0002\b4J\u001f\u00105\u001a\u0004\u0018\u00010\u00062\u0006\u0010-\u001a\u00020.2\u0006\u00101\u001a\u00020&H\u0000¢\u0006\u0002\b6J+\u00107\u001a\u00020\u00192\u0014\u0010#\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060!2\u0006\u00101\u001a\u00020&H\u0000¢\u0006\u0002\b8J-\u00109\u001a\u00020\u00192\u0006\u0010-\u001a\u00020.2\u0006\u0010:\u001a\u00020;2\u0006\u00100\u001a\u00020\u001b2\u0006\u00101\u001a\u00020&H\u0000¢\u0006\u0002\b<J\u001d\u0010=\u001a\u00020\u001b2\u0006\u0010-\u001a\u00020.2\u0006\u00101\u001a\u00020&H\u0000¢\u0006\u0002\b>J\u000e\u0010?\u001a\u00020@2\u0006\u0010)\u001a\u00020\u0006J\u0006\u0010A\u001a\u00020@J\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00060\u0016J\u000e\u0010C\u001a\u00020@2\u0006\u0010)\u001a\u00020\u0006R,\u0010\u0004\u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00050\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R \u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006E",
   d2 = {
         "Lcom/cb/cosmetic/RoomStore;",
         "",
         "<init>",
         "()V",
         "roomBlocks",
         "",
         "",
         "getRoomBlocks$cb_client",
         "()Ljava/util/Map;",
         "roomBlocksFile",
         "Ljava/io/File;",
         "roomCacheKeys",
         "",
         "",
         "getRoomCacheKeys$cb_client",
         "()Ljava/util/Set;",
         "lastScannedRoom",
         "getLastScannedRoom$cb_client",
         "()Ljava/lang/String;",
         "setLastScannedRoom$cb_client",
         "(Ljava/lang/String;)V",
         "resolvedRoomCache",
         "",
         "Lcom/cb/cosmetic/RoomStore$ResolvedRoomBlock;",
         "loadRoomBlocks",
         "",
         "tryLoadRoomFile",
         "",
         "file",
         "saveRoomBlocksAsync",
         "saveRoomBlocksAsync$cb_client",
         "forceRoomSave",
         "snapshotRoomBlocks",
         "",
         "writeRoomConfig",
         "snapshot",
         "onRoomChanged",
         "newRoom",
         "Lcom/cb/dungeon/ScannedRoom;",
         "onRoomChanged$cb_client",
         "resolveRoomBlocks",
         "roomName",
         "resolveRoomBlocks$cb_client",
         "resolveAllRoomBlocks",
         "setRoomReplacement",
         "pos",
         "Lnet/minecraft/core/BlockPos;",
         "blockId",
         "phantom",
         "room",
         "setRoomReplacement$cb_client",
         "setRoomReplacementRaw",
         "setRoomReplacementRaw$cb_client",
         "rawAtWorld",
         "rawAtWorld$cb_client",
         "restoreRoomFill",
         "restoreRoomFill$cb_client",
         "setRoomReplacementState",
         "state",
         "Lnet/minecraft/world/level/block/state/BlockState;",
         "setRoomReplacementState$cb_client",
         "removeRoomReplacement",
         "removeRoomReplacement$cb_client",
         "clearRoom",
         "",
         "clearAllRooms",
         "listRoomNames",
         "getRoomBlockCount",
         "ResolvedRoomBlock",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nRoomStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RoomStore.kt\ncom/cb/cosmetic/RoomStore\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,361:1\n410#2,7:362\n410#2,7:369\n410#2,7:376\n*S KotlinDebug\n*F\n+ 1 RoomStore.kt\ncom/cb/cosmetic/RoomStore\n*L\n237#1:362,7\n276#1:369,7\n291#1:376,7\n*E\n"
)
public final class RoomStore {
   @NotNull
   public static final RoomStore INSTANCE = new RoomStore();
   @NotNull
   private static final Map<String, Map<String, String>> roomBlocks = new LinkedHashMap<>();
   @NotNull
   private static final File roomBlocksFile = new File("config/cb/cb_rooms.json");
   @NotNull
   private static final Set<Long> roomCacheKeys;
   @Nullable
   private static String lastScannedRoom;
   @NotNull
   private static final Map<String, List<RoomStore.ResolvedRoomBlock>> resolvedRoomCache = new LinkedHashMap<>();

   private RoomStore() {
   }

   @NotNull
   public final Map<String, Map<String, String>> getRoomBlocks$cb_client() {
      return roomBlocks;
   }

   @NotNull
   public final Set<Long> getRoomCacheKeys$cb_client() {
      return roomCacheKeys;
   }

   @Nullable
   public final String getLastScannedRoom$cb_client() {
      return lastScannedRoom;
   }

   public final void setLastScannedRoom$cb_client(@Nullable String var1) {
      lastScannedRoom = var1/* $VF was: <set-?> */;
   }

   public final void loadRoomBlocks() {
      roomBlocks.clear();
      resolvedRoomCache.clear();
      File tmpFile = new File(roomBlocksFile.getParentFile(), roomBlocksFile.getName() + ".tmp");
      boolean loaded = this.tryLoadRoomFile(roomBlocksFile) || this.tryLoadRoomFile(tmpFile);
      if (!loaded && (roomBlocksFile.exists() || tmpFile.exists())) {
         System.err.println("[CB] Failed to load room blocks");
      }
   }

   private final boolean tryLoadRoomFile(File file) {
      if (!file.exists()) {
         return false;
      }

      boolean text;
      try {
         String textx = FilesKt.readText(file, kotlin.text.Charsets.UTF_8);
         if (StringsKt.isBlank(textx)) {
            return false;
         }

         JsonObject root = JsonParser.parseString(textx).getAsJsonObject();

         for (Entry var5 : root.entrySet()) {
            Intrinsics.checkNotNull(var5);
            String roomName = (String)var5.getKey();
            JsonElement element = (JsonElement)var5.getValue();
            JsonObject roomObj = element.getAsJsonObject();
            Map roomMap = new LinkedHashMap();

            for (Entry var11 : roomObj.entrySet()) {
               Intrinsics.checkNotNull(var11);
               String pos = (String)var11.getKey();
               JsonElement block = (JsonElement)var11.getValue();
               roomMap.put(pos, block.getAsString());
            }

            roomBlocks.put(roomName, roomMap);
         }

         text = true;
      } catch (Exception e) {
         System.err.println("[CB] Failed to parse room file " + file.getName() + ": " + e.getMessage());
         text = false;
      }

      return text;
   }

   public final void saveRoomBlocksAsync$cb_client() {
      SaveScheduler.INSTANCE.setNeedsRoomSave$cb_client(false);
      SaveScheduler.INSTANCE.setLastRoomSaveTime$cb_client(System.currentTimeMillis());
      Map snapshot = this.snapshotRoomBlocks();
      SaveScheduler.INSTANCE.getSaveExecutor$cb_client().execute(() -> saveRoomBlocksAsync$lambda$0(snapshot));
   }

   public final void forceRoomSave() {
      SaveScheduler.INSTANCE.setNeedsRoomSave$cb_client(false);
      SaveScheduler.INSTANCE.setLastRoomSaveTime$cb_client(System.currentTimeMillis());
      Map snapshot = this.snapshotRoomBlocks();
      SaveScheduler.INSTANCE.getSaveExecutor$cb_client().execute(() -> forceRoomSave$lambda$0(snapshot));
   }

   private final Map<String, Map<String, String>> snapshotRoomBlocks() {
      HashMap snapshot = new HashMap(roomBlocks.size());

      for (Entry var3 : roomBlocks.entrySet()) {
         String room = (String)var3.getKey();
         Map map = (Map)var3.getValue();
         snapshot.put(room, new HashMap(map));
      }

      return snapshot;
   }

   private final void writeRoomConfig(Map<String, ? extends Map<String, String>> snapshot) throws IOException {
      File var10000 = roomBlocksFile.getParentFile();
      if (var10000 != null) {
         var10000.mkdirs();
      }

      File tmpFile = new File(roomBlocksFile.getParentFile(), roomBlocksFile.getName() + ".tmp");
      var var3 = new BufferedWriter(new FileWriter(tmpFile), 65536);
      Throwable var4 = null;

      try {
         BufferedWriter bw = var3;
         int var6/* $VF was: $i$a$-use-RoomStore$writeRoomConfig$1 */ = 0;
         var var7 = new JsonWriter(bw);
         Throwable var8 = null;

         try {
            JsonWriter jw = var7;
            int var10/* $VF was: $i$a$-use-RoomStore$writeRoomConfig$1$1 */ = 0;
            jw.setIndent("  ");
            jw.beginObject();

            for (Entry var12 : snapshot.entrySet()) {
               String room = (String)var12.getKey();
               Map<String, String> map = (Map<String, String>)var12.getValue();
               jw.name(room);
               jw.beginObject();

               for (Entry var16 : map.entrySet()) {
                  String pos = (String)var16.getKey();
                  String block = (String)var16.getValue();
                  jw.name(pos).value(block);
               }

               jw.endObject();
            }

            jw = jw.endObject();
         } catch (Throwable var27) {
            var8 = var27;
            throw var27;
         } finally {
            CloseableKt.closeFinally(var7, var8);
         }
      } catch (Throwable var29) {
         var4 = var29;
         throw var29;
      } finally {
         CloseableKt.closeFinally(var3, var4);
      }

      if (roomBlocksFile.exists()) {
         roomBlocksFile.delete();
      }

      tmpFile.renameTo(roomBlocksFile);
   }

   public final void onRoomChanged$cb_client(@Nullable ScannedRoom newRoom) {
      HashSet dirtySections = new HashSet();
      Iterator resolved = roomCacheKeys.iterator();

      while (resolved.hasNext()) {
         long key = ((Number)resolved.next()).longValue();
         dirtySections.add(DirtySections.INSTANCE.sectionKey$cb_client(BlockPos.getX(key), BlockPos.getY(key), BlockPos.getZ(key)));
         ReplacementCache.INSTANCE.getActiveCache$cb_client().remove(key);
         ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(key);
         ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(key);
         ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(key);
         ReplacementCache.INSTANCE.getShapedPositions$cb_client().remove(key);
      }

      roomCacheKeys.clear();
      if (newRoom != null && newRoom.getRotation() != Rotation.NONE) {
         List<RoomStore.ResolvedRoomBlock> resolvedx = resolvedRoomCache.get(newRoom.getData().getName());
         if (resolvedx != null) {
            Rotation rot = newRoom.getRotation();

            for (RoomStore.ResolvedRoomBlock entry : resolvedx) {
               BlockPos var10000 = RoomDataKt.rotateAroundNorth(entry.getRelPos(), rot).offset(newRoom.getClayPos().getX(), 0, newRoom.getClayPos().getZ());
               Intrinsics.checkNotNullExpressionValue(var10000, "offset(...)");
               BlockPos absPos = var10000;
               long packed = BlockPos.asLong(absPos.getX(), absPos.getY(), absPos.getZ());
               if (entry.getShapeState() != null && entry.getTextureState() != null) {
                  ReplacementCache.INSTANCE.getActiveCache$cb_client().put(packed, entry.getShapeState());
                  ReplacementCache.INSTANCE
                     .getRetextureCache$cb_client()
                     .put(packed, BlockStateCodec.INSTANCE.rotateBlockStateFromNorth$cb_client(entry.getTextureState(), rot));
                  ReplacementCache.INSTANCE.getShapedPositions$cb_client().add(packed);
               } else {
                  ReplacementCache.INSTANCE
                     .getActiveCache$cb_client()
                     .put(packed, BlockStateCodec.INSTANCE.rotateBlockStateFromNorth$cb_client(entry.getState(), rot));
               }

               roomCacheKeys.add(packed);
               if (entry.getPhantom()) {
                  ReplacementCache.INSTANCE.getPhantomPositions$cb_client().add(packed);
               }

               dirtySections.add(DirtySections.INSTANCE.sectionKey$cb_client(absPos.getX(), absPos.getY(), absPos.getZ()));
            }
         }

         lastScannedRoom = newRoom.getData().getName();
      } else {
         lastScannedRoom = null;
      }

      DirtySections.INSTANCE.queueDirtySections$cb_client(dirtySections);
   }

   public final void resolveRoomBlocks$cb_client(@NotNull String roomName) {
      Intrinsics.checkNotNullParameter(roomName, "roomName");
      Map<String, String> rawMap = roomBlocks.get(roomName);
      if (rawMap != null && !rawMap.isEmpty()) {
         ArrayList list = new ArrayList(rawMap.size());

         for (Entry var5 : rawMap.entrySet()) {
            String relKey = (String)var5.getKey();
            String raw = (String)var5.getValue();
            CharSequence var10000 = relKey;
            String[] relPos = new String[]{","};
            List parts = StringsKt.split(var10000, relPos, false, 0);
            if (parts.size() == 3) {
               try {
                  BlockPos relPosx = new BlockPos(
                     Integer.parseInt((String)parts.get(0)), Integer.parseInt((String)parts.get(1)), Integer.parseInt((String)parts.get(2))
                  );
                  boolean phantom = StringsKt.startsWith(raw, "!", false);
                  String var21;
                  if (phantom) {
                     var21 = raw.substring(1);
                     Intrinsics.checkNotNullExpressionValue(var21, "substring(...)");
                  } else {
                     var21 = raw;
                  }

                  String cleanRaw = var21;
                  var21 = cleanRaw;
                  String[] blockStr = new String[]{"|shape="};
                  List<String> shapeSplit = StringsKt.split(var21, blockStr, false, 2);
                  String blockStrx = (String)shapeSplit.get(0);
                  String shape = CollectionsKt.getOrNull(shapeSplit, 1);
                  if (shape != null) {
                     BlockState shapeState = BlockStateCodec.INSTANCE.resolveShapeState$cb_client(shape);
                     BlockState textureState = BlockStateCodec.INSTANCE.resolveBlockState(blockStrx);
                     if (shapeState != null && textureState != null) {
                        list.add(new RoomStore.ResolvedRoomBlock(relPosx, shapeState, phantom, shapeState, textureState));
                     }
                  } else {
                     BlockState var23 = BlockStateCodec.INSTANCE.resolveBlockState(blockStrx);
                     if (var23 != null) {
                        BlockState state = var23;
                        list.add(new RoomStore.ResolvedRoomBlock(relPosx, state, phantom, null, null));
                     }
                  }
               } catch (Exception var17) {
               }
            }
         }

         resolvedRoomCache.put(roomName, list);
      } else {
         resolvedRoomCache.remove(roomName);
      }
   }

   public final void resolveAllRoomBlocks() {
      resolvedRoomCache.clear();

      for (String roomName : roomBlocks.keySet()) {
         this.resolveRoomBlocks$cb_client(roomName);
      }
   }

   public final void setRoomReplacement$cb_client(@NotNull BlockPos pos, @NotNull String blockId, boolean phantom, @NotNull ScannedRoom room) {
      Intrinsics.checkNotNullParameter(pos, "pos");
      Intrinsics.checkNotNullParameter(blockId, "blockId");
      Intrinsics.checkNotNullParameter(room, "room");
      this.setRoomReplacementRaw$cb_client(pos, blockId, phantom, room);
      this.resolveRoomBlocks$cb_client(room.getData().getName());
      SaveScheduler.INSTANCE.scheduleRoomSave$cb_client();
   }

   public final void setRoomReplacementRaw$cb_client(@NotNull BlockPos pos, @NotNull String blockId, boolean phantom, @NotNull ScannedRoom room) {
      Intrinsics.checkNotNullParameter(pos, "pos");
      Intrinsics.checkNotNullParameter(blockId, "blockId");
      Intrinsics.checkNotNullParameter(room, "room");
      BlockPos var10000 = pos.subtract(room.getClayPos().atY(0));
      Intrinsics.checkNotNullExpressionValue(var10000, "subtract(...)");
      BlockPos relPos = RoomDataKt.rotateToNorth(var10000, room.getRotation());
      String relKey = relPos.getX() + "," + relPos.getY() + "," + relPos.getZ();
      Map rotatedId = roomBlocks;
      Object key$iv = room.getData().getName();
      int $i$f$getOrPut = 0;
      Object value$iv = rotatedId.get(key$iv);
      Object var20;
      if (value$iv == null) {
         int var12/* $VF was: $i$a$-getOrPut-RoomStore$setRoomReplacementRaw$roomMap$1 */ = 0;
         Object answer$iv = new LinkedHashMap();
         rotatedId.put(key$iv, answer$iv);
         var20 = answer$iv;
      } else {
         var20 = value$iv;
      }

      Map roomMap = (Map)var20;
      String rotatedIdx = BlockStateCodec.INSTANCE.rotateStateToNorth$cb_client(blockId, room.getRotation());
      key$iv = EditMode.INSTANCE.getCurrentShape() != null ? rotatedIdx + "|shape=" + EditMode.INSTANCE.getCurrentShape() : rotatedIdx;
      roomMap.put(relKey, phantom ? "!" + key$iv : key$iv);
      long packed = BlockPos.asLong(pos.getX(), pos.getY(), pos.getZ());
      if (EditMode.INSTANCE.getCurrentShape() != null) {
         BlockStateCodec var21 = BlockStateCodec.INSTANCE;
         String var10001 = EditMode.INSTANCE.getCurrentShape();
         Intrinsics.checkNotNull(var10001);
         BlockState shapeState = var21.resolveShapeState$cb_client(var10001);
         BlockState textureState = BlockStateCodec.INSTANCE.resolveBlockState(blockId);
         if (shapeState != null && textureState != null) {
            ReplacementCache.INSTANCE.getActiveCache$cb_client().put(packed, shapeState);
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().put(packed, textureState);
            ReplacementCache.INSTANCE.getShapedPositions$cb_client().add(packed);
            roomCacheKeys.add(packed);
         }
      } else {
         BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(blockId);
         if (state != null) {
            ReplacementCache.INSTANCE.getActiveCache$cb_client().put(packed, state);
            roomCacheKeys.add(packed);
         }
      }

      if (phantom) {
         ReplacementCache.INSTANCE.getPhantomPositions$cb_client().add(packed);
      } else {
         ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(packed);
      }

      DirtySections.INSTANCE.markDirty$cb_client(pos);
   }

   @Nullable
   public final String rawAtWorld$cb_client(@NotNull BlockPos pos, @NotNull ScannedRoom room) {
      Intrinsics.checkNotNullParameter(pos, "pos");
      Intrinsics.checkNotNullParameter(room, "room");
      BlockPos var10000 = pos.subtract(room.getClayPos().atY(0));
      Intrinsics.checkNotNullExpressionValue(var10000, "subtract(...)");
      BlockPos relPos = RoomDataKt.rotateToNorth(var10000, room.getRotation());
      Map var4 = roomBlocks.get(room.getData().getName());
      return var4 != null ? (String)var4.get(relPos.getX() + "," + relPos.getY() + "," + relPos.getZ()) : null;
   }

   public final void restoreRoomFill$cb_client(@NotNull Map<String, String> snapshot, @NotNull ScannedRoom room) {
      Intrinsics.checkNotNullParameter(snapshot, "snapshot");
      Intrinsics.checkNotNullParameter(room, "room");
      Map $this$getOrPut$iv = roomBlocks;
      Object key$iv = room.getData().getName();
      int $i$f$getOrPut = 0;
      Object value$iv = $this$getOrPut$iv.get(key$iv);
      Object var10000;
      if (value$iv == null) {
         int var8/* $VF was: $i$a$-getOrPut-RoomStore$restoreRoomFill$roomMap$1 */ = 0;
         Object answer$iv = new LinkedHashMap();
         $this$getOrPut$iv.put(key$iv, answer$iv);
         var10000 = answer$iv;
      } else {
         var10000 = value$iv;
      }

      Map roomMap = (Map)var10000;

      for (Entry var10 : snapshot.entrySet()) {
         String relKey = (String)var10.getKey();
         value$iv = (String)var10.getValue();
         if (value$iv == null) {
            roomMap.remove(relKey);
         } else {
            roomMap.put(relKey, value$iv);
         }
      }

      if (roomMap.isEmpty()) {
         roomBlocks.remove(room.getData().getName());
      }

      String var16;
      label30: {
         this.resolveRoomBlocks$cb_client(room.getData().getName());
         ScannedRoom var14 = RoomScanner.INSTANCE.getCurrentRoom();
         if (var14 != null) {
            RoomInfo var15 = var14.getData();
            if (var15 != null) {
               var16 = var15.getName();
               break label30;
            }
         }

         var16 = null;
      }

      if (Intrinsics.areEqual(var16, room.getData().getName())) {
         this.onRoomChanged$cb_client(RoomScanner.INSTANCE.getCurrentRoom());
      }

      SaveScheduler.INSTANCE.scheduleRoomSave$cb_client();
   }

   public final void setRoomReplacementState$cb_client(@NotNull BlockPos pos, @NotNull BlockState state, boolean phantom, @NotNull ScannedRoom room) {
      Intrinsics.checkNotNullParameter(pos, "pos");
      Intrinsics.checkNotNullParameter(state, "state");
      Intrinsics.checkNotNullParameter(room, "room");
      BlockPos var10000 = pos.subtract(room.getClayPos().atY(0));
      Intrinsics.checkNotNullExpressionValue(var10000, "subtract(...)");
      BlockPos relPos = RoomDataKt.rotateToNorth(var10000, room.getRotation());
      String relKey = relPos.getX() + "," + relPos.getY() + "," + relPos.getZ();
      Map serialized = roomBlocks;
      Object key$iv = room.getData().getName();
      int $i$f$getOrPut = 0;
      Object value$iv = serialized.get(key$iv);
      Object var17;
      if (value$iv == null) {
         int var12/* $VF was: $i$a$-getOrPut-RoomStore$setRoomReplacementState$roomMap$1 */ = 0;
         Object answer$iv = new LinkedHashMap();
         serialized.put(key$iv, answer$iv);
         var17 = answer$iv;
      } else {
         var17 = value$iv;
      }

      Map roomMap = (Map)var17;
      String serializedx = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(state);
      key$iv = BlockStateCodec.INSTANCE.rotateStateToNorth$cb_client(serializedx, room.getRotation());
      roomMap.put(relKey, phantom ? "!" + key$iv : key$iv);
      long packed = BlockPos.asLong(pos.getX(), pos.getY(), pos.getZ());
      ReplacementCache.INSTANCE.getActiveCache$cb_client().put(packed, state);
      roomCacheKeys.add(packed);
      if (phantom) {
         ReplacementCache.INSTANCE.getPhantomPositions$cb_client().add(packed);
      } else {
         ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(packed);
      }

      DirtySections.INSTANCE.markDirty$cb_client(pos);
      this.resolveRoomBlocks$cb_client(room.getData().getName());
      SaveScheduler.INSTANCE.scheduleRoomSave$cb_client();
   }

   public final boolean removeRoomReplacement$cb_client(@NotNull BlockPos pos, @NotNull ScannedRoom room) {
      Intrinsics.checkNotNullParameter(pos, "pos");
      Intrinsics.checkNotNullParameter(room, "room");
      BlockPos var10000 = pos.subtract(room.getClayPos().atY(0));
      Intrinsics.checkNotNullExpressionValue(var10000, "subtract(...)");
      BlockPos relPos = RoomDataKt.rotateToNorth(var10000, room.getRotation());
      String relKey = relPos.getX() + "," + relPos.getY() + "," + relPos.getZ();
      Map var9 = roomBlocks.get(room.getData().getName());
      if (var9 == null) {
         return false;
      }

      Map roomMap = var9;
      boolean removed = roomMap.remove(relKey) != null;
      if (removed) {
         if (roomMap.isEmpty()) {
            roomBlocks.remove(room.getData().getName());
         }

         long packed = BlockPos.asLong(pos.getX(), pos.getY(), pos.getZ());
         ReplacementCache.INSTANCE.getActiveCache$cb_client().remove(packed);
         roomCacheKeys.remove(packed);
         ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(packed);
         ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
         ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
         ReplacementCache.INSTANCE.getShapedPositions$cb_client().remove(packed);
         DirtySections.INSTANCE.markDirtyImmediate$cb_client(pos);
         this.resolveRoomBlocks$cb_client(room.getData().getName());
         SaveScheduler.INSTANCE.scheduleRoomSave$cb_client();
      }

      return removed;
   }

   public final int clearRoom(@NotNull String roomName) {
      Intrinsics.checkNotNullParameter(roomName, "roomName");
      Map var10000 = roomBlocks.remove(roomName);
      if (var10000 == null) {
         return 0;
      }

      Map roomMap = var10000;
      int count = roomMap.size();
      resolvedRoomCache.remove(roomName);
      Iterator var4 = CollectionsKt.toList(roomCacheKeys).iterator();

      while (var4.hasNext()) {
         long key = ((Number)var4.next()).longValue();
         ReplacementCache.INSTANCE.getActiveCache$cb_client().remove(key);
         ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(key);
      }

      roomCacheKeys.clear();
      ReplacementCache.INSTANCE.getRetextureCache$cb_client().clear();
      ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().clear();
      DirtySections.INSTANCE.markAllCachedDirty$cb_client();
      this.forceRoomSave();
      return count;
   }

   public final int clearAllRooms() {
      int count = 0;
      Iterator var2 = roomBlocks.entrySet().iterator();

      while (var2.hasNext()) {
         Map map = (Map)((Entry)var2.next()).getValue();
         count += map.size();
      }

      roomBlocks.clear();
      resolvedRoomCache.clear();
      var2 = CollectionsKt.toList(roomCacheKeys).iterator();

      while (var2.hasNext()) {
         long key = ((Number)var2.next()).longValue();
         ReplacementCache.INSTANCE.getActiveCache$cb_client().remove(key);
         ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(key);
      }

      roomCacheKeys.clear();
      DirtySections.INSTANCE.markAllCachedDirty$cb_client();
      this.forceRoomSave();
      return count;
   }

   @NotNull
   public final List<String> listRoomNames() {
      return CollectionsKt.sorted(roomBlocks.keySet());
   }

   public final int getRoomBlockCount(@NotNull String roomName) {
      Intrinsics.checkNotNullParameter(roomName, "roomName");
      Map var10000 = roomBlocks.get(roomName);
      return var10000 != null ? var10000.size() : 0;
   }

   private static final void saveRoomBlocksAsync$lambda$0(Map $snapshot) {
      try {
         INSTANCE.writeRoomConfig($snapshot);
      } catch (Exception var2) {
      }
   }

   private static final void forceRoomSave$lambda$0(Map $snapshot) {
      try {
         INSTANCE.writeRoomConfig($snapshot);
      } catch (Exception var2) {
      }
   }

   static {
      KeySetView var10000 = ConcurrentHashMap.newKeySet();
      Intrinsics.checkNotNullExpressionValue(var10000, "newKeySet(...)");
      roomCacheKeys = var10000;
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0015\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\n\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0007HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0005HÆ\u0003J?\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0014\u0010\u001a\u001a\u00020\u00072\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u001c\u001a\u00020\u001dHÖ\u0081\u0004J\n\u0010\u001e\u001a\u00020\u001fHÖ\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f¨\u0006 ",
      d2 = {
            "Lcom/cb/cosmetic/RoomStore$ResolvedRoomBlock;",
            "",
            "relPos",
            "Lnet/minecraft/core/BlockPos;",
            "state",
            "Lnet/minecraft/world/level/block/state/BlockState;",
            "phantom",
            "",
            "shapeState",
            "textureState",
            "<init>",
            "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;ZLnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;)V",
            "getRelPos",
            "()Lnet/minecraft/core/BlockPos;",
            "getState",
            "()Lnet/minecraft/world/level/block/state/BlockState;",
            "getPhantom",
            "()Z",
            "getShapeState",
            "getTextureState",
            "component1",
            "component2",
            "component3",
            "component4",
            "component5",
            "copy",
            "equals",
            "other",
            "hashCode",
            "",
            "toString",
            "",
            "cb_client"
      }
   )
   private static final class ResolvedRoomBlock {
      @NotNull
      private final BlockPos relPos;
      @NotNull
      private final BlockState state;
      private final boolean phantom;
      @Nullable
      private final BlockState shapeState;
      @Nullable
      private final BlockState textureState;

      public ResolvedRoomBlock(
         @NotNull BlockPos relPos, @NotNull BlockState state, boolean phantom, @Nullable BlockState shapeState, @Nullable BlockState textureState
      ) {
         Intrinsics.checkNotNullParameter(relPos, "relPos");
         Intrinsics.checkNotNullParameter(state, "state");
         super();
         this.relPos = relPos;
         this.state = state;
         this.phantom = phantom;
         this.shapeState = shapeState;
         this.textureState = textureState;
      }

      @NotNull
      public final BlockPos getRelPos() {
         return this.relPos;
      }

      @NotNull
      public final BlockState getState() {
         return this.state;
      }

      public final boolean getPhantom() {
         return this.phantom;
      }

      @Nullable
      public final BlockState getShapeState() {
         return this.shapeState;
      }

      @Nullable
      public final BlockState getTextureState() {
         return this.textureState;
      }

      @NotNull
      public final BlockPos component1() {
         return this.relPos;
      }

      @NotNull
      public final BlockState component2() {
         return this.state;
      }

      public final boolean component3() {
         return this.phantom;
      }

      @Nullable
      public final BlockState component4() {
         return this.shapeState;
      }

      @Nullable
      public final BlockState component5() {
         return this.textureState;
      }

      @NotNull
      public final RoomStore.ResolvedRoomBlock copy(
         @NotNull BlockPos relPos, @NotNull BlockState state, boolean phantom, @Nullable BlockState shapeState, @Nullable BlockState textureState
      ) {
         Intrinsics.checkNotNullParameter(relPos, "relPos");
         Intrinsics.checkNotNullParameter(state, "state");
         return new RoomStore.ResolvedRoomBlock(relPos, state, phantom, shapeState, textureState);
      }

      @NotNull
      @Override
      public String toString() {
         return "ResolvedRoomBlock(relPos="
            + this.relPos
            + ", state="
            + this.state
            + ", phantom="
            + this.phantom
            + ", shapeState="
            + this.shapeState
            + ", textureState="
            + this.textureState
            + ")";
      }

      @Override
      public int hashCode() {
         int result = this.relPos.hashCode();
         result = result * 31 + this.state.hashCode();
         result = result * 31 + Boolean.hashCode(this.phantom);
         result = result * 31 + (this.shapeState == null ? 0 : this.shapeState.hashCode());
         return result * 31 + (this.textureState == null ? 0 : this.textureState.hashCode());
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof RoomStore.ResolvedRoomBlock var2)) {
            return false;
         } else if (!Intrinsics.areEqual(this.relPos, var2.relPos)) {
            return false;
         } else if (!Intrinsics.areEqual(this.state, var2.state)) {
            return false;
         } else if (this.phantom != var2.phantom) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.shapeState, var2.shapeState) ? false : Intrinsics.areEqual(this.textureState, var2.textureState);
         }
      }
   }
}
