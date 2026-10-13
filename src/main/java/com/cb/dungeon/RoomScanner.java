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
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
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
