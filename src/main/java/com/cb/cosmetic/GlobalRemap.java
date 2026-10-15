package com.cb.cosmetic;

import com.cb.config.tabs.GlobalCosmeticsTab;
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
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GlobalRemap implements RemapStore {
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
      if (!GlobalCosmeticsTab.INSTANCE.getGlobalRemapEnabled().getValue()) {
         return null;
      }

      if (LocationTracker.INSTANCE.getInDungeon() && ConfigRemap.INSTANCE.isActiveForActiveConfig()) {
         return null;
      }

      GlobalRemap.Resolved r = resolved.get(block);
      if (r == null) {
         return null;
      }

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

      for (Entry entry : remaps.entrySet()) {
         String sourceId = (String)entry.getKey();
         List<GlobalRemap.RemapTarget> targets = (List<GlobalRemap.RemapTarget>)entry.getValue();
         BlockState sourceState = BlockStateCodec.INSTANCE.resolveBlockState(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(sourceId));
         if (sourceState != null) {
            Block sourceBlock = sourceState.getBlock();
            if (sourceBlock != null) {
               ArrayList stateList = new ArrayList(targets.size());
               ArrayList cumWeights = new ArrayList(targets.size());
               int running = 0;

               for (GlobalRemap.RemapTarget t : targets) {
                  if (t.getWeight() > 0) {
                     BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(t.getId()));
                     if (state != null) {
                        running += t.getWeight();
                        stateList.add(state);
                        cumWeights.add(running);
                     }
                  }
               }

               if (!stateList.isEmpty()) {
                  GlobalRemap.Resolved resolvedEntry = new GlobalRemap.Resolved((BlockState[])stateList.toArray(new BlockState[0]), CollectionsKt.toIntArray(cumWeights), running);
                  map.put(sourceBlock, resolvedEntry);
               }
            }
         }
      }

      resolved = map;
      Minecraft mc = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
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
      Set keys = remaps.keySet();
      Intrinsics.checkNotNullExpressionValue(keys, "<get-keys>(...)");
      return CollectionsKt.toList(keys);
   }

   @NotNull
   public final List<GlobalRemap.RemapTarget> targetsOf(@NotNull String source) {
      Intrinsics.checkNotNullParameter(source, "source");
      List targets = remaps.get(source);
      if (targets != null) {
         targets = CollectionsKt.toList(targets);
         if (targets != null) {
            return targets;
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
      Map remapMap = remaps;
      Object key = src;
      Object value = remapMap.get(key);
      Object listValue;
      if (value == null) {
         Object newList = new ArrayList();
         remapMap.put(key, newList);
         listValue = newList;
      } else {
         listValue = value;
      }

      List list = (List)listValue;
      Iterator valueIterator = list.iterator();
      Object found;

      while (true) {
         if (valueIterator.hasNext()) {
            Object element = valueIterator.next();
            GlobalRemap.RemapTarget it = (GlobalRemap.RemapTarget)element;
            if (!Intrinsics.areEqual(it.getId(), tgt)) {
               continue;
            }

            found = element;
            break;
         }

         found = null;
         break;
      }

      GlobalRemap.RemapTarget existingTarget = (GlobalRemap.RemapTarget)found;
      if (existingTarget != null) {
         existingTarget.setWeight(existingTarget.getWeight() + weight);
      } else {
         list.add(new GlobalRemap.RemapTarget(tgt, RangesKt.coerceAtLeast(weight, 1)));
      }

      this.rebuild$cb_client();
      this.save();
   }

   public final void setWeight(@NotNull String source, int index, int weight) {
      Intrinsics.checkNotNullParameter(source, "source");
      List remapList = remaps.get(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(source));
      if (remapList != null) {
         if (0 <= index ? index < remapList.size() : false) {
            ((GlobalRemap.RemapTarget)remapList.get(index)).setWeight(RangesKt.coerceIn(weight, 1, 999));
            this.rebuild$cb_client();
            this.save();
         }
      }
   }

   public final void removeTarget(@NotNull String source, int index) {
      Intrinsics.checkNotNullParameter(source, "source");
      String key = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(source);
      List remapList = remaps.get(key);
      if (remapList != null) {
         if (0 <= index ? index < remapList.size() : false) {
            remapList.remove(index);
            if (remapList.isEmpty()) {
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
      String json = gson.toJson(remaps);
      Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
      return json;
   }

   public final boolean importJson(@NotNull String json) {
      Intrinsics.checkNotNullParameter(json, "json");

      boolean success;
      try {
         Type mapType = (new TypeToken<LinkedHashMap<String, List<GlobalRemap.RemapTarget>>>() {}).getType();
         LinkedHashMap parsed = gson.fromJson(json, mapType);
         if (parsed == null) {
            return false;
         }

         remaps.clear();

         for (Object entryObj : parsed.entrySet()) {
            Entry entry = (Entry)entryObj;
            String src = (String)entry.getKey();
            List<GlobalRemap.RemapTarget> targets = (List<GlobalRemap.RemapTarget>)entry.getValue();
            List filtered = new ArrayList();

            for (Object element : targets) {
               GlobalRemap.RemapTarget target = (GlobalRemap.RemapTarget)element;
               if (!StringsKt.isBlank(target.getId()) && target.getWeight() > 0) {
                  filtered.add(element);
               }
            }

            List clean = CollectionsKt.toMutableList((Collection & List)filtered);
            if (!clean.isEmpty()) {
               remaps.put(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(src), clean);
            }
         }

         this.rebuild$cb_client();
         this.save();
         success = true;
      } catch (Exception ignored) {
         success = false;
      }

      return success;
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
         File parent = file.getParentFile();
         if (parent != null) {
            parent.mkdirs();
         }

         File tmp = new File(file.getParentFile(), file.getName() + ".tmp");
         String json = gson.toJson(remaps);
         Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
         FilesKt.writeText(tmp, json, kotlin.text.Charsets.UTF_8);
         if (file.exists()) {
            file.delete();
         }

         tmp.renameTo(file);
      } catch (Exception e) {
         System.err.println("[CB] Failed to save global remaps: " + e.getMessage());
      }
   }

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

      public final void setId(@NotNull String value) {
         Intrinsics.checkNotNullParameter(value, "<set-?>");
         this.id = value;
      }

      public final int getWeight() {
         return this.weight;
      }

      public final void setWeight(int value) {
         this.weight = value;
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
         } else if (!(other instanceof GlobalRemap.RemapTarget that)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.id, that.id) ? false : this.weight == that.weight;
         }
      }
   }

   static final class Resolved {
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
