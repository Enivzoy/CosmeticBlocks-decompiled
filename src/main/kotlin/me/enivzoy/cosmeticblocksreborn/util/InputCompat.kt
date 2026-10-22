package me.enivzoy.cosmeticblocksreborn.util

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.Minecraft

object InputCompat {

    val KEY_KP_ENTER: Int =
        //? if >=26.3 {
        88
        //?} else {
        /*335
        *///?}

    fun isKeyDown(key: Int): Boolean {
        //? if >=26.3 {
        return InputConstants.isKeyDown(key)
        //?} else {
        /*return InputConstants.isKeyDown(Minecraft.getInstance().window, key)
        *///?}
    }
}
