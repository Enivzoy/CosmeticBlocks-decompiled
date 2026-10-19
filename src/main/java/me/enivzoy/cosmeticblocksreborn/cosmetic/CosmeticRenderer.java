package me.enivzoy.cosmeticblocksreborn.cosmetic;

import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class CosmeticRenderer {
    public static final CosmeticRenderer INSTANCE = new CosmeticRenderer();

    private CosmeticRenderer() {
    }

    private static Minecraft mc() {
        return Minecraft.getInstance();
    }

    private static boolean enabled() {
        return (Boolean) CosmeticBlocksTab.INSTANCE.getEnabled().getValue();
    }

    public void onWorldRender(Matrix4f modelView, Matrix4f projection, Vec3 camPos) {
    }

    public void emitGizmos() {
        if (!enabled()) {
            return;
        }
        BlockPos p1 = WorldEditMode.INSTANCE.getPos1();
        BlockPos p2 = WorldEditMode.INSTANCE.getPos2();
        try {
            if (p1 != null || p2 != null) {
                if (p1 != null && p2 != null) {
                    AABB box = new AABB(
                            Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()), Math.min(p1.getZ(), p2.getZ()),
                            Math.max(p1.getX(), p2.getX()) + 1.0, Math.max(p1.getY(), p2.getY()) + 1.0, Math.max(p1.getZ(), p2.getZ()) + 1.0);
                    Gizmos.cuboid(box, GizmoStyle.strokeAndFill(0xCC4DFF4D, 2.0f, 0x264DFF4D)).setAlwaysOnTop();
                } else {
                    BlockPos p = p1 != null ? p1 : p2;
                    Gizmos.cuboid(p, GizmoStyle.stroke(0xCCFFFF00, 2.0f)).setAlwaysOnTop();
                }
            }
            if (p1 != null && p2 != null
                    && (Boolean) CosmeticBlocksTab.INSTANCE.getShowReplacedBlocks().getValue()
                    && !ReplacementCache.INSTANCE.getActiveCache$cb_client().isEmpty()) {
                int selMinX = Math.min(p1.getX(), p2.getX());
                int selMinY = Math.min(p1.getY(), p2.getY());
                int selMinZ = Math.min(p1.getZ(), p2.getZ());
                int selMaxX = Math.max(p1.getX(), p2.getX());
                int selMaxY = Math.max(p1.getY(), p2.getY());
                int selMaxZ = Math.max(p1.getZ(), p2.getZ());
                double range = ((Number) CosmeticBlocksTab.INSTANCE.getOverlayRange().getValue()).doubleValue();
                double rangeSq = range * range;
                int color = ((Number) CosmeticBlocksTab.INSTANCE.getOverlayColor().getValue()).intValue();
                int argb = 0x99 | (color & 0xFFFFFF);
                Vec3 cam = mc().player != null ? mc().player.getEyePosition(1.0f) : Vec3.ZERO;
                for (Long packed : ReplacementCache.INSTANCE.getActiveCache$cb_client().keySet()) {
                    int bx = BlockPos.getX(packed);
                    int by = BlockPos.getY(packed);
                    int bz = BlockPos.getZ(packed);
                    if (bx < selMinX || bx > selMaxX || by < selMinY || by > selMaxY || bz < selMinZ || bz > selMaxZ) {
                        continue;
                    }
                    double dx = bx + 0.5 - cam.x;
                    double dy = by + 0.5 - cam.y;
                    double dz = bz + 0.5 - cam.z;
                    if (dx * dx + dy * dy + dz * dz > rangeSq) {
                        continue;
                    }
                    Gizmos.cuboid(new AABB(bx, by, bz, bx + 1.0, by + 1.0, bz + 1.0), GizmoStyle.stroke(argb, 1.0f)).setAlwaysOnTop();
                }
            }
        } catch (IllegalStateException ignored) {
        }
    }
}
