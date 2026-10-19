package me.enivzoy.cosmeticblocksreborn.util

import java.util.Locale
import org.lwjgl.glfw.GLFW

object KeyNames {

    private val SPECIAL: Map<Int, String> = mapOf(
        340 to "L SHIFT",
        344 to "R SHIFT",
        341 to "L CTRL",
        345 to "R CTRL",
        342 to "L ALT",
        346 to "R ALT",
        32 to "SPACE",
        257 to "ENTER",
        258 to "TAB",
        259 to "BACK",
        261 to "DELETE",
        260 to "INSERT",
        268 to "HOME",
        269 to "END",
        266 to "PG UP",
        267 to "PG DN",
        265 to "UP",
        264 to "DOWN",
        263 to "LEFT",
        262 to "RIGHT",
        280 to "CAPS",
        282 to "NUM LOCK",
        283 to "PRT SC",
        284 to "PAUSE",
        348 to "MENU",
        343 to "L SUPER",
        347 to "R SUPER",
    )

    fun name(key: Int): String {
        if (key == -1) {
            return "None"
        }

        SPECIAL[key]?.let { return it }

        if (key in 290 until 315) {
            return "F" + (key - 290 + 1)
        }

        if (key in 320 until 330) {
            return "NUM " + (key - 320)
        }

        val printable = try {
            GLFW.glfwGetKeyName(key, 0)
        } catch (ignored: Throwable) {
            null
        }

        if (printable != null) {
            return printable.uppercase(Locale.ROOT)
        }

        return "KEY $key"
    }
}
