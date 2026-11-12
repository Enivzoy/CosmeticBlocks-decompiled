package me.enivzoy.cosmeticblocksreborn.util

import com.mojang.blaze3d.platform.InputConstants

object InputCompat {

    val KEY_KP_ENTER: Int = 88

    fun isKeyDown(key: Int): Boolean {
        return InputConstants.isKeyDown(key)
    }
}
