package me.enivzoy.cosmeticblocksreborn.setting

import com.google.gson.JsonObject

class ButtonSetting(
    name: String,
    description: String,
    val actionLabel: String,
    val onClick: () -> Unit,
) : Setting<Unit>(name, description, Unit) {

    override fun writeJson(json: JsonObject) {
    }

    override fun readJson(json: JsonObject) {
    }
}
