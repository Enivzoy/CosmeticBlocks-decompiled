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

   public final void setLastScannedRoom$cb_client(@Nullable String value) {
      lastScannedRoom = value;
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

      boolean loaded;
      try {
         String content = FilesKt.readText(file, kotlin.text.Charsets.UTF_8);
         if (StringsKt.isBlank(content)) {
            return false;
         }

         JsonObject root = JsonParser.parseString(content).getAsJsonObject();

         for (Entry entry : root.entrySet()) {
            Intrinsics.checkNotNull(entry);
            String roomName = (String)entry.getKey();
            JsonElement element = (JsonElement)entry.getValue();
            JsonObject roomObj = element.getAsJsonObject();
            Map roomMap = new LinkedHashMap();

            for (Entry posEntry : roomObj.entrySet()) {
               Intrinsics.checkNotNull(posEntry);
               String pos = (String)posEntry.getKey();
               JsonElement block = (JsonElement)posEntry.getValue();
               roomMap.put(pos, block.getAsString());
            }

            roomBlocks.put(roomName, roomMap);
         }

         loaded = true;
      } catch (Exception e) {
         System.err.println("[CB] Failed to parse room file " + file.getName() + ": " + e.getMessage());
         loaded = false;
      }

      return loaded;
   }

   public final void saveRoomBlocksAsync$cb_client() {
      SaveScheduler.INSTANCE.setNeedsRoomSave$cb_client(false);
      SaveScheduler.INSTANCE.setLastRoomSaveTime$cb_client(System.currentTimeMillis());
      Map snapshot = this.snapshotRoomBlocks();
      SaveScheduler.INSTANCE.getSaveExecutor$cb_client().execute(() -> saveRoomBlocksTask(snapshot));
   }

   public final void forceRoomSave() {
      SaveScheduler.INSTANCE.setNeedsRoomSave$cb_client(false);
      SaveScheduler.INSTANCE.setLastRoomSaveTime$cb_client(System.currentTimeMillis());
      Map snapshot = this.snapshotRoomBlocks();
      SaveScheduler.INSTANCE.getSaveExecutor$cb_client().execute(() -> forceRoomSaveTask(snapshot));
   }

   private final Map<String, Map<String, String>> snapshotRoomBlocks() {
      HashMap snapshot = new HashMap(roomBlocks.size());

      for (Entry entry : roomBlocks.entrySet()) {
         String room = (String)entry.getKey();
         Map map = (Map)entry.getValue();
         snapshot.put(room, new HashMap(map));
      }

      return snapshot;
   }

   private final void writeRoomConfig(Map<String, ? extends Map<String, String>> snapshot) throws IOException {
      File parentDir = roomBlocksFile.getParentFile();
      if (parentDir != null) {
         parentDir.mkdirs();
      }

      File tmpFile = new File(roomBlocksFile.getParentFile(), roomBlocksFile.getName() + ".tmp");
      BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile), 65536);
      Throwable writerError = null;

      try {
         JsonWriter jw = new JsonWriter(writer);
         Throwable jsonWriterError = null;

         try {
            jw.setIndent("  ");
            jw.beginObject();

            for (Entry entry : snapshot.entrySet()) {
               String room = (String)entry.getKey();
               Map<String, String> map = (Map<String, String>)entry.getValue();
               jw.name(room);
               jw.beginObject();

               for (Entry posEntry : map.entrySet()) {
                  String pos = (String)posEntry.getKey();
                  String block = (String)posEntry.getValue();
                  jw.name(pos).value(block);
               }

               jw.endObject();
            }

            jw = jw.endObject();
         } catch (Throwable jsonError) {
            jsonWriterError = jsonError;
            throw jsonError;
         } finally {
            CloseableKt.closeFinally(jw, jsonWriterError);
         }
      } catch (Throwable writeError) {
         writerError = writeError;
         throw writeError;
      } finally {
         CloseableKt.closeFinally(writer, writerError);
      }

      if (roomBlocksFile.exists()) {
         roomBlocksFile.delete();
      }

      tmpFile.renameTo(roomBlocksFile);
   }

   public final void onRoomChanged$cb_client(@Nullable ScannedRoom newRoom) {
      HashSet dirtySections = new HashSet();

      for (long key : roomCacheKeys) {
         dirtySections.add(DirtySections.INSTANCE.sectionKey$cb_client(BlockPos.getX(key), BlockPos.getY(key), BlockPos.getZ(key)));
         ReplacementCache.INSTANCE.getActiveCache$cb_client().remove(key);
         ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(key);
         ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(key);
         ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(key);
         ReplacementCache.INSTANCE.getShapedPositions$cb_client().remove(key);
      }

      roomCacheKeys.clear();
      if (newRoom != null && newRoom.getRotation() != Rotation.NONE) {
         List<RoomStore.ResolvedRoomBlock> resolved = resolvedRoomCache.get(newRoom.getData().getName());
         if (resolved != null) {
            Rotation rot = newRoom.getRotation();

            for (RoomStore.ResolvedRoomBlock entry : resolved) {
               BlockPos absPos = RoomDataKt.rotateAroundNorth(entry.getRelPos(), rot).offset(newRoom.getClayPos().getX(), 0, newRoom.getClayPos().getZ());
               Intrinsics.checkNotNullExpressionValue(absPos, "offset(...)");
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

         for (Entry entry : rawMap.entrySet()) {
            String relKey = (String)entry.getKey();
            String raw = (String)entry.getValue();
            CharSequence keyText = relKey;
            String[] separators = new String[]{","};
            List parts = StringsKt.split(keyText, separators, false, 0);
            if (parts.size() == 3) {
               try {
                  BlockPos relBlockPos = new BlockPos(
                     Integer.parseInt((String)parts.get(0)), Integer.parseInt((String)parts.get(1)), Integer.parseInt((String)parts.get(2))
                  );
                  boolean phantom = StringsKt.startsWith(raw, "!", false);
                  String cleanRaw;
                  if (phantom) {
                     cleanRaw = raw.substring(1);
                     Intrinsics.checkNotNullExpressionValue(cleanRaw, "substring(...)");
                  } else {
                     cleanRaw = raw;
                  }

                  String[] blockStr = new String[]{"|shape="};
                  List<String> shapeSplit = StringsKt.split(cleanRaw, blockStr, false, 2);
                  String blockText = (String)shapeSplit.get(0);
                  String shape = CollectionsKt.getOrNull(shapeSplit, 1);
                  if (shape != null) {
                     BlockState shapeState = BlockStateCodec.INSTANCE.resolveShapeState$cb_client(shape);
                     BlockState textureState = BlockStateCodec.INSTANCE.resolveBlockState(blockText);
                     if (shapeState != null && textureState != null) {
                        list.add(new RoomStore.ResolvedRoomBlock(relBlockPos, shapeState, phantom, shapeState, textureState));
                     }
                  } else {
                     BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(blockText);
                     if (state != null) {
                        list.add(new RoomStore.ResolvedRoomBlock(relBlockPos, state, phantom, null, null));
                     }
                  }
               } catch (Exception ignored) {
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
      BlockPos localPos = pos.subtract(room.getClayPos().atY(0));
      Intrinsics.checkNotNullExpressionValue(localPos, "subtract(...)");
      BlockPos relPos = RoomDataKt.rotateToNorth(localPos, room.getRotation());
      String relKey = relPos.getX() + "," + relPos.getY() + "," + relPos.getZ();
      Map targetMap = roomBlocks;
      Object roomKey = room.getData().getName();
      Object existingValue = targetMap.get(roomKey);
      Object roomMapValue;
      if (existingValue == null) {
         Object createdMap = new LinkedHashMap();
         targetMap.put(roomKey, createdMap);
         roomMapValue = createdMap;
      } else {
         roomMapValue = existingValue;
      }

      Map roomMap = (Map)roomMapValue;
      String rotatedId = BlockStateCodec.INSTANCE.rotateStateToNorth$cb_client(blockId, room.getRotation());
      roomKey = EditMode.INSTANCE.getCurrentShape() != null ? rotatedId + "|shape=" + EditMode.INSTANCE.getCurrentShape() : rotatedId;
      roomMap.put(relKey, phantom ? "!" + roomKey : roomKey);
      long packed = BlockPos.asLong(pos.getX(), pos.getY(), pos.getZ());
      if (EditMode.INSTANCE.getCurrentShape() != null) {
         BlockStateCodec codec = BlockStateCodec.INSTANCE;
         String shape = EditMode.INSTANCE.getCurrentShape();
         Intrinsics.checkNotNull(shape);
         BlockState shapeState = codec.resolveShapeState$cb_client(shape);
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
      BlockPos localPos = pos.subtract(room.getClayPos().atY(0));
      Intrinsics.checkNotNullExpressionValue(localPos, "subtract(...)");
      BlockPos relPos = RoomDataKt.rotateToNorth(localPos, room.getRotation());
      Map roomMap = roomBlocks.get(room.getData().getName());
      return roomMap != null ? (String)roomMap.get(relPos.getX() + "," + relPos.getY() + "," + relPos.getZ()) : null;
   }

   public final void restoreRoomFill$cb_client(@NotNull Map<String, String> snapshot, @NotNull ScannedRoom room) {
      Intrinsics.checkNotNullParameter(snapshot, "snapshot");
      Intrinsics.checkNotNullParameter(room, "room");
      Map targetMap = roomBlocks;
      Object roomKey = room.getData().getName();
      Object existingValue = targetMap.get(roomKey);
      Object roomMapValue;
      if (existingValue == null) {
         Object createdMap = new LinkedHashMap();
         targetMap.put(roomKey, createdMap);
         roomMapValue = createdMap;
      } else {
         roomMapValue = existingValue;
      }

      Map roomMap = (Map)roomMapValue;

      for (Entry entry : snapshot.entrySet()) {
         String relKey = (String)entry.getKey();
         existingValue = (String)entry.getValue();
         if (existingValue == null) {
            roomMap.remove(relKey);
         } else {
            roomMap.put(relKey, existingValue);
         }
      }

      if (roomMap.isEmpty()) {
         roomBlocks.remove(room.getData().getName());
      }

      String currentRoomName;
      label30: {
         this.resolveRoomBlocks$cb_client(room.getData().getName());
         ScannedRoom currentRoom = RoomScanner.INSTANCE.getCurrentRoom();
         if (currentRoom != null) {
            RoomInfo roomInfo = currentRoom.getData();
            if (roomInfo != null) {
               currentRoomName = roomInfo.getName();
               break label30;
            }
         }

         currentRoomName = null;
      }

      if (Intrinsics.areEqual(currentRoomName, room.getData().getName())) {
         this.onRoomChanged$cb_client(RoomScanner.INSTANCE.getCurrentRoom());
      }

      SaveScheduler.INSTANCE.scheduleRoomSave$cb_client();
   }

   public final void setRoomReplacementState$cb_client(@NotNull BlockPos pos, @NotNull BlockState state, boolean phantom, @NotNull ScannedRoom room) {
      Intrinsics.checkNotNullParameter(pos, "pos");
      Intrinsics.checkNotNullParameter(state, "state");
      Intrinsics.checkNotNullParameter(room, "room");
      BlockPos localPos = pos.subtract(room.getClayPos().atY(0));
      Intrinsics.checkNotNullExpressionValue(localPos, "subtract(...)");
      BlockPos relPos = RoomDataKt.rotateToNorth(localPos, room.getRotation());
      String relKey = relPos.getX() + "," + relPos.getY() + "," + relPos.getZ();
      Map targetMap = roomBlocks;
      Object roomKey = room.getData().getName();
      Object existingValue = targetMap.get(roomKey);
      Object roomMapValue;
      if (existingValue == null) {
         Object createdMap = new LinkedHashMap();
         targetMap.put(roomKey, createdMap);
         roomMapValue = createdMap;
      } else {
         roomMapValue = existingValue;
      }

      Map roomMap = (Map)roomMapValue;
      String serializedState = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(state);
      roomKey = BlockStateCodec.INSTANCE.rotateStateToNorth$cb_client(serializedState, room.getRotation());
      roomMap.put(relKey, phantom ? "!" + roomKey : roomKey);
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
      BlockPos localPos = pos.subtract(room.getClayPos().atY(0));
      Intrinsics.checkNotNullExpressionValue(localPos, "subtract(...)");
      BlockPos relPos = RoomDataKt.rotateToNorth(localPos, room.getRotation());
      String relKey = relPos.getX() + "," + relPos.getY() + "," + relPos.getZ();
      Map roomMap = roomBlocks.get(room.getData().getName());
      if (roomMap == null) {
         return false;
      }

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
      Map roomMap = roomBlocks.remove(roomName);
      if (roomMap == null) {
         return 0;
      }

      int count = roomMap.size();
      resolvedRoomCache.remove(roomName);

      for (long key : CollectionsKt.toList(roomCacheKeys)) {
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
      for (Entry entry : roomBlocks.entrySet()) {
         Map map = (Map)entry.getValue();
         count += map.size();
      }

      roomBlocks.clear();
      resolvedRoomCache.clear();

      for (long key : CollectionsKt.toList(roomCacheKeys)) {
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
      Map roomMap = roomBlocks.get(roomName);
      return roomMap != null ? roomMap.size() : 0;
   }

   private static final void saveRoomBlocksTask(Map $snapshot) {
      try {
         INSTANCE.writeRoomConfig($snapshot);
      } catch (Exception ignored) {
      }
   }

   private static final void forceRoomSaveTask(Map $snapshot) {
      try {
         INSTANCE.writeRoomConfig($snapshot);
      } catch (Exception ignored) {
      }
   }

   static {
      KeySetView keys = ConcurrentHashMap.newKeySet();
      Intrinsics.checkNotNullExpressionValue(keys, "newKeySet(...)");
      roomCacheKeys = keys;
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
         } else if (!(other instanceof RoomStore.ResolvedRoomBlock that)) {
            return false;
         } else if (!Intrinsics.areEqual(this.relPos, that.relPos)) {
            return false;
         } else if (!Intrinsics.areEqual(this.state, that.state)) {
            return false;
         } else if (this.phantom != that.phantom) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.shapeState, that.shapeState) ? false : Intrinsics.areEqual(this.textureState, that.textureState);
         }
      }
   }
}
