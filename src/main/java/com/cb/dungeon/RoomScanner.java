package com.cb.dungeon;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
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
      Minecraft var10000 = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(var10000, "getInstance(...)");
      return var10000;
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
         InputStream var10000 = RoomScanner.class.getResourceAsStream("/assets/cb/rooms.json");
         if (var10000 == null) {
            return;
         }

         InputStream stream = var10000;
         InputStream type = stream;
         Charset entries = Charsets.UTF_8;
         Reader $this$flatMap$iv = new InputStreamReader(type, entries);
         int var6/* $VF was: $i$a$-use-RoomScanner$loadRoomDatabase$json$1 */ = 8192;
         var var31 = $this$flatMap$iv instanceof BufferedReader
            ? (BufferedReader)$this$flatMap$iv
            : new BufferedReader($this$flatMap$iv, var6/* $VF was: $i$a$-use-RoomScanner$loadRoomDatabase$json$1 */);
         Throwable var33 = null;
         String var36;

         try {
            BufferedReader it = var31;
            var6/* $VF was: $i$a$-use-RoomScanner$loadRoomDatabase$json$1 */ = 0;
            var36 = TextStreamsKt.readText(it);
         } catch (Throwable var28) {
            var33 = var28;
            throw var28;
         } finally {
            CloseableKt.closeFinally(var31, var33);
         }

         String json = var36;
         Type var32 = (new TypeToken<List<? extends RoomScanner.RoomJsonEntry>>() {}).getType();
         Object var50 = new Gson().fromJson(json, var32);
         Intrinsics.checkNotNullExpressionValue(var50, "fromJson(...)");
         List entriesx = (List)var50;
         Iterable var37 = entriesx;
         int $i$f$flatMap = 0;
         Iterable $this$flatMapTo$iv$iv = var37;
         var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(var37, 10));
         int $i$f$flatMapTo = 0;

         for (Object item$iv$iv : $this$flatMapTo$iv$iv) {
            RoomScanner.RoomJsonEntry list$iv$iv = (RoomScanner.RoomJsonEntry)item$iv$iv;
            ArrayList var24 = destination$iv$iv;
            int var13/* $VF was: $i$a$-map-RoomScanner$loadRoomDatabase$1 */ = 0;
            var24.add(
               new RoomInfo(
                  list$iv$iv.getName(),
                  RoomType.Companion.fromString(list$iv$iv.getType()),
                  list$iv$iv.getCores(),
                  list$iv$iv.getSecrets(),
                  RoomShape.Companion.fromString(list$iv$iv.getShape())
               )
            );
         }

         roomDatabase = destination$iv$iv;
         Iterable $this$flatMap$ivx = roomDatabase;
         $i$f$flatMap = 0;
         $this$flatMapTo$iv$iv = $this$flatMap$ivx;
         var destination$iv$ivx = new ArrayList();
         $i$f$flatMapTo = 0;

         for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            RoomInfo room = (RoomInfo)element$iv$iv;
            int var49/* $VF was: $i$a$-flatMap-RoomScanner$loadRoomDatabase$2 */ = 0;
            Iterable $this$map$iv = room.getCores();
            int $i$f$map = 0;
            Iterable $this$mapTo$iv$iv = $this$map$iv;
            var destination$iv$ivxx = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            int $i$f$mapTo = 0;

            for (Object item$iv$iv : $this$mapTo$iv$iv) {
               int core = ((Number)item$iv$iv).intValue();
               Collection var22 = destination$iv$ivxx;
               int var23/* $VF was: $i$a$-map-RoomScanner$loadRoomDatabase$2$1 */ = 0;
               var22.add(TuplesKt.to(core, room));
            }

            List list$iv$iv = destination$iv$ivxx;
            CollectionsKt.addAll(destination$iv$ivx, list$iv$iv);
         }

         coreToRoom = MapsKt.toMap(destination$iv$ivx);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public final boolean tick() {
      LocalPlayer var10000 = this.getMc().player;
      if (var10000 == null) {
         return false;
      } else {
         LocalPlayer player = var10000;
         ClientLevel var7 = this.getMc().level;
         if (var7 == null) {
            return false;
         } else {
            ClientLevel level = var7;
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
      int roomX = posX - -185 + 16 >> 5;
      int roomZ = posZ - -185 + 16 >> 5;
      return new Vec2i((roomX << 5) + -185, (roomZ << 5) + -185);
   }

   private final ScannedRoom scanRoom(Vec2i center, ClientLevel level) {
      LevelChunk core;
      try {
         core = level.getChunk(center.getX() >> 4, center.getZ() >> 4);
      } catch (Exception var9/* $VF was: <unused var> */) {
         return null;
      }

      LevelChunk roomHeight = core;
      Intrinsics.checkNotNull(roomHeight);
      LevelChunk chunk = roomHeight;
      int roomHeightx = this.getTopLayerOfRoom(center.getX(), center.getZ(), chunk);
      if (roomHeightx <= 0) {
         return null;
      }

      int corex = this.getCoreAtHeight(center.getX(), center.getZ(), roomHeightx, chunk);
      RoomInfo var10000 = coreToRoom.get(corex);
      if (var10000 == null) {
         return null;
      }

      RoomInfo roomInfo = var10000;
      Set components = this.findComponentsRecursive(center, roomInfo.getCores(), roomHeightx, level, null, null);
      ScannedRoom room = new ScannedRoom(roomInfo, null, null, components);
      this.findRotation(room, roomHeightx, level);
      return room;
   }

   private final int getTopLayerOfRoom(int x, int z, LevelChunk chunk) {
      for (int y = 140; 59 < y; y--) {
         mutableBlockPos.set(x, y, z);
         BlockState var10000 = chunk.getBlockState(mutableBlockPos);
         Intrinsics.checkNotNullExpressionValue(var10000, "getBlockState(...)");
         BlockState state = var10000;
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
      int y = 140 - clampedHeight;
      char[] block = new char[y];
      StringBuilder var14 = sb;

      while (bedrock < y) {
         int var10 = bedrock;
         block[var10] = '0';
         bedrock++;
      }

      var14.append(block);
      bedrock = 0;

      for (int yx = clampedHeight; 11 < yx; yx--) {
         mutableBlockPos.set(x, yx, z);
         Block var10000 = chunk.getBlockState(mutableBlockPos).getBlock();
         Intrinsics.checkNotNullExpressionValue(var10000, "getBlock(...)");
         Block blockx = var10000;
         if (Intrinsics.areEqual(blockx, Blocks.AIR) && bedrock >= 2 && yx < 69) {
            int var18 = 0;
            int var11 = yx - 11;
            char[] var12 = new char[var11];
            var14 = sb;

            while (var18 < var11) {
               int var13 = var18;
               var12[var13] = '0';
               var18++;
            }

            var14.append(var12);
            break;
         }

         if (Intrinsics.areEqual(blockx, Blocks.BEDROCK)) {
            bedrock++;
         } else {
            bedrock = 0;
            if (Intrinsics.areEqual(blockx, Blocks.OAK_PLANKS)
               || Intrinsics.areEqual(blockx, Blocks.TRAPPED_CHEST)
               || Intrinsics.areEqual(blockx, Blocks.CHEST)) {
               continue;
            }
         }

         sb.append(blockx);
      }

      return sb.toString().hashCode();
   }

   private final Set<Vec2i> findComponentsRecursive(Vec2i center, List<Integer> cores, int roomHeight, ClientLevel level, Set<Vec2i> visited, Set<Vec2i> tiles) {
      if (visited.contains(center)) {
         return tiles;
      }

      visited.add(center);

      LevelChunk var9;
      try {
         var9 = level.getChunk(center.getX() >> 4, center.getZ() >> 4);
      } catch (Exception var13/* $VF was: <unused var> */) {
         return tiles;
      }

      LevelChunk core = var9;
      Intrinsics.checkNotNull(core);
      LevelChunk chunk = core;
      int corex = this.getCoreAtHeight(center.getX(), center.getZ(), roomHeight, chunk);
      if (!cores.contains(corex)) {
         return tiles;
      }

      tiles.add(center);

      for (Direction dir : horizontals) {
         int nx = center.getX() + ((dir.getAxis() == Axis.X ? dir.getStepX() : 0) << 5);
         int nz = center.getZ() + ((dir.getAxis() == Axis.Z ? dir.getStepZ() : 0) << 5);
         this.findComponentsRecursive(new Vec2i(nx, nz), cores, roomHeight, level, visited, tiles);
      }

      return tiles;
   }

   private final void findRotation(ScannedRoom room, int roomHeight, ClientLevel level) {
      if (Intrinsics.areEqual(room.getData().getName(), "Fairy")) {
         Vec2i var10000 = CollectionsKt.firstOrNull(room.getComponents());
         if (var10000 != null) {
            Vec2i it = var10000;
            int var15/* $VF was: $i$a$-let-RoomScanner$findRotation$1 */ = 0;
            room.setClayPos(new BlockPos(it.getX() - 15, roomHeight, it.getZ() - 15));
            room.setRotation(Rotation.SOUTH);
         }
      } else {
         for (Rotation rotation : Rotation.getEntries()) {
            if (rotation != Rotation.NONE) {
               for (Vec2i component : room.getComponents()) {
                  BlockPos testPos = new BlockPos(component.getX() + rotation.getX(), roomHeight, component.getZ() + rotation.getZ());

                  BlockState var11;
                  try {
                     var11 = level.getBlockState(testPos);
                  } catch (Exception var13/* $VF was: <unused var> */) {
                     continue;
                  }

                  BlockState var10 = var11;
                  Intrinsics.checkNotNull(var10);
                  BlockState state = var10;
                  if (Intrinsics.areEqual(state.getBlock(), Blocks.DYED_TERRACOTTA.blue()) && (room.getComponents().size() == 1 || this.validateClay(testPos, level))) {
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
         BlockPos var10000 = pos.offset(dir.getAxis() == Axis.X ? dir.getStepX() : 0, 0, dir.getAxis() == Axis.Z ? dir.getStepZ() : 0);
         Intrinsics.checkNotNullExpressionValue(var10000, "offset(...)");
         BlockPos neighbor = var10000;

         Block var8;
         try {
            var8 = level.getBlockState(neighbor).getBlock();
         } catch (Exception var10/* $VF was: <unused var> */) {
            return false;
         }

         Block var7 = var8;
         Intrinsics.checkNotNull(var7);
         Block block = var7;
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

         BlockState var8;
         try {
            var8 = level.getBlockState(mutableBlockPos);
         } catch (Exception var11/* $VF was: <unused var> */) {
            return null;
         }

         BlockState var7 = var8;
         Intrinsics.checkNotNull(var7);
         BlockState state = var7;
         if (!state.isAir()) {
            height = y;
            break;
         }
      }

      if (73 <= height ? height >= 83 : true) {
         return null;
      }

      mutableBlockPos.set(worldX, 69, worldZ);

      Block var15;
      try {
         var15 = level.getBlockState(mutableBlockPos).getBlock();
      } catch (Exception var10/* $VF was: <unused var> */) {
         return null;
      }

      Block var13 = var15;
      Intrinsics.checkNotNull(var13);
      Block block = var13;
      var13 = block;
      return Intrinsics.areEqual(var13, Blocks.COAL_BLOCK)
         ? "wither"
         : (Intrinsics.areEqual(var13, Blocks.DYED_TERRACOTTA.red()) ? "blood" : (Intrinsics.areEqual(var13, Blocks.AIR) ? "normal" : null));
   }

   static {
      Direction[] var0 = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
      horizontals = CollectionsKt.listOf(var0);
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
         } else if (!(other instanceof RoomScanner.RoomJsonEntry var2)) {
            return false;
         } else if (!Intrinsics.areEqual(this.name, var2.name)) {
            return false;
         } else if (!Intrinsics.areEqual(this.type, var2.type)) {
            return false;
         } else if (!Intrinsics.areEqual(this.cores, var2.cores)) {
            return false;
         } else if (this.secrets != var2.secrets) {
            return false;
         } else if (this.crypts != var2.crypts) {
            return false;
         } else {
            return this.trappedChests != var2.trappedChests ? false : Intrinsics.areEqual(this.shape, var2.shape);
         }
      }

      public RoomJsonEntry() {
         this("", "NORMAL", CollectionsKt.emptyList(), 0, 0, 0, "1x1");
      }
   }
}
