package com.cb.cosmetic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import kotlin.Metadata;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\"\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\r\u0010\u0010\u001a\u00020\u0011H\u0000¢\u0006\u0002\b\u0012J\r\u0010\u0013\u001a\u00020\u0014H\u0000¢\u0006\u0002\b\u0015J%\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u000fH\u0000¢\u0006\u0002\b\u001aJ\u001b\u0010\u001b\u001a\u00020\u00142\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\u001dH\u0000¢\u0006\u0002\b\u001eJ\r\u0010\u001f\u001a\u00020\u0014H\u0000¢\u0006\u0002\b J=\u0010!\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020\u000fH\u0000¢\u0006\u0002\b(J\u0015\u0010)\u001a\u00020\u00142\u0006\u0010*\u001a\u00020+H\u0000¢\u0006\u0002\b,J\u0015\u0010-\u001a\u00020\u00142\u0006\u0010*\u001a\u00020+H\u0000¢\u0006\u0002\b.R\u0014\u0010\u0004\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\n0\fj\b\u0012\u0004\u0012\u00020\n`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082T¢\u0006\u0002\n\u0000¨\u0006/",
   d2 = {
         "Lcom/cb/cosmetic/DirtySections;",
         "",
         "<init>",
         "()V",
         "mc",
         "Lnet/minecraft/client/Minecraft;",
         "getMc",
         "()Lnet/minecraft/client/Minecraft;",
         "pendingDirtySections",
         "Lkotlin/collections/ArrayDeque;",
         "",
         "pendingDirtySet",
         "Ljava/util/HashSet;",
         "Lkotlin/collections/HashSet;",
         "DIRTY_SECTIONS_PER_TICK",
         "",
         "hasPending",
         "",
         "hasPending$cb_client",
         "markAllCachedDirty",
         "",
         "markAllCachedDirty$cb_client",
         "sectionKey",
         "x",
         "y",
         "z",
         "sectionKey$cb_client",
         "queueDirtySections",
         "sections",
         "",
         "queueDirtySections$cb_client",
         "drainDirtySections",
         "drainDirtySections$cb_client",
         "markRegionDirtyByBounds",
         "minX",
         "minY",
         "minZ",
         "maxX",
         "maxY",
         "maxZ",
         "markRegionDirtyByBounds$cb_client",
         "markDirty",
         "pos",
         "Lnet/minecraft/core/BlockPos;",
         "markDirty$cb_client",
         "markDirtyImmediate",
         "markDirtyImmediate$cb_client",
         "cb_client"
   }
)
public final class DirtySections {
   @NotNull
   public static final DirtySections INSTANCE = new DirtySections();
   @NotNull
   private static final ArrayDeque<Long> pendingDirtySections = new ArrayDeque<>();
   @NotNull
   private static final HashSet<Long> pendingDirtySet = new HashSet<>();
   private static final int DIRTY_SECTIONS_PER_TICK = 4;

   private DirtySections() {
   }

   private final Minecraft getMc() {
      Minecraft var10000 = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(var10000, "getInstance(...)");
      return var10000;
   }

   public final boolean hasPending$cb_client() {
      return !pendingDirtySections.isEmpty();
   }

   public final void markAllCachedDirty$cb_client() {
      if (this.getMc().level != null) {
         HashSet sections = new HashSet(ReplacementCache.INSTANCE.getActiveCache$cb_client().size() / 4);
         Iterator var10000 = ((KeySetView)ReplacementCache.INSTANCE.getActiveCache$cb_client().keySet()).iterator();
         Intrinsics.checkNotNullExpressionValue(var10000, "iterator(...)");
         Iterator var2 = var10000;

         while (var2.hasNext()) {
            Long packed = (Long)var2.next();
            Intrinsics.checkNotNull(packed);
            sections.add(this.sectionKey$cb_client(BlockPos.getX(packed), BlockPos.getY(packed), BlockPos.getZ(packed)));
         }

         this.queueDirtySections$cb_client(sections);
      }
   }

   public final long sectionKey$cb_client(int x, int y, int z) {
      return BlockPos.asLong(x >> 4, y >> 4, z >> 4);
   }

