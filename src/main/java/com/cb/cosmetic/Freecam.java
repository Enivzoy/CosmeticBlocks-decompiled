package com.cb.cosmetic;

import com.cb.config.tabs.GuiTab;
import com.cb.utils.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

public final class Freecam {
    public static final Freecam INSTANCE = new Freecam();

    private static final double SPEED = 0.7;
    private static final float LOOK_SENSITIVITY = 0.15f;
    private static final double REACH = 5.0;

    private boolean active;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean keyWasPressed;

    private Freecam() {
    }

    public boolean isActive() {
        return active;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void toggle() {
        setActive(!active);
    }

    public void setActive(boolean value) {
        Minecraft mc = Minecraft.getInstance();
        if (value && mc.player == null) {
            return;
        }
        if (value == active) {
            return;
        }
        active = value;
        if (value) {
            LocalPlayer player = mc.player;
            Vec3 eye = player.getEyePosition(1.0f);
            x = eye.x;
            y = eye.y;
            z = eye.z;
            yaw = player.getYRot();
            pitch = player.getXRot();
        }
        ChatUtil.INSTANCE.send(active ? "Freecam \u00a7aON" : "Freecam \u00a7cOFF");
    }

    public void tick() {
        Minecraft mc = Minecraft.getInstance();
        long window = mc.getWindow().handle();
        if (mc.gui.screen() == null && window != 0L) {
            int key = ((Number) GuiTab.INSTANCE.getFreecamKeybind().getValue()).intValue();
            if (key != -1) {
                boolean down = GLFW.glfwGetKey(window, key) == 1;
                if (down && !keyWasPressed) {
                    toggle();
                }
                keyWasPressed = down;
            } else {
                keyWasPressed = false;
            }
        } else {
            keyWasPressed = false;
        }
        if (!active) {
            return;
        }
        if (mc.player == null || mc.level == null) {
            setActive(false);
        }
    }

    public Vec3 getLookVector() {
        double yawRad = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(pitch);
        return new Vec3(
            -Math.sin(yawRad) * Math.cos(pitchRad),
            -Math.sin(pitchRad),
            Math.cos(yawRad) * Math.cos(pitchRad)
        );
    }

    public HitResult raycast() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        ClientLevel level = mc.level;
        if (player == null || level == null) {
            return null;
        }
        Vec3 from = new Vec3(x, y, z);
        Vec3 to = from.add(getLookVector().scale(REACH));
        return level.clip(new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    }

    public void onFrame(float realtimeDeltaTicks) {
        if (!active) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.gui.screen() != null) {
            return;
        }
        double yawRad = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(pitch);
        double forwardX = -Math.sin(yawRad) * Math.cos(pitchRad);
        double forwardY = -Math.sin(pitchRad);
        double forwardZ = Math.cos(yawRad) * Math.cos(pitchRad);
        double rightX = Math.cos(yawRad);
        double rightZ = Math.sin(yawRad);
        double moveX = 0.0;
        double moveY = 0.0;
        double moveZ = 0.0;
        if (mc.options.keyUp.isDown()) {
            moveX += forwardX;
            moveY += forwardY;
            moveZ += forwardZ;
        }
        if (mc.options.keyDown.isDown()) {
            moveX -= forwardX;
            moveY -= forwardY;
            moveZ -= forwardZ;
        }
        if (mc.options.keyRight.isDown()) {
            moveX += rightX;
            moveZ += rightZ;
        }
        if (mc.options.keyLeft.isDown()) {
            moveX -= rightX;
            moveZ -= rightZ;
        }
        if (mc.options.keyJump.isDown()) {
            moveY += 1.0;
        }
        if (mc.options.keyShift.isDown()) {
            moveY -= 1.0;
        }
        double length = Math.sqrt(moveX * moveX + moveY * moveY + moveZ * moveZ);
        if (length > 0.0) {
            double speed = SPEED * (double) realtimeDeltaTicks;
            x += (moveX / length) * speed;
            y += (moveY / length) * speed;
            z += (moveZ / length) * speed;
        }
    }

    public void addLook(double dx, double dy) {
        yaw += (float) (dx * (double) LOOK_SENSITIVITY);
        pitch += (float) (dy * (double) LOOK_SENSITIVITY);
        if (pitch > 90.0f) {
            pitch = 90.0f;
        } else if (pitch < -90.0f) {
            pitch = -90.0f;
        }
    }
}
