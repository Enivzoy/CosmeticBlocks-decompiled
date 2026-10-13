/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.ArrayDeque
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.core.BlockPos
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.cosmetic;

import com.cb.cosmetic.ReplacementCache;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\"\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\r\u0010\u0010\u001a\u00020\u0011H\u0000\u00a2\u0006\u0002\b\u0012J\r\u0010\u0013\u001a\u00020\u0014H\u0000\u00a2\u0006\u0002\b\u0015J%\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u000fH\u0000\u00a2\u0006\u0002\b\u001aJ\u001b\u0010\u001b\u001a\u00020\u00142\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\u001dH\u0000\u00a2\u0006\u0002\b\u001eJ\r\u0010\u001f\u001a\u00020\u0014H\u0000\u00a2\u0006\u0002\b J=\u0010!\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020\u000fH\u0000\u00a2\u0006\u0002\b(J\u0015\u0010)\u001a\u00020\u00142\u0006\u0010*\u001a\u00020+H\u0000\u00a2\u0006\u0002\b,J\u0015\u0010-\u001a\u00020\u00142\u0006\u0010*\u001a\u00020+H\u0000\u00a2\u0006\u0002\b.R\u0014\u0010\u0004\u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\n0\fj\b\u0012\u0004\u0012\u00020\n`\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2={"Lcom/cb/cosmetic/DirtySections;", "", "<init>", "()V", "mc", "Lnet/minecraft/client/Minecraft;", "getMc", "()Lnet/minecraft/client/Minecraft;", "pendingDirtySections", "Lkotlin/collections/ArrayDeque;", "", "pendingDirtySet", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "DIRTY_SECTIONS_PER_TICK", "", "hasPending", "", "hasPending$cb_client", "markAllCachedDirty", "", "markAllCachedDirty$cb_client", "sectionKey", "x", "y", "z", "sectionKey$cb_client", "queueDirtySections", "sections", "", "queueDirtySections$cb_client", "drainDirtySections", "drainDirtySections$cb_client", "markRegionDirtyByBounds", "minX", "minY", "minZ", "maxX", "maxY", "maxZ", "markRegionDirtyByBounds$cb_client", "markDirty", "pos", "Lnet/minecraft/core/BlockPos;", "markDirty$cb_client", "markDirtyImmediate", "markDirtyImmediate$cb_client", "cb_client"})
public final class DirtySections {
    @NotNull
    public static final DirtySections INSTANCE = new DirtySections();
    @NotNull
    private static final ArrayDeque<Long> pendingDirtySections = new ArrayDeque();
    @NotNull
    private static final HashSet<Long> pendingDirtySet = new HashSet();
    private static final int DIRTY_SECTIONS_PER_TICK = 4;

    private DirtySections() {
    }

    private final Minecraft getMc() {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        return minecraft;
    }

    public final boolean hasPending$cb_client() {
        return !((Collection)pendingDirtySections).isEmpty();
    }

    public final void markAllCachedDirty$cb_client() {
        if (this.getMc().level == null) {
            return;
        }
        HashSet<Long> sections = new HashSet<Long>(ReplacementCache.INSTANCE.getActiveCache$cb_client().size() / 4);
        Iterator iterator = ((ConcurrentHashMap.KeySetView)ReplacementCache.INSTANCE.getActiveCache$cb_client().keySet()).iterator();
        Intrinsics.checkNotNullExpressionValue(iterator, (String)"iterator(...)");
        Iterator iterator2 = iterator;
        while (iterator2.hasNext()) {
            Long packed = (Long)iterator2.next();
            Intrinsics.checkNotNull((Object)packed);
            sections.add(this.sectionKey$cb_client(BlockPos.getX((long)packed), BlockPos.getY((long)packed), BlockPos.getZ((long)packed)));
        }
        this.queueDirtySections$cb_client((Set<Long>)sections);
    }

    public final long sectionKey$cb_client(int x, int y, int z) {
        return BlockPos.asLong((int)(x >> 4), (int)(y >> 4), (int)(z >> 4));
    }

