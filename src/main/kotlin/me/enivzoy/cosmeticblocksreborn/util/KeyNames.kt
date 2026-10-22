package me.enivzoy.cosmeticblocksreborn.util

import com.mojang.blaze3d.platform.InputConstants
import java.util.Locale

object KeyNames {

    private val SPECIAL: Map<Int, String> = mapOf(
        InputConstants.KEY_LSHIFT to "L SHIFT",
        InputConstants.KEY_RSHIFT to "R SHIFT",
        InputConstants.KEY_LCONTROL to "L CTRL",
        InputConstants.KEY_RCONTROL to "R CTRL",
        InputConstants.KEY_LALT to "L ALT",
        InputConstants.KEY_RALT to "R ALT",
        InputConstants.KEY_SPACE to "SPACE",
        InputConstants.KEY_RETURN to "ENTER",
        InputConstants.KEY_TAB to "TAB",
        InputConstants.KEY_BACKSPACE to "BACK",
        InputConstants.KEY_DELETE to "DELETE",
        InputConstants.KEY_INSERT to "INSERT",
        InputConstants.KEY_HOME to "HOME",
        InputConstants.KEY_END to "END",
        InputConstants.KEY_PAGEUP to "PG UP",
        InputConstants.KEY_PAGEDOWN to "PG DN",
        InputConstants.KEY_UP to "UP",
        InputConstants.KEY_DOWN to "DOWN",
        InputConstants.KEY_LEFT to "LEFT",
        InputConstants.KEY_RIGHT to "RIGHT",
        InputConstants.KEY_CAPSLOCK to "CAPS",
        InputConstants.KEY_NUMLOCK to "NUM LOCK",
        InputConstants.KEY_PRINTSCREEN to "PRT SC",
        InputConstants.KEY_PAUSE to "PAUSE",
        //? if >=26.3 {
        InputConstants.KEY_LGUI to "L SUPER",
        InputConstants.KEY_RGUI to "R SUPER",
        //?} else {
        /*InputConstants.KEY_LSUPER to "L SUPER",
        InputConstants.KEY_RSUPER to "R SUPER",
        *///?}
    )

    fun name(key: Int): String {
        if (key == -1) {
            return "None"
        }

        SPECIAL[key]?.let { return it }

        if (key in InputConstants.KEY_F1..InputConstants.KEY_F24) {
            return "F" + (key - InputConstants.KEY_F1 + 1)
        }

        if (key in InputConstants.KEY_NUMPAD0..InputConstants.KEY_NUMPAD9) {
            return "NUM " + (key - InputConstants.KEY_NUMPAD0)
        }

        val printable = try {
            //? if >=26.3 {
            InputConstants.Type.KEYBOARD.getOrCreate(key).displayName.string
            //?} else {
            /*InputConstants.Type.KEYSYM.getOrCreate(key).displayName.string
            *///?}
        } catch (ignored: Throwable) {
            null
        }

        if (!printable.isNullOrBlank() && printable != "Unknown") {
            return printable.uppercase(Locale.ROOT)
        }

        return "KEY $key"
    }
}
