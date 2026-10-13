package com.cb.cosmetic;

import com.cb.config.tabs.BlockRemapsTab;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0013\bÆ\u0002\u0018\u00002\u00020\u0001:\u000234B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J(\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0019J\u0006\u0010\u001c\u001a\u00020\u001dJ\r\u0010\u001e\u001a\u00020\u001fH\u0000¢\u0006\u0002\b J\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00060\"J\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020\b0\"2\u0006\u0010$\u001a\u00020\u0006J\u000e\u0010%\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020\u0006J \u0010&\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u00062\u0006\u0010'\u001a\u00020\u00062\b\b\u0002\u0010(\u001a\u00020\u0019J\u001e\u0010)\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\u0019J\u0016\u0010+\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0019J\u000e\u0010,\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u0006J\u0006\u0010-\u001a\u00020\u001fJ\u0006\u0010.\u001a\u00020\u0006J\u000e\u0010/\u001a\u00020\u001d2\u0006\u00100\u001a\u00020\u0006J\u0006\u00101\u001a\u00020\u001fJ\u0006\u00102\u001a\u00020\u001fR<\u0010\u0004\u001a*\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005j\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007`\tX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n \u0010*\u0004\u0018\u00010\u000f0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00065",
   d2 = {
         "Lcom/cb/cosmetic/GlobalRemap;",
         "",
         "<init>",
         "()V",
         "remaps",
         "Ljava/util/LinkedHashMap;",
         "",
         "",
         "Lcom/cb/cosmetic/GlobalRemap$RemapTarget;",
         "Lkotlin/collections/LinkedHashMap;",
         "getRemaps$cb_client",
         "()Ljava/util/LinkedHashMap;",
         "file",
         "Ljava/io/File;",
         "gson",
         "Lcom/google/gson/Gson;",
         "kotlin.jvm.PlatformType",
         "resolved",
         "",
         "Lnet/minecraft/world/level/block/Block;",
         "Lcom/cb/cosmetic/GlobalRemap$Resolved;",
         "remapFor",
         "Lnet/minecraft/world/level/block/state/BlockState;",
         "block",
         "x",
         "",
         "y",
         "z",
         "hasRemaps",
         "",
         "rebuild",
         "",
         "rebuild$cb_client",
         "sources",
         "",
         "targetsOf",
         "source",
         "hasSource",
         "addTarget",
         "targetId",
         "weight",
         "setWeight",
         "index",
         "removeTarget",
         "removeSource",
         "clear",
         "exportJson",
         "importJson",
         "json",
         "load",
         "save",
         "RemapTarget",
         "Resolved",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nGlobalRemap.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GlobalRemap.kt\ncom/cb/cosmetic/GlobalRemap\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,189:1\n37#2,2:190\n410#3,7:192\n296#4,2:199\n777#4:201\n873#4,2:202\n*S KotlinDebug\n*F\n+ 1 GlobalRemap.kt\ncom/cb/cosmetic/GlobalRemap\n*L\n80#1:190,2\n98#1:192,7\n99#1:199,2\n148#1:201\n148#1:202,2\n*E\n"
)
public final class GlobalRemap {
   @NotNull
   public static final GlobalRemap INSTANCE = new GlobalRemap();
   @NotNull
   private static final LinkedHashMap<String, List<GlobalRemap.RemapTarget>> remaps = new LinkedHashMap<>();
   @NotNull
   private static final File file = new File("config/cb/cb_global_remaps.json");
   private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
   @NotNull
   private static volatile Map<Block, GlobalRemap.Resolved> resolved = MapsKt.emptyMap();

   private GlobalRemap() {
   }

   @NotNull
   public final LinkedHashMap<String, List<GlobalRemap.RemapTarget>> getRemaps$cb_client() {
      return remaps;
   }

   @Nullable
   public final BlockState remapFor(@NotNull Block block, int x, int y, int z) {
      Intrinsics.checkNotNullParameter(block, "block");
      if (!BlockRemapsTab.INSTANCE.getEnabled().getValue()) {
         return null;
      }

      GlobalRemap.Resolved var10000 = resolved.get(block);
      if (var10000 == null) {
         return null;
      }

      GlobalRemap.Resolved r = var10000;
      if (r.getTotal() <= 0) {
         return null;
      }

      if (r.getStates().length == 1) {
         return r.getStates()[0];
      }

      int h = Math.floorMod(x * 73856093 ^ y * 19349663 ^ z * 83492791, r.getTotal());
      int idx = 0;

      while (idx < r.getCumWeights().length && h >= r.getCumWeights()[idx]) {
         idx++;
      }

      return r.getStates()[RangesKt.coerceIn(idx, 0, r.getStates().length - 1)];
   }

   public final boolean hasRemaps() {
      return !resolved.isEmpty();
   }

   public final void rebuild$cb_client() {
      HashMap map = new HashMap();

      for (Entry var3 : remaps.entrySet()) {
         String sourceId = (String)var3.getKey();
         List<GlobalRemap.RemapTarget> targets = (List<GlobalRemap.RemapTarget>)var3.getValue();
         BlockState states = BlockStateCodec.INSTANCE.resolveBlockState(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(sourceId));
         if (states != null) {
            Block cum = states.getBlock();
            if (cum != null) {
               Block sourceBlock = cum;
               ArrayList var15 = new ArrayList(targets.size());
               ArrayList var16 = new ArrayList(targets.size());
               int running = 0;

               for (GlobalRemap.RemapTarget t : targets) {
                  if (t.getWeight() > 0) {
                     BlockState var10000 = BlockStateCodec.INSTANCE.resolveBlockState(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(t.getId()));
                     if (var10000 != null) {
                        BlockState state = var10000;
                        running += t.getWeight();
                        var15.add(state);
                        var16.add(running);
                     }
                  }
               }

               if (!var15.isEmpty()) {
                  Map var17 = map;
                  Collection $this$toTypedArray$iv = var15;
                  int $i$f$toTypedArray = 0;
                  Collection thisCollection$iv = $this$toTypedArray$iv;
                  GlobalRemap.Resolved var19 = new GlobalRemap.Resolved((BlockState[])thisCollection$iv.toArray(new BlockState[0]), CollectionsKt.toIntArray(var16), running);
                  var17.put(sourceBlock, var19);
               }
            }
         }
      }

      resolved = map;
      Minecraft var21 = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(var21, "getInstance(...)");
      Minecraft mc = var21;
      if (mc.level != null) {
         BlockPos center = mc.player != null ? mc.player.blockPosition() : BlockPos.ZERO;
         int dist = mc.options.getEffectiveRenderDistance() * 16 + 16;
         mc.level.setSectionRangeDirty(
            center.getX() - dist, mc.level.getMinY(), center.getZ() - dist, center.getX() + dist, mc.level.getMaxY(), center.getZ() + dist
         );
      }
   }

   @NotNull
   public final List<String> sources() {
      Set var10000 = remaps.keySet();
      Intrinsics.checkNotNullExpressionValue(var10000, "<get-keys>(...)");
      return CollectionsKt.toList(var10000);
   }

   @NotNull
   public final List<GlobalRemap.RemapTarget> targetsOf(@NotNull String source) {
      Intrinsics.checkNotNullParameter(source, "source");
      List var10000 = remaps.get(source);
      if (var10000 != null) {
         var10000 = CollectionsKt.toList(var10000);
         if (var10000 != null) {
            return var10000;
         }
      }

      return CollectionsKt.emptyList();
   }

   public final boolean hasSource(@NotNull String source) {
      Intrinsics.checkNotNullParameter(source, "source");
      return remaps.containsKey(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(source));
   }

   public final void addTarget(@NotNull String source, @NotNull String targetId, int weight) {
      Intrinsics.checkNotNullParameter(source, "source");
      Intrinsics.checkNotNullParameter(targetId, "targetId");
      String src = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(source);
      String tgt = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(targetId);
      Map existing = remaps;
      Object key$iv = src;
      int $i$f$getOrPut = 0;
      Object value$iv = existing.get(key$iv);
      Object var10000;
      if (value$iv == null) {
         int var11/* $VF was: $i$a$-getOrPut-GlobalRemap$addTarget$list$1 */ = 0;
         Object answer$iv = new ArrayList();
         existing.put(key$iv, answer$iv);
         var10000 = answer$iv;
      } else {
         var10000 = value$iv;
      }

      List list = (List)var10000;
      Iterator valueIterator = list.iterator();

      while (true) {
         if (valueIterator.hasNext()) {
            Object element$iv = valueIterator.next();
            GlobalRemap.RemapTarget it = (GlobalRemap.RemapTarget)element$iv;
            int var13/* $VF was: $i$a$-firstOrNull-GlobalRemap$addTarget$existing$1 */ = 0;
            if (!Intrinsics.areEqual(it.getId(), tgt)) {
               continue;
            }

            var10000 = element$iv;
            break;
         }

         var10000 = null;
         break;
      }

      GlobalRemap.RemapTarget existingx = (GlobalRemap.RemapTarget)var10000;
      if (existingx != null) {
         GlobalRemap.RemapTarget var16 = existingx;
         var16.setWeight(var16.getWeight() + weight);
      } else {
         list.add(new GlobalRemap.RemapTarget(tgt, RangesKt.coerceAtLeast(weight, 1)));
      }

      this.rebuild$cb_client();
      this.save();
   }

   public final void setWeight(@NotNull String source, int index, int weight) {
      Intrinsics.checkNotNullParameter(source, "source");
      List var10000 = remaps.get(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(source));
      if (var10000 != null) {
         List list = var10000;
         if (0 <= index ? index < list.size() : false) {
            ((GlobalRemap.RemapTarget)list.get(index)).setWeight(RangesKt.coerceIn(weight, 1, 999));
            this.rebuild$cb_client();
            this.save();
         }
      }
   }

   public final void removeTarget(@NotNull String source, int index) {
      Intrinsics.checkNotNullParameter(source, "source");
      String key = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(source);
      List var10000 = remaps.get(key);
      if (var10000 != null) {
         List list = var10000;
         if (0 <= index ? index < list.size() : false) {
            list.remove(index);
            if (list.isEmpty()) {
               remaps.remove(key);
            }

            this.rebuild$cb_client();
            this.save();
         }
      }
   }

   public final void removeSource(@NotNull String source) {
      Intrinsics.checkNotNullParameter(source, "source");
      if (remaps.remove(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(source)) != null) {
         this.rebuild$cb_client();
         this.save();
      }
   }

   public final void clear() {
      remaps.clear();
      this.rebuild$cb_client();
      this.save();
   }

   @NotNull
   public final String exportJson() {
      String var10000 = gson.toJson(remaps);
      Intrinsics.checkNotNullExpressionValue(var10000, "toJson(...)");
      return var10000;
   }

   public final boolean importJson(@NotNull String json) {
      Intrinsics.checkNotNullParameter(json, "json");

      boolean type;
      try {
         Type typex = (new TypeToken<LinkedHashMap<String, List<GlobalRemap.RemapTarget>>>() {}).getType();
         LinkedHashMap var10000 = gson.fromJson(json, typex);
         if (var10000 == null) {
            return false;
         }

         LinkedHashMap parsed = var10000;
         remaps.clear();

         for (Object var5o : parsed.entrySet()) {
            Entry var5 = (Entry)var5o;
            String src = (String)var5.getKey();
            List<GlobalRemap.RemapTarget> targets = (List<GlobalRemap.RemapTarget>)var5.getValue();
            Iterable $this$filter$iv = targets;
            int $i$f$filter = 0;
            Iterable $this$filterTo$iv$iv = $this$filter$iv;
            var destination$iv$iv = new ArrayList();
            int $i$f$filterTo = 0;

            for (Object element$iv$iv : $this$filterTo$iv$iv) {
               GlobalRemap.RemapTarget it = (GlobalRemap.RemapTarget)element$iv$iv;
               int var17/* $VF was: $i$a$-filter-GlobalRemap$importJson$clean$1 */ = 0;
               if (!StringsKt.isBlank(it.getId()) && it.getWeight() > 0) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            List clean = CollectionsKt.toMutableList((Collection & List)destination$iv$iv);
            if (!clean.isEmpty()) {
               remaps.put(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(src), clean);
            }
         }

         this.rebuild$cb_client();
         this.save();
         type = true;
      } catch (Exception var18/* $VF was: <unused var> */) {
         type = false;
      }

      return type;
   }

   public final void load() {
      remaps.clear();

      try {
         if (file.exists()) {
            String text = FilesKt.readText(file, kotlin.text.Charsets.UTF_8);
            if (!StringsKt.isBlank(text)) {
               Type type = (new TypeToken<LinkedHashMap<String, List<GlobalRemap.RemapTarget>>>() {}).getType();
               LinkedHashMap parsed = gson.fromJson(text, type);
               if (parsed != null) {
                  remaps.putAll(parsed);
               }
            }
         }
      } catch (Exception e) {
         System.err.println("[CB] Failed to load global remaps: " + e.getMessage());
      }

      this.rebuild$cb_client();
   }

   public final void save() {
      try {
         File var10000 = file.getParentFile();
         if (var10000 != null) {
            var10000.mkdirs();
         }

         File tmp = new File(file.getParentFile(), file.getName() + ".tmp");
         String var10001 = gson.toJson(remaps);
         Intrinsics.checkNotNullExpressionValue(var10001, "toJson(...)");
         FilesKt.writeText(tmp, var10001, kotlin.text.Charsets.UTF_8);
         if (file.exists()) {
            file.delete();
         }

         tmp.renameTo(file);
      } catch (Exception e) {
         System.err.println("[CB] Failed to save global remaps: " + e.getMessage());
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0014\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u0016\u001a\u00020\u0005HÖ\u0081\u0004J\n\u0010\u0017\u001a\u00020\u0003HÖ\u0081\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0018",
      d2 = {
            "Lcom/cb/cosmetic/GlobalRemap$RemapTarget;",
            "",
            "id",
            "",
            "weight",
            "",
            "<init>",
            "(Ljava/lang/String;I)V",
            "getId",
            "()Ljava/lang/String;",
            "setId",
            "(Ljava/lang/String;)V",
            "getWeight",
            "()I",
            "setWeight",
            "(I)V",
            "component1",
            "component2",
            "copy",
            "equals",
            "",
            "other",
            "hashCode",
            "toString",
            "cb_client"
      }
   )
   public static final class RemapTarget {
      @NotNull
      private String id;
      private int weight;

      public RemapTarget(@NotNull String id, int weight) {
         Intrinsics.checkNotNullParameter(id, "id");
         super();
         this.id = id;
         this.weight = weight;
      }

      @NotNull
      public final String getId() {
         return this.id;
      }

      public final void setId(@NotNull String var1) {
         Intrinsics.checkNotNullParameter(var1/* $VF was: <set-?> */, "<set-?>");
         this.id = var1/* $VF was: <set-?> */;
      }

      public final int getWeight() {
         return this.weight;
      }

      public final void setWeight(int var1) {
         this.weight = var1/* $VF was: <set-?> */;
      }

      @NotNull
      public final String component1() {
         return this.id;
      }

      public final int component2() {
         return this.weight;
      }

      @NotNull
      public final GlobalRemap.RemapTarget copy(@NotNull String id, int weight) {
         Intrinsics.checkNotNullParameter(id, "id");
         return new GlobalRemap.RemapTarget(id, weight);
      }

      @NotNull
      @Override
      public String toString() {
         return "RemapTarget(id=" + this.id + ", weight=" + this.weight + ")";
      }

      @Override
      public int hashCode() {
         int result = this.id.hashCode();
         return result * 31 + Integer.hashCode(this.weight);
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof GlobalRemap.RemapTarget var2)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.id, var2.id) ? false : this.weight == var2.weight;
         }
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B%\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nR\u0019\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012",
      d2 = {
            "Lcom/cb/cosmetic/GlobalRemap$Resolved;",
            "",
            "states",
            "",
            "Lnet/minecraft/world/level/block/state/BlockState;",
            "cumWeights",
            "",
            "total",
            "",
            "<init>",
            "([Lnet/minecraft/world/level/block/state/BlockState;[II)V",
            "getStates",
            "()[Lnet/minecraft/world/level/block/state/BlockState;",
            "[Lnet/minecraft/world/level/block/state/BlockState;",
            "getCumWeights",
            "()[I",
            "getTotal",
            "()I",
            "cb_client"
      }
   )
   private static final class Resolved {
      @NotNull
      private final BlockState[] states;
      @NotNull
      private final int[] cumWeights;
      private final int total;

      public Resolved(@NotNull BlockState[] states, @NotNull int[] cumWeights, int total) {
         Intrinsics.checkNotNullParameter(states, "states");
         Intrinsics.checkNotNullParameter(cumWeights, "cumWeights");
         super();
         this.states = states;
         this.cumWeights = cumWeights;
         this.total = total;
      }

      @NotNull
      public final BlockState[] getStates() {
         return this.states;
      }

      @NotNull
      public final int[] getCumWeights() {
         return this.cumWeights;
      }

      public final int getTotal() {
         return this.total;
      }
   }
}
