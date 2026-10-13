package com.cb.cosmetic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

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
      Minecraft mc = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
      return mc;
   }

   public final boolean hasPending$cb_client() {
      return !pendingDirtySections.isEmpty();
   }

   public final void markAllCachedDirty$cb_client() {
      if (this.getMc().level != null) {
         HashSet sections = new HashSet(ReplacementCache.INSTANCE.getActiveCache$cb_client().size() / 4);

         for (Long packed : ReplacementCache.INSTANCE.getActiveCache$cb_client().keySet()) {
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

      for (long sec : sections) {
         if (pendingDirtySet.add(sec)) {
            pendingDirtySections.addLast(sec);
         }
      }
   }

   public final void drainDirtySections$cb_client() {
      LevelRenderer renderer = this.getMc().levelRenderer;
      if (renderer != null) {
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
            } catch (Exception ignored) {
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
      LevelRenderer renderer = this.getMc().levelRenderer;
      if (renderer != null) {
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
               Iterator iterator = sections.iterator();
               Intrinsics.checkNotNullExpressionValue(iterator, "iterator(...)");

               while (iterator.hasNext()) {
                  Object value = iterator.next();
                  Intrinsics.checkNotNullExpressionValue(value, "next(...)");
                  long sec = ((Number)value).longValue();
                  this.getMc().level.setSectionDirtyWithNeighbors(BlockPos.getX(sec), BlockPos.getY(sec), BlockPos.getZ(sec));
               }
            } catch (Exception ignored) {
            }
         }
      }
   }
}