    public final void queueDirtySections$cb_client(@NotNull Set<Long> sections) {
        Intrinsics.checkNotNullParameter(sections, (String)"sections");
        Iterator<Long> iterator = sections.iterator();
        while (iterator.hasNext()) {
            long sec = ((Number)iterator.next()).longValue();
            if (!pendingDirtySet.add(sec)) continue;
            pendingDirtySections.addLast((Object)sec);
        }
    }

    public final void drainDirtySections$cb_client() {
        LevelRenderer levelRenderer = this.getMc().levelRenderer;
        if (levelRenderer == null) {
            return;
        }
        LevelRenderer renderer = levelRenderer;
        if (this.getMc().level == null) {
            pendingDirtySections.clear();
            pendingDirtySet.clear();
            return;
        }
        try {
            for (int count = 0; count < 4 && !((Collection)pendingDirtySections).isEmpty(); ++count) {
                long sec = ((Number)pendingDirtySections.removeFirst()).longValue();
                pendingDirtySet.remove(sec);
                renderer.setSectionDirty(BlockPos.getX((long)sec), BlockPos.getY((long)sec), BlockPos.getZ((long)sec));
            }
        }
        catch (Exception exception) {
            pendingDirtySections.clear();
            pendingDirtySet.clear();
        }
    }

    public final void markRegionDirtyByBounds$cb_client(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        if (this.getMc().level == null) {
            return;
        }
        int sx = minX - 1 >> 4;
        int ex = maxX + 1 >> 4;
        int sy = minY - 1 >> 4;
        int ey = maxY + 1 >> 4;
        int sz = minZ - 1 >> 4;
        int ez = maxZ + 1 >> 4;
        HashSet<Long> sections = new HashSet<Long>((ex - sx + 1) * (ey - sy + 1) * (ez - sz + 1));
        int cx = sx;
        if (cx <= ex) {
            while (true) {
                int cy;
                if ((cy = sy) <= ey) {
                    while (true) {
                        int cz;
                        if ((cz = sz) <= ez) {
                            while (true) {
                                sections.add(BlockPos.asLong((int)cx, (int)cy, (int)cz));
                                if (cz == ez) break;
                                ++cz;
                            }
                        }
                        if (cy == ey) break;
                        ++cy;
                    }
                }
                if (cx == ex) break;
                ++cx;
            }
        }
        this.queueDirtySections$cb_client((Set<Long>)sections);
    }

    public final void markDirty$cb_client(@NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        if (this.getMc().level == null) {
            return;
        }
        HashSet<Long> sections = new HashSet<Long>(8);
        for (int dx = -1; dx < 2; ++dx) {
            for (int dy = -1; dy < 2; ++dy) {
                for (int dz = -1; dz < 2; ++dz) {
                    sections.add(this.sectionKey$cb_client(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz));
                }
            }
        }
        this.queueDirtySections$cb_client((Set<Long>)sections);
    }

    public final void markDirtyImmediate$cb_client(@NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        LevelRenderer levelRenderer = this.getMc().levelRenderer;
        if (levelRenderer == null) {
            return;
        }
        LevelRenderer renderer = levelRenderer;
        if (this.getMc().level == null) {
            return;
        }
        HashSet<Long> sections = new HashSet<Long>(8);
        for (int dx = -1; dx < 2; ++dx) {
            for (int dy = -1; dy < 2; ++dy) {
                for (int dz = -1; dz < 2; ++dz) {
                    sections.add(this.sectionKey$cb_client(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz));
                }
            }
        }
        try {
            Iterator iterator = sections.iterator();
            Intrinsics.checkNotNullExpressionValue(iterator, (String)"iterator(...)");
            Iterator iterator2 = iterator;
            while (iterator2.hasNext()) {
                Object e = iterator2.next();
                Intrinsics.checkNotNullExpressionValue(e, (String)"next(...)");
                long sec = ((Number)e).longValue();
                renderer.setSectionDirty(BlockPos.getX((long)sec), BlockPos.getY((long)sec), BlockPos.getZ((long)sec));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}