   public final void queueDirtySections$cb_client(@NotNull Set<Long> sections) {
      Intrinsics.checkNotNullParameter(sections, "sections");
      Iterator var2 = sections.iterator();

      while (var2.hasNext()) {
         long sec = ((Number)var2.next()).longValue();
         if (pendingDirtySet.add(sec)) {
            pendingDirtySections.addLast(sec);
         }
      }
   }

   public final void drainDirtySections$cb_client() {
      LevelRenderer var10000 = this.getMc().levelRenderer;
      if (var10000 != null) {
         LevelRenderer renderer = var10000;
         if (this.getMc().level == null) {
            pendingDirtySections.clear();
            pendingDirtySet.clear();
         } else {
            try {
               for (int count = 0; count < 4 && !pendingDirtySections.isEmpty(); count++) {
                  long sec = pendingDirtySections.removeFirst().longValue();
                  pendingDirtySet.remove(sec);
                  this.getMc().level.setSectionDirtyWithNeighbors(BlockPos.getX(sec), BlockPos.getY(sec), BlockPos.getZ(sec));
               }
            } catch (Exception var5/* $VF was: <unused var> */) {
               pendingDirtySections.clear();
               pendingDirtySet.clear();
            }
         }
      }
   }

   public final void markRegionDirtyByBounds$cb_client(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
      if (this.getMc().level != null) {
         int sx = minX - 1 >> 4;
         int ex = maxX + 1 >> 4;
         int sy = minY - 1 >> 4;
         int ey = maxY + 1 >> 4;
         int sz = minZ - 1 >> 4;
         int ez = maxZ + 1 >> 4;
         HashSet sections = new HashSet((ex - sx + 1) * (ey - sy + 1) * (ez - sz + 1));
         int cx = sx;
         if (cx <= ex) {
            while (true) {
               int cy = sy;
               if (cy <= ey) {
                  while (true) {
                     int cz = sz;
                     if (cz <= ez) {
                        while (true) {
                           sections.add(BlockPos.asLong(cx, cy, cz));
                           if (cz == ez) {
                              break;
                           }

                           cz++;
                        }
                     }

                     if (cy == ey) {
                        break;
                     }

                     cy++;
                  }
               }

               if (cx == ex) {
                  break;
               }

               cx++;
            }
         }

         this.queueDirtySections$cb_client(sections);
      }
   }

   public final void markDirty$cb_client(@NotNull BlockPos pos) {
      Intrinsics.checkNotNullParameter(pos, "pos");
      if (this.getMc().level != null) {
         HashSet sections = new HashSet(8);

         for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
               for (int dz = -1; dz < 2; dz++) {
                  sections.add(this.sectionKey$cb_client(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz));
               }
            }
         }

         this.queueDirtySections$cb_client(sections);
      }
   }

   public final void markDirtyImmediate$cb_client(@NotNull BlockPos pos) {
      Intrinsics.checkNotNullParameter(pos, "pos");
      LevelRenderer var10000 = this.getMc().levelRenderer;
      if (var10000 != null) {
         LevelRenderer renderer = var10000;
         if (this.getMc().level != null) {
            HashSet sections = new HashSet(8);

            for (int dx = -1; dx < 2; dx++) {
               for (int dy = -1; dy < 2; dy++) {
                  for (int dz = -1; dz < 2; dz++) {
                     sections.add(this.sectionKey$cb_client(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz));
                  }
               }
            }

            try {
               Iterator var10 = sections.iterator();
               Intrinsics.checkNotNullExpressionValue(var10, "iterator(...)");
               Iterator var8 = var10;

               while (var8.hasNext()) {
                  Object var11 = var8.next();
                  Intrinsics.checkNotNullExpressionValue(var11, "next(...)");
                  long sec = ((Number)var11).longValue();
                  this.getMc().level.setSectionDirtyWithNeighbors(BlockPos.getX(sec), BlockPos.getY(sec), BlockPos.getZ(sec));
               }
            } catch (Exception var7) {
            }
         }
      }
   }
}
