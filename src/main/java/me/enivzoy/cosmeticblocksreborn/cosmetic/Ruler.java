package me.enivzoy.cosmeticblocksreborn.cosmetic;

import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab;
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

public final class Ruler {
    public static final Ruler INSTANCE = new Ruler();

    private static boolean active;
    private static BlockPos startPos;
    private static List<BlockPos> previewPositions = Collections.emptyList();
    private static double airReach = 4.0;
    private static BlockPos reachTargetPos;
    private static boolean rulerKeyWasPressed;
    private static boolean rightWasPressed;
    private static boolean leftWasPressed;

    private Ruler() {
    }

    private static Minecraft mc() {
        return Minecraft.getInstance();
    }

    private static boolean enabled() {
        return (Boolean) CosmeticBlocksTab.INSTANCE.getEnabled().getValue();
    }

    private static int rulerKeybind() {
        return ((Number) CosmeticBlocksTab.INSTANCE.getRulerKeybind().getValue()).intValue();
    }

    public boolean getActive() {
        return active;
    }

    public boolean onScroll(double scrollY) {
        if (!active || mc().gui.screen() != null) {
            return false;
        }
        airReach = Math.max(1.0, Math.min(100.0, airReach + scrollY));
        ChatUtil.INSTANCE.success("Ruler reach: " + (int) airReach);
        return true;
    }

    public void tick() {
        if (!enabled()) {
            if (active) {
                active = false;
                startPos = null;
                previewPositions = Collections.emptyList();
                reachTargetPos = null;
            }
            return;
        }
        long window = Minecraft.getInstance().getWindow().handle();
        if (window == 0L) {
            return;
        }
        if (mc().gui.screen() == null && rulerKeybind() != -1) {
            boolean pressed = GLFW.glfwGetKey(window, rulerKeybind()) == 1;
            if (pressed && !rulerKeyWasPressed) {
                active = !active;
                if (!active) {
                    startPos = null;
                    previewPositions = Collections.emptyList();
                    reachTargetPos = null;
                }
                ChatUtil.INSTANCE.send(active ? "Ruler mode \u00a7aON \u00a77(reach: " + (int) airReach + ")" : "Ruler mode \u00a7cOFF");
            }
            rulerKeyWasPressed = pressed;
        } else {
            rulerKeyWasPressed = false;
        }
        if (!active || mc().gui.screen() != null) {
            return;
        }
        reachTargetPos = getAirTargetPos();
        BlockPos start = startPos;
        if (start != null) {
            BlockPos endPos = getTargetPos();
            if (endPos != null) {
                previewPositions = bresenham3D(start, endPos);
            }
        }
        boolean leftPressed = GLFW.glfwGetMouseButton(window, 0) == 1;
        boolean rightPressed = GLFW.glfwGetMouseButton(window, 1) == 1;
        if (rightPressed && !rightWasPressed) {
            if (start == null) {
                BlockPos endPos2 = getTargetPos();
                if (endPos2 != null) {
                    startPos = endPos2;
                    ChatUtil.INSTANCE.success("Ruler: Start set at " + endPos2.getX() + ", " + endPos2.getY() + ", " + endPos2.getZ());
                }
            } else if (!previewPositions.isEmpty()) {
                for (BlockPos blockPos : previewPositions) {
                    CosmeticBlocks.INSTANCE.setReplacement(blockPos, CosmeticBlocks.INSTANCE.getSelectedBlock(), (Boolean) CosmeticBlocksTab.INSTANCE.getPlaceMode().getValue());
                }
                ChatUtil.INSTANCE.success("Ruler: Placed " + previewPositions.size() + " blocks");
                startPos = null;
                previewPositions = Collections.emptyList();
            }
        }
        if (leftPressed && !leftWasPressed && start != null) {
            startPos = null;
            previewPositions = Collections.emptyList();
            ChatUtil.INSTANCE.warning("Ruler: Cancelled");
        }
        leftWasPressed = leftPressed;
        rightWasPressed = rightPressed;
        emitGizmos();
    }

    public void onWorldRender(Matrix4f modelView, Matrix4f projection, Vec3 camPos) {
    }

    private static void emitGizmos() {
        if (!active) {
            return;
        }
        try {
            if (reachTargetPos != null && startPos == null) {
                Gizmos.cuboid(reachTargetPos, GizmoStyle.stroke(0x9980FF80)).setAlwaysOnTop();
            }
            for (BlockPos blockPos : previewPositions) {
                Gizmos.cuboid(blockPos, GizmoStyle.fill(0x59FFFFFF)).setAlwaysOnTop();
            }
        } catch (IllegalStateException ignored) {
        }
    }

    private static BlockPos getTargetPos() {
        HitResult hit = mc().hitResult;
        if (hit instanceof BlockHitResult && ((BlockHitResult) hit).getType() == HitResult.Type.BLOCK) {
            return ((BlockHitResult) hit).getBlockPos();
        }
        return getAirTargetPos();
    }

    private static BlockPos getAirTargetPos() {
        LocalPlayer player = mc().player;
        if (player == null) {
            return null;
        }
        Vec3 eye = player.getEyePosition(1.0f);
        Vec3 look = player.getLookAngle();
        Vec3 target = eye.add(look.x * airReach, look.y * airReach, look.z * airReach);
        return new BlockPos((int) Math.floor(target.x), (int) Math.floor(target.y), (int) Math.floor(target.z));
    }

    private static List<BlockPos> bresenham3D(BlockPos start, BlockPos end) {
        List<BlockPos> result = new ArrayList<BlockPos>();
        int x = start.getX();
        int y = start.getY();
        int z = start.getZ();
        int x2 = end.getX();
        int y2 = end.getY();
        int z2 = end.getZ();
        int dx = Math.abs(x2 - x);
        int dy = Math.abs(y2 - y);
        int dz = Math.abs(z2 - z);
        int sx = x2 > x ? 1 : -1;
        int sy = y2 > y ? 1 : -1;
        int sz = z2 > z ? 1 : -1;
        if (dx >= dy && dx >= dz) {
            int ey = 2 * dy - dx;
            int ez = 2 * dz - dx;
            for (int i = 0; i <= dx; i++) {
                result.add(new BlockPos(x, y, z));
                if (ey >= 0) {
                    y += sy;
                    ey -= 2 * dx;
                }
                if (ez >= 0) {
                    z += sz;
                    ez -= 2 * dx;
                }
                ey += 2 * dy;
                ez += 2 * dz;
                x += sx;
            }
        } else if (dy >= dx && dy >= dz) {
            int ex = 2 * dx - dy;
            int ez = 2 * dz - dy;
            for (int i = 0; i <= dy; i++) {
                result.add(new BlockPos(x, y, z));
                if (ex >= 0) {
                    x += sx;
                    ex -= 2 * dy;
                }
                if (ez >= 0) {
                    z += sz;
                    ez -= 2 * dy;
                }
                ex += 2 * dx;
                ez += 2 * dz;
                y += sy;
            }
        } else {
            int ex = 2 * dx - dz;
            int ey = 2 * dy - dz;
            for (int i = 0; i <= dz; i++) {
                result.add(new BlockPos(x, y, z));
                if (ex >= 0) {
                    x += sx;
                    ex -= 2 * dz;
                }
                if (ey >= 0) {
                    y += sy;
                    ey -= 2 * dz;
                }
                ex += 2 * dx;
                ey += 2 * dy;
                z += sz;
            }
        }
        return result;
    }
}
