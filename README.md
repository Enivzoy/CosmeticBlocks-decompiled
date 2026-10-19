# CosmeticBlocks — Reborn

A fork of [CosmeticBlocks-decompiled](https://github.com/Enivzoy/CosmeticBlocks-decompiled), updated and reworked
from the old `native-java` branch.

This build adds a bunch of new features on top of the original system — per-floor config switching,
per-config water colours and block remaps, a profile system, a HUD editor, a radial block picker,
a command palette (CLI), and more.

> The `native-kotlin` branch is a full rewrite: everything is now **Kotlin**
> (except the Mixin classes, which stay in Java on purpose).
> The GUI is rendered with [Cascade](https://github.com/starred-star/cascade).

## Usage

Grab the latest action build, drop it in your `mods` folder, then run **`/cb`** in game to open the GUI.

| Where | What |
| --- | --- |
| `/cb` | Opens the main configuration screen |
| `/cb help` | Lists all commands |
| `.minecraft/config/cb/` | All config files live here |
| `.minecraft/config/cb/cb/` | Individual config profiles |

## Building From Source

1. Open the project folder in IntelliJ IDEA and import it as a Gradle project.
2. Set the Gradle JVM to **JDK 25**.
3. Let Gradle sync — the wrapper downloads Gradle 9.7.0 and resolves Minecraft/Fabric dependencies on first sync.
4. Run the `runClient` Gradle task for a development client, or `build` to produce the mod JAR.

Configured versions: Minecraft **26.2**, Fabric Loader **0.19.5**, Fabric API **0.161.0+26.2**,
Fabric Language Kotlin **1.14.2+kotlin.2.4.21**, Fabric Loom **1.18.4**, Kotlin **2.4.21**.

## Dependencies

- [Fabric API](https://modrinth.com/mod/fabric-api)
- [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin)
- [Cascade](https://modrinth.com/mod/cascade) — used for all GUI rendering (immediate-mode rects, strokes,
  shadows and text) instead of the old vendored `renderkit` pipeline.

A big thank you to **Starred** for Cascade — it replaced the whole custom shader stack and made the UI
code dramatically simpler. The library is bundled into the mod JAR, so you don't need to install it separately.

## TODO / Features

- [x] Split settings into Global Cosmetics / Dungeon Config / Settings tabs
- [x] Per-config water colour + per-config block remaps
- [x] Freecam *(cheat)*
- [x] Avoid getting banned for blinking — *whoops!*
- [x] Per-dungeon load/save system
- [x] Incorporated into the GUI
- [x] Revert to original config after a dungeon is done
- [ ] Per island (outside dungeons)
- [ ] Easier config sharing
- [ ] Minecraft 26.3 support

## Contributing

Feel free to add or change features — pull requests will most likely be accepted.

## References

- [OdinClient — CheaterMap](https://github.com/skies-starred/OdinClient/blob/master/src/main/kotlin/foo/starred/odinclient/features/impl/dungeons/CheaterMap.kt)
- [OdinClient — FuckDiorite](https://github.com/skies-starred/OdinClient/blob/master/src/main/kotlin/foo/starred/odinclient/features/impl/floor7/FuckDiorite.kt)
- [Devonian — DungeonWaypoints](https://github.com/Synnerz/devonian/blob/26.3/src/main/kotlin/com/github/synnerz/devonian/features/dungeons/clear/DungeonWaypoints.kt)
- [Odin — DungeonWaypointPacks](https://github.com/odtheking/Odin/blob/main/src/main/kotlin/com/odtheking/odin/features/impl/dungeon/dungeonwaypoints/DungeonWaypointPacks.kt)
- [Odin — DungeonWaypointEditor](https://github.com/odtheking/Odin/blob/main/src/main/kotlin/com/odtheking/odin/features/impl/dungeon/dungeonwaypoints/DungeonWaypointEditor.kt)
- [Odin — SecretWaypoints](https://github.com/odtheking/Odin/blob/main/src/main/kotlin/com/odtheking/odin/features/impl/dungeon/dungeonwaypoints/SecretWaypoints.kt)
