package me.enivzoy.cosmeticblocksreborn.setting

import com.google.gson.JsonObject

class LabelSetting(
    name: String,
    val text: String,
    val spaceBefore: Boolean,
) : Setting<String>(name, "", text) {

    override fun writeJson(json: JsonObject) {
    }

    override fun readJson(json: JsonObject) {
    }
}
