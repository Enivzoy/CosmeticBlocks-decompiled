package com.cb.dungeon;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001@B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u0010\u001f\u001a\u00020\u001eJ\b\u0010 \u001a\u00020\u001eH\u0002J\u0006\u0010!\u001a\u00020\u0015J\u0016\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005J\u001a\u0010%\u001a\u0004\u0018\u00010\u00172\u0006\u0010&\u001a\u00020\u001c2\u0006\u0010'\u001a\u00020(H\u0002J \u0010)\u001a\u00020\u00052\u0006\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u00052\u0006\u0010,\u001a\u00020-H\u0002J(\u0010.\u001a\u00020\u00052\u0006\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u00052\u0006\u0010/\u001a\u00020\u00052\u0006\u0010,\u001a\u00020-H\u0002JT\u00100\u001a\b\u0012\u0004\u0012\u00020\u001c012\u0006\u0010&\u001a\u00020\u001c2\f\u00102\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\u0006\u0010/\u001a\u00020\u00052\u0006\u0010'\u001a\u00020(2\u000e\b\u0002\u00103\u001a\b\u0012\u0004\u0012\u00020\u001c012\u000e\b\u0002\u00104\u001a\b\u0012\u0004\u0012\u00020\u001c01H\u0002J \u00105\u001a\u00020\u001e2\u0006\u00106\u001a\u00020\u00172\u0006\u0010/\u001a\u00020\u00052\u0006\u0010'\u001a\u00020(H\u0002J\u0018\u00107\u001a\u00020\u00152\u0006\u00108\u001a\u0002092\u0006\u0010'\u001a\u00020(H\u0002J\u0006\u0010:\u001a\u00020\u0015J\u0018\u0010;\u001a\u0004\u0018\u00010\u00172\u0006\u0010&\u001a\u00020\u001c2\u0006\u0010'\u001a\u00020(J \u0010<\u001a\u0004\u0018\u00010=2\u0006\u0010>\u001a\u00020\u00052\u0006\u0010?\u001a\u00020\u00052\u0006\u0010'\u001a\u00020(R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00110\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\u0018\u001a\u0004\u0018\u00010\u00172\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006A",
   d2 = {
         "Lcom/cb/dungeon/RoomScanner;",
         "",
         "<init>",
         "()V",
         "START",
         "",
         "ROOM_SIZE_SHIFT",
         "mc",
         "Lnet/minecraft/client/Minecraft;",
         "getMc",
         "()Lnet/minecraft/client/Minecraft;",
         "horizontals",
         "",
         "Lnet/minecraft/core/Direction;",
         "mutableBlockPos",
         "Lnet/minecraft/core/BlockPos$MutableBlockPos;",
         "roomDatabase",
         "Lcom/cb/dungeon/RoomInfo;",
         "coreToRoom",
         "",
         "loaded",
         "",
         "value",
         "Lcom/cb/dungeon/ScannedRoom;",
         "currentRoom",
         "getCurrentRoom",
         "()Lcom/cb/dungeon/ScannedRoom;",
         "lastRoomCenter",
         "Lcom/cb/dungeon/Vec2i;",
         "init",
         "",
         "unload",
         "loadRoomDatabase",
         "tick",
         "getRoomCenter",
         "posX",
         "posZ",
         "scanRoom",
         "center",
         "level",
         "Lnet/minecraft/client/multiplayer/ClientLevel;",
         "getTopLayerOfRoom",
         "x",
         "z",
         "chunk",
         "Lnet/minecraft/world/level/chunk/LevelChunk;",
         "getCoreAtHeight",
         "roomHeight",
         "findComponentsRecursive",
         "",
         "cores",
         "visited",
         "tiles",
         "findRotation",
         "room",
         "validateClay",
         "pos",
         "Lnet/minecraft/core/BlockPos;",
         "isReady",
         "scanAt",
         "detectDoorType",
         "",
         "worldX",
         "worldZ",
         "RoomJsonEntry",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nRoomScanner.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RoomScanner.kt\ncom/cb/dungeon/RoomScanner\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,320:1\n1#2:321\n1586#3:322\n1661#3,3:323\n1391#3:326\n1480#3,2:327\n1586#3:329\n1661#3,3:330\n1482#3,3:333\n*S KotlinDebug\n*F\n+ 1 RoomScanner.kt\ncom/cb/dungeon/RoomScanner\n*L\n56#1:322\n56#1:323,3\n66#1:326\n66#1:327,2\n67#1:329\n67#1:330,3\n66#1:333,3\n*E\n"
)
public final class RoomScanner {
   @NotNull
   public static final RoomScanner INSTANCE = new RoomScanner();
   private static final int START = -185;
   private static final int ROOM_SIZE_SHIFT = 5;
   @NotNull
   private static final List<Direction> horizontals;
   @NotNull
   private static final MutableBlockPos mutableBlockPos = new MutableBlockPos();
   @NotNull
   private static List<RoomInfo> roomDatabase = CollectionsKt.emptyList();
   @NotNull
   private static Map<Integer, RoomInfo> coreToRoom = MapsKt.emptyMap();
   private static boolean loaded;
   @Nullable
   private static ScannedRoom currentRoom;
   @Nullable
   private static Vec2i lastRoomCenter;

   private RoomScanner() {
   }

   private final Minecraft getMc() {
      Minecraft mc = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
      return mc;
   }

   @Nullable
   public final ScannedRoom getCurrentRoom() {
      return currentRoom;
   }

   public final void init() {
      if (!loaded) {
         this.loadRoomDatabase();
         loaded = true;
      }
   }

   public final void unload() {
      currentRoom = null;
      lastRoomCenter = null;
   }

   private final void loadRoomDatabase() {
      try {
         InputStream stream = RoomScanner.class.getResourceAsStream("/assets/cb/rooms.json");
         if (stream == null) {
            return;
         }

         String json;
         try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charsets.UTF_8), 8192)) {
            json = TextStreamsKt.readText(reader);
         }

         Type type = (new TypeToken<List<RoomScanner.RoomJsonEntry>>() {}).getType();
         List<RoomScanner.RoomJsonEntry> entries = new Gson().fromJson(json, type);
         Intrinsics.checkNotNullExpressionValue(entries, "fromJson(...)");
         List<RoomInfo> database = new ArrayList<>(CollectionsKt.collectionSizeOrDefault(entries, 10));

         for (RoomScanner.RoomJsonEntry entry : entries) {
            database.add(
               new RoomInfo(
                  entry.getName(),
                  RoomType.Companion.fromString(entry.getType()),
                  entry.getCores(),
                  entry.getSecrets(),
                  RoomShape.Companion.fromString(entry.getShape())
               )
            );
         }

         roomDatabase = database;
         List<Pair<Integer, RoomInfo>> coreRooms = new ArrayList<>();

         for (RoomInfo room : roomDatabase) {
            for (Integer core : room.getCores()) {
               coreRooms.add(TuplesKt.to(core, room));
            }
         }

         coreToRoom = MapsKt.toMap(coreRooms);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public final boolean tick() {
      LocalPlayer player = this.getMc().player;
      if (player == null) {
         return false;
      } else {
         ClientLevel level = this.getMc().level;
         if (level == null) {
            return false;
         } else {
            Vec2i center = this.getRoomCenter(player.blockPosition().getX(), player.blockPosition().getZ());
            ScannedRoom current = currentRoom;
            if (current != null && current.getComponents().contains(center)) {
               return false;
            } else if (Intrinsics.areEqual(center, lastRoomCenter)) {
               return false;
            } else {
               lastRoomCenter = center;
               ScannedRoom room = this.scanRoom(center, level);
               if (room != null && room.getRotation() != Rotation.NONE) {
                  boolean changed = current == null
                     || !Intrinsics.areEqual(current.getData().getName(), room.getData().getName())
                     || !current.getComponents().containsAll(room.getComponents());
                  currentRoom = room;
                  return changed;
               } else {
                  return false;
               }
            }
         }
      }
   }

   @NotNull
   public final Vec2i getRoomCenter(int posX, int posZ) {
      int roomX = posX - START + 16 >> ROOM_SIZE_SHIFT;
      int roomZ = posZ - START + 16 >> ROOM_SIZE_SHIFT;
      return new Vec2i((roomX << ROOM_SIZE_SHIFT) + START, (roomZ << ROOM_SIZE_SHIFT) + START);
   }

   private final ScannedRoom scanRoom(Vec2i center, ClientLevel level) {
      LevelChunk chunk;
      try {
         chunk = level.getChunk(center.getX() >> 4, center.getZ() >> 4);
      } catch (Exception ignored) {
         return null;
      }

      Intrinsics.checkNotNull(chunk);
      int roomHeight = this.getTopLayerOfRoom(center.getX(), center.getZ(), chunk);
      if (roomHeight <= 0) {
         return null;
      }

      int core = this.getCoreAtHeight(center.getX(), center.getZ(), roomHeight, chunk);
      RoomInfo roomInfo = coreToRoom.get(core);
      if (roomInfo == null) {
         return null;
      }

      Set<Vec2i> components = this.findComponentsRecursive(center, roomInfo.getCores(), roomHeight, level, new LinkedHashSet<>(), new LinkedHashSet<>());
      ScannedRoom room = new ScannedRoom(roomInfo, Rotation.NONE, BlockPos.ZERO, components);
      this.findRotation(room, roomHeight, level);
      return room;
   }

   private final int getTopLayerOfRoom(int x, int z, LevelChunk chunk) {
      for (int y = 140; 59 < y; y--) {
         mutableBlockPos.set(x, y, z);
         BlockState state = chunk.getBlockState(mutableBlockPos);
         Intrinsics.checkNotNullExpressionValue(state, "getBlockState(...)");
         if (!state.isAir()) {
            return Intrinsics.areEqual(state.getBlock(), Blocks.GOLD_BLOCK) ? y - 1 : y;
         }
      }

      return 0;
   }

   private final int getCoreAtHeight(int x, int z, int roomHeight, LevelChunk chunk) {
      StringBuilder sb = new StringBuilder(150);
      int clampedHeight = RangesKt.coerceIn(roomHeight, new IntRange(11, 140));
      int bedrock = 0;
      int paddingSize = 140 - clampedHeight;
      char[] padding = new char[paddingSize];

      while (bedrock < paddingSize) {
         padding[bedrock] = '0';
         bedrock++;
      }

      sb.append(padding);
      bedrock = 0;

      for (int y = clampedHeight; 11 < y; y--) {
         mutableBlockPos.set(x, y, z);
         Block block = chunk.getBlockState(mutableBlockPos).getBlock();
         Intrinsics.checkNotNullExpressionValue(block, "getBlock(...)");
         if (Intrinsics.areEqual(block, Blocks.AIR) && bedrock >= 2 && y < 69) {
            int remaining = y - 11;
            char[] tail = new char[remaining];

            for (int i = 0; i < remaining; i++) {
               tail[i] = '0';
            }

            sb.append(tail);
            break;
         }

         if (Intrinsics.areEqual(block, Blocks.BEDROCK)) {
            bedrock++;
         } else {
            bedrock = 0;
            if (Intrinsics.areEqual(block, Blocks.OAK_PLANKS)
               || Intrinsics.areEqual(block, Blocks.TRAPPED_CHEST)
               || Intrinsics.areEqual(block, Blocks.CHEST)) {
               continue;
            }
         }

         sb.append(block);
      }

      return sb.toString().hashCode();
   }

   private final Set<Vec2i> findComponentsRecursive(Vec2i center, List<Integer> cores, int roomHeight, ClientLevel level, Set<Vec2i> visited, Set<Vec2i> tiles) {
      if (visited.contains(center)) {
         return tiles;
      }

      visited.add(center);

      LevelChunk chunk;
      try {
         chunk = level.getChunk(center.getX() >> 4, center.getZ() >> 4);
      } catch (Exception ignored) {
         return tiles;
      }

      Intrinsics.checkNotNull(chunk);
      int core = this.getCoreAtHeight(center.getX(), center.getZ(), roomHeight, chunk);
      if (!cores.contains(core)) {
         return tiles;
      }

      tiles.add(center);

      for (Direction dir : horizontals) {
         int nx = center.getX() + ((dir.getAxis() == Axis.X ? dir.getStepX() : 0) << ROOM_SIZE_SHIFT);
         int nz = center.getZ() + ((dir.getAxis() == Axis.Z ? dir.getStepZ() : 0) << ROOM_SIZE_SHIFT);
         this.findComponentsRecursive(new Vec2i(nx, nz), cores, roomHeight, level, visited, tiles);
      }

      return tiles;
   }

   private final void findRotation(ScannedRoom room, int roomHeight, ClientLevel level) {
      if (Intrinsics.areEqual(room.getData().getName(), "Fairy")) {
         Vec2i first = CollectionsKt.firstOrNull(room.getComponents());
         if (first != null) {
            room.setClayPos(new BlockPos(first.getX() - 15, roomHeight, first.getZ() - 15));
            room.setRotation(Rotation.SOUTH);
         }
      } else {
         for (Rotation rotation : Rotation.getEntries()) {
            if (rotation != Rotation.NONE) {
               for (Vec2i component : room.getComponents()) {
                  BlockPos testPos = new BlockPos(component.getX() + rotation.getX(), roomHeight, component.getZ() + rotation.getZ());

                  BlockState state;
                  try {
                     state = level.getBlockState(testPos);
                  } catch (Exception ignored) {
                     continue;
                  }

                  Intrinsics.checkNotNull(state);
                  if (Intrinsics.areEqual(state.getBlock(), Blocks.DYED_TERRACOTTA.blue())
                     && (room.getComponents().size() == 1 || this.validateClay(testPos, level))) {
                     room.setClayPos(testPos);
                     room.setRotation(rotation);
                     return;
                  }
               }
            }
         }
      }
   }

   private final boolean validateClay(BlockPos pos, ClientLevel level) {
      for (Direction dir : horizontals) {
         BlockPos neighbor = pos.offset(dir.getAxis() == Axis.X ? dir.getStepX() : 0, 0, dir.getAxis() == Axis.Z ? dir.getStepZ() : 0);
         Intrinsics.checkNotNullExpressionValue(neighbor, "offset(...)");

         Block block;
         try {
            block = level.getBlockState(neighbor).getBlock();
         } catch (Exception ignored) {
            return false;
         }

         Intrinsics.checkNotNull(block);
         if (!Intrinsics.areEqual(block, Blocks.AIR) && !Intrinsics.areEqual(block, Blocks.DYED_TERRACOTTA.blue())) {
            return false;
         }
      }

      return true;
   }

   public final boolean isReady() {
      return !coreToRoom.isEmpty();
   }

   @Nullable
   public final ScannedRoom scanAt(@NotNull Vec2i center, @NotNull ClientLevel level) {
      Intrinsics.checkNotNullParameter(center, "center");
      Intrinsics.checkNotNullParameter(level, "level");
      return this.scanRoom(center, level);
   }

   @Nullable
   public final String detectDoorType(int worldX, int worldZ, @NotNull ClientLevel level) {
      Intrinsics.checkNotNullParameter(level, "level");
      int height = 0;

      for (int y = 140; 11 < y; y--) {
         mutableBlockPos.set(worldX, y, worldZ);

         BlockState state;
         try {
            state = level.getBlockState(mutableBlockPos);
         } catch (Exception ignored) {
            return null;
         }

         Intrinsics.checkNotNull(state);
         if (!state.isAir()) {
            height = y;
            break;
         }
      }

      if (73 <= height ? height >= 83 : true) {
         return null;
      }

      mutableBlockPos.set(worldX, 69, worldZ);

      Block block;
      try {
         block = level.getBlockState(mutableBlockPos).getBlock();
      } catch (Exception ignored) {
         return null;
      }

      Intrinsics.checkNotNull(block);
      return Intrinsics.areEqual(block, Blocks.COAL_BLOCK)
         ? "wither"
         : (Intrinsics.areEqual(block, Blocks.DYED_TERRACOTTA.red()) ? "blood" : (Intrinsics.areEqual(block, Blocks.AIR) ? "normal" : null));
   }

   static {
      Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
      horizontals = CollectionsKt.listOf(directions);
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\b\u0018\u00002\u00020\u0001BS\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003¢\u0006\u0004\b\f\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003JU\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u0003HÆ\u0001J\u0014\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010#\u001a\u00020\u0007HÖ\u0081\u0004J\n\u0010$\u001a\u00020\u0003HÖ\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\t\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000f¨\u0006%",
      d2 = {
            "Lcom/cb/dungeon/RoomScanner$RoomJsonEntry;",
            "",
            "name",
            "",
            "type",
            "cores",
            "",
            "",
            "secrets",
            "crypts",
            "trappedChests",
            "shape",
            "<init>",
            "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;IIILjava/lang/String;)V",
            "getName",
            "()Ljava/lang/String;",
            "getType",
            "getCores",
            "()Ljava/util/List;",
            "getSecrets",
            "()I",
            "getCrypts",
            "getTrappedChests",
            "getShape",
            "component1",
            "component2",
            "component3",
            "component4",
            "component5",
            "component6",
            "component7",
            "copy",
            "equals",
            "",
            "other",
            "hashCode",
            "toString",
            "cb_client"
      }
   )
   private static final class RoomJsonEntry {
      @NotNull
      private final String name;
      @NotNull
      private final String type;
      @NotNull
      private final List<Integer> cores;
      private final int secrets;
      private final int crypts;
      private final int trappedChests;
      @NotNull
      private final String shape;

      public RoomJsonEntry(
         @NotNull String name, @NotNull String type, @NotNull List<Integer> cores, int secrets, int crypts, int trappedChests, @NotNull String shape
      ) {
         Intrinsics.checkNotNullParameter(name, "name");
         Intrinsics.checkNotNullParameter(type, "type");
         Intrinsics.checkNotNullParameter(cores, "cores");
         Intrinsics.checkNotNullParameter(shape, "shape");
         super();
         this.name = name;
         this.type = type;
         this.cores = cores;
         this.secrets = secrets;
         this.crypts = crypts;
         this.trappedChests = trappedChests;
         this.shape = shape;
      }

      @NotNull
      public final String getName() {
         return this.name;
      }

      @NotNull
      public final String getType() {
         return this.type;
      }

      @NotNull
      public final List<Integer> getCores() {
         return this.cores;
      }

      public final int getSecrets() {
         return this.secrets;
      }

      public final int getCrypts() {
         return this.crypts;
      }

      public final int getTrappedChests() {
         return this.trappedChests;
      }

      @NotNull
      public final String getShape() {
         return this.shape;
      }

      @NotNull
      public final String component1() {
         return this.name;
      }

      @NotNull
      public final String component2() {
         return this.type;
      }

      @NotNull
      public final List<Integer> component3() {
         return this.cores;
      }

      public final int component4() {
         return this.secrets;
      }

      public final int component5() {
         return this.crypts;
      }

      public final int component6() {
         return this.trappedChests;
      }

      @NotNull
      public final String component7() {
         return this.shape;
      }

      @NotNull
      public final RoomScanner.RoomJsonEntry copy(
         @NotNull String name, @NotNull String type, @NotNull List<Integer> cores, int secrets, int crypts, int trappedChests, @NotNull String shape
      ) {
         Intrinsics.checkNotNullParameter(name, "name");
         Intrinsics.checkNotNullParameter(type, "type");
         Intrinsics.checkNotNullParameter(cores, "cores");
         Intrinsics.checkNotNullParameter(shape, "shape");
         return new RoomScanner.RoomJsonEntry(name, type, cores, secrets, crypts, trappedChests, shape);
      }

      @NotNull
      @Override
      public String toString() {
         return "RoomJsonEntry(name="
            + this.name
            + ", type="
            + this.type
            + ", cores="
            + this.cores
            + ", secrets="
            + this.secrets
            + ", crypts="
            + this.crypts
            + ", trappedChests="
            + this.trappedChests
            + ", shape="
            + this.shape
            + ")";
      }

      @Override
      public int hashCode() {
         int result = this.name.hashCode();
         result = result * 31 + this.type.hashCode();
         result = result * 31 + this.cores.hashCode();
         result = result * 31 + Integer.hashCode(this.secrets);
         result = result * 31 + Integer.hashCode(this.crypts);
         result = result * 31 + Integer.hashCode(this.trappedChests);
         return result * 31 + this.shape.hashCode();
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof RoomScanner.RoomJsonEntry that)) {
            return false;
         } else if (!Intrinsics.areEqual(this.name, that.name)) {
            return false;
         } else if (!Intrinsics.areEqual(this.type, that.type)) {
            return false;
         } else if (!Intrinsics.areEqual(this.cores, that.cores)) {
            return false;
         } else if (this.secrets != that.secrets) {
            return false;
         } else if (this.crypts != that.crypts) {
            return false;
         } else {
            return this.trappedChests != that.trappedChests ? false : Intrinsics.areEqual(this.shape, that.shape);
         }
      }

      public RoomJsonEntry() {
         this("", "NORMAL", CollectionsKt.emptyList(), 0, 0, 0, "1x1");
      }
   }
}
