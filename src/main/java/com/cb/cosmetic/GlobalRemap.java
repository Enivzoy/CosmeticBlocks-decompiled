/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.io.FilesKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  kotlin.text.StringsKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.cosmetic;

import com.cb.config.tabs.BlockRemapsTab;
import com.cb.cosmetic.BlockStateCodec;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0013\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u000234B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J(\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0019J\u0006\u0010\u001c\u001a\u00020\u001dJ\r\u0010\u001e\u001a\u00020\u001fH\u0000\u00a2\u0006\u0002\b J\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00060\"J\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020\b0\"2\u0006\u0010$\u001a\u00020\u0006J\u000e\u0010%\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020\u0006J \u0010&\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u00062\u0006\u0010'\u001a\u00020\u00062\b\b\u0002\u0010(\u001a\u00020\u0019J\u001e\u0010)\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\u0019J\u0016\u0010+\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0019J\u000e\u0010,\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u0006J\u0006\u0010-\u001a\u00020\u001fJ\u0006\u0010.\u001a\u00020\u0006J\u000e\u0010/\u001a\u00020\u001d2\u0006\u00100\u001a\u00020\u0006J\u0006\u00101\u001a\u00020\u001fJ\u0006\u00102\u001a\u00020\u001fR<\u0010\u0004\u001a*\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005j\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007`\tX\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n \u0010*\u0004\u0018\u00010\u000f0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00065"}, d2={"Lcom/cb/cosmetic/GlobalRemap;", "", "<init>", "()V", "remaps", "Ljava/util/LinkedHashMap;", "", "", "Lcom/cb/cosmetic/GlobalRemap$RemapTarget;", "Lkotlin/collections/LinkedHashMap;", "getRemaps$cb_client", "()Ljava/util/LinkedHashMap;", "file", "Ljava/io/File;", "gson", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "resolved", "", "Lnet/minecraft/world/level/block/Block;", "Lcom/cb/cosmetic/GlobalRemap$Resolved;", "remapFor", "Lnet/minecraft/world/level/block/state/BlockState;", "block", "x", "", "y", "z", "hasRemaps", "", "rebuild", "", "rebuild$cb_client", "sources", "", "targetsOf", "source", "hasSource", "addTarget", "targetId", "weight", "setWeight", "index", "removeTarget", "removeSource", "clear", "exportJson", "importJson", "json", "load", "save", "RemapTarget", "Resolved", "cb_client"})
@SourceDebugExtension(value={"SMAP\nGlobalRemap.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GlobalRemap.kt\ncom/cb/cosmetic/GlobalRemap\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,189:1\n37#2,2:190\n410#3,7:192\n296#4,2:199\n777#4:201\n873#4,2:202\n*S KotlinDebug\n*F\n+ 1 GlobalRemap.kt\ncom/cb/cosmetic/GlobalRemap\n*L\n80#1:190,2\n98#1:192,7\n99#1:199,2\n148#1:201\n148#1:202,2\n*E\n"})
public final class GlobalRemap {
    @NotNull
    public static final GlobalRemap INSTANCE = new GlobalRemap();
    @NotNull
    private static final LinkedHashMap<String, List<RemapTarget>> remaps = new LinkedHashMap();
    @NotNull
    private static final File file = new File("config/cb/cb_global_remaps.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @NotNull
    private static volatile Map<Block, Resolved> resolved = MapsKt.emptyMap();

    private GlobalRemap() {
    }

    @NotNull
    public final LinkedHashMap<String, List<RemapTarget>> getRemaps$cb_client() {
        return remaps;
    }

    @Nullable
    public final BlockState remapFor(@NotNull Block block, int x, int y, int z) {
        int idx;
        Intrinsics.checkNotNullParameter((Object)block, (String)"block");
        if (!((Boolean)BlockRemapsTab.INSTANCE.getEnabled().getValue()).booleanValue()) {
            return null;
        }
        Resolved resolved = GlobalRemap.resolved.get(block);
        if (resolved == null) {
            return null;
        }
        Resolved r = resolved;
        if (r.getTotal() <= 0) {
            return null;
        }
        if (r.getStates().length == 1) {
            return r.getStates()[0];
        }
        int h = Math.floorMod(x * 73856093 ^ y * 19349663 ^ z * 83492791, r.getTotal());
        for (idx = 0; idx < r.getCumWeights().length && h >= r.getCumWeights()[idx]; ++idx) {
        }
        return r.getStates()[RangesKt.coerceIn((int)idx, (int)0, (int)(r.getStates().length - 1))];
    }

    public final boolean hasRemaps() {
        return !resolved.isEmpty();
    }

    public final void rebuild$cb_client() {
        HashMap map = new HashMap();
        for (Map.Entry entry : ((Map)remaps).entrySet()) {
            Block block;
            String sourceId = (String)entry.getKey();
            List targets = (List)entry.getValue();
            BlockState blockState = BlockStateCodec.INSTANCE.resolveBlockState(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(sourceId));
            if (blockState == null || (block = blockState.getBlock()) == null) continue;
            Block sourceBlock = block;
            ArrayList<BlockState> states = new ArrayList<BlockState>(targets.size());
            ArrayList<Integer> cum = new ArrayList<Integer>(targets.size());
            int running = 0;
            for (RemapTarget t : targets) {
                BlockState state;
                if (t.getWeight() <= 0 || BlockStateCodec.INSTANCE.resolveBlockState(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(t.getId())) == null) continue;
                states.add(state);
                cum.add(running += t.getWeight());
            }
            if (!(!((Collection)states).isEmpty())) continue;
            Map map2 = map;
            Collection $this$toTypedArray$iv = states;
            boolean $i$f$toTypedArray = false;
            Collection thisCollection$iv = $this$toTypedArray$iv;
            Resolved resolved = new Resolved(thisCollection$iv.toArray(new BlockState[0]), CollectionsKt.toIntArray((Collection)cum), running);
            map2.put(sourceBlock, resolved);
        }
        resolved = map;
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        Minecraft mc = minecraft;
        if (mc.level != null) {
            mc.levelRenderer.allChanged();
        }
    }

    @NotNull
    public final List<String> sources() {
        Set<String> set = remaps.keySet();
        Intrinsics.checkNotNullExpressionValue(set, (String)"<get-keys>(...)");
        return CollectionsKt.toList((Iterable)set);
    }

    @NotNull
    public final List<RemapTarget> targetsOf(@NotNull String source) {
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        List list = remaps.get(source);
        if (list == null || (list = CollectionsKt.toList((Iterable)list)) == null) {
            list = CollectionsKt.emptyList();
        }
        return list;
    }

    public final boolean hasSource(@NotNull String source) {
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        return remaps.containsKey(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(source));
    }

    /*
     * WARNING - void declaration
     */
    public final void addTarget(@NotNull String source, @NotNull String targetId, int weight) {
        RemapTarget existing;
        Object v1;
        List list;
        String tgt;
        block5: {
            Object object;
            void $this$getOrPut$iv;
            Intrinsics.checkNotNullParameter((Object)source, (String)"source");
            Intrinsics.checkNotNullParameter((Object)targetId, (String)"targetId");
            String src = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(source);
            tgt = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(targetId);
            Map map = remaps;
            String key$iv = src;
            boolean $i$f$getOrPut = false;
            Object value$iv = $this$getOrPut$iv.get(key$iv);
            if (value$iv == null) {
                boolean bl = false;
                List answer$iv = new ArrayList();
                $this$getOrPut$iv.put(key$iv, answer$iv);
                object = answer$iv;
            } else {
                object = value$iv;
            }
            list = (List)object;
            Iterable $this$firstOrNull$iv = list;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                RemapTarget it = (RemapTarget)element$iv;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getId(), (Object)tgt)) continue;
                v1 = element$iv;
                break block5;
            }
            v1 = null;
        }
        RemapTarget remapTarget = existing = (RemapTarget)v1;
        if (remapTarget != null) {
            RemapTarget remapTarget2 = remapTarget;
            remapTarget2.setWeight(remapTarget2.getWeight() + weight);
        } else {
            list.add(new RemapTarget(tgt, RangesKt.coerceAtLeast((int)weight, (int)1)));
        }
        this.rebuild$cb_client();
        this.save();
    }

    public static /* synthetic */ void addTarget$default(GlobalRemap globalRemap, String string, String string2, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = 1;
        }
        globalRemap.addTarget(string, string2, n);
    }

    public final void setWeight(@NotNull String source, int index, int weight) {
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        List<RemapTarget> list = remaps.get(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(source));
        if (list == null) {
            return;
        }
        List<RemapTarget> list2 = list;
        boolean bl = 0 <= index ? index < ((Collection)list2).size() : false;
        if (bl) {
            list2.get(index).setWeight(RangesKt.coerceIn((int)weight, (int)1, (int)999));
            this.rebuild$cb_client();
            this.save();
        }
    }

    public final void removeTarget(@NotNull String source, int index) {
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        String key = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(source);
        List<RemapTarget> list = remaps.get(key);
        if (list == null) {
            return;
        }
        List<RemapTarget> list2 = list;
        boolean bl = 0 <= index ? index < ((Collection)list2).size() : false;
        if (bl) {
            list2.remove(index);
            if (list2.isEmpty()) {
                remaps.remove(key);
            }
            this.rebuild$cb_client();
            this.save();
        }
    }

    public final void removeSource(@NotNull String source) {
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
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
        String string = gson.toJson(remaps);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"toJson(...)");
        return string;
    }

    /*
     * WARNING - void declaration
     */
    public final boolean importJson(@NotNull String json) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        try {
            Type type2 = new TypeToken<LinkedHashMap<String, List<RemapTarget>>>(){}.getType();
            LinkedHashMap linkedHashMap = (LinkedHashMap)gson.fromJson(json, type2);
            if (linkedHashMap == null) {
                return false;
            }
            LinkedHashMap parsed = linkedHashMap;
            remaps.clear();
            for (Map.Entry entry : ((Map)parsed).entrySet()) {
                void $this$filterTo$iv$iv;
                String src = (String)entry.getKey();
                List targets = (List)entry.getValue();
                Iterable $this$filter$iv = targets;
                boolean $i$f$filter = false;
                Iterable iterable = $this$filter$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterTo = false;
                for (Object element$iv$iv : $this$filterTo$iv$iv) {
                    RemapTarget it = (RemapTarget)element$iv$iv;
                    boolean bl2 = false;
                    if (!(!StringsKt.isBlank((CharSequence)it.getId()) && it.getWeight() > 0)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                List clean = CollectionsKt.toMutableList((Collection)((List)destination$iv$iv));
                if (!(!((Collection)clean).isEmpty())) continue;
                ((Map)remaps).put(BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(src), clean);
            }
            this.rebuild$cb_client();
            this.save();
            bl = true;
        }
        catch (Exception exception) {
            bl = false;
        }
        return bl;
    }

    public final void load() {
        remaps.clear();
        try {
            Type type2;
            LinkedHashMap parsed;
            String text;
            if (file.exists() && !StringsKt.isBlank((CharSequence)(text = FilesKt.readText$default((File)file, null, (int)1, null))) && (parsed = (LinkedHashMap)gson.fromJson(text, type2 = new TypeToken<LinkedHashMap<String, List<RemapTarget>>>(){}.getType())) != null) {
                remaps.putAll(parsed);
            }
        }
        catch (Exception e) {
            System.err.println("[CB] Failed to load global remaps: " + e.getMessage());
        }
        this.rebuild$cb_client();
    }

    public final void save() {
        try {
            File file = GlobalRemap.file.getParentFile();
            if (file != null) {
                file.mkdirs();
            }
            File tmp = new File(GlobalRemap.file.getParentFile(), GlobalRemap.file.getName() + ".tmp");
            String string = gson.toJson(remaps);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"toJson(...)");
            FilesKt.writeText$default((File)tmp, (String)string, null, (int)2, null);
            if (GlobalRemap.file.exists()) {
                GlobalRemap.file.delete();
            }
            tmp.renameTo(GlobalRemap.file);
        }
        catch (Exception e) {
            System.err.println("[CB] Failed to save global remaps: " + e.getMessage());
        }
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0014\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0083\u0004J\n\u0010\u0016\u001a\u00020\u0005H\u00d6\u0081\u0004J\n\u0010\u0017\u001a\u00020\u0003H\u00d6\u0081\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0018"}, d2={"Lcom/cb/cosmetic/GlobalRemap$RemapTarget;", "", "id", "", "weight", "", "<init>", "(Ljava/lang/String;I)V", "getId", "()Ljava/lang/String;", "setId", "(Ljava/lang/String;)V", "getWeight", "()I", "setWeight", "(I)V", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "cb_client"})
    public static final class RemapTarget {
        @NotNull
        private String id;
        private int weight;

        public RemapTarget(@NotNull String id, int weight) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            this.id = id;
            this.weight = weight;
        }

        public /* synthetic */ RemapTarget(String string, int n, int n2, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n2 & 2) != 0) {
                n = 1;
            }
            this(string, n);
        }

        @NotNull
        public final String getId() {
            return this.id;
        }

        public final void setId(@NotNull String string) {
            Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
            this.id = string;
        }

        public final int getWeight() {
            return this.weight;
        }

        public final void setWeight(int n) {
            this.weight = n;
        }

        @NotNull
        public final String component1() {
            return this.id;
        }

        public final int component2() {
            return this.weight;
        }

        @NotNull
        public final RemapTarget copy(@NotNull String id, int weight) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            return new RemapTarget(id, weight);
        }

        public static /* synthetic */ RemapTarget copy$default(RemapTarget remapTarget, String string, int n, int n2, Object object) {
            if ((n2 & 1) != 0) {
                string = remapTarget.id;
            }
            if ((n2 & 2) != 0) {
                n = remapTarget.weight;
            }
            return remapTarget.copy(string, n);
        }

        @NotNull
        public String toString() {
            return "RemapTarget(id=" + this.id + ", weight=" + this.weight + ")";
        }

        public int hashCode() {
            int result = this.id.hashCode();
            result = result * 31 + Integer.hashCode(this.weight);
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof RemapTarget)) {
                return false;
            }
            RemapTarget remapTarget = (RemapTarget)other;
            if (!Intrinsics.areEqual((Object)this.id, (Object)remapTarget.id)) {
                return false;
            }
            return this.weight == remapTarget.weight;
        }
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B%\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0004\b\t\u0010\nR\u0019\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0012"}, d2={"Lcom/cb/cosmetic/GlobalRemap$Resolved;", "", "states", "", "Lnet/minecraft/world/level/block/state/BlockState;", "cumWeights", "", "total", "", "<init>", "([Lnet/minecraft/world/level/block/state/BlockState;[II)V", "getStates", "()[Lnet/minecraft/world/level/block/state/BlockState;", "[Lnet/minecraft/world/level/block/state/BlockState;", "getCumWeights", "()[I", "getTotal", "()I", "cb_client"})
    private static final class Resolved {
        @NotNull
        private final BlockState[] states;
        @NotNull
        private final int[] cumWeights;
        private final int total;

        public Resolved(@NotNull BlockState[] states, @NotNull int[] cumWeights, int total) {
            Intrinsics.checkNotNullParameter((Object)states, (String)"states");
            Intrinsics.checkNotNullParameter((Object)cumWeights, (String)"cumWeights");
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


