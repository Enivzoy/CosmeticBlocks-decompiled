# CosmeticBlocks — Reborn

**CosmeticBlocks — Reborn** is an updated, reworked fork of
[CosmeticBlocks-decompiled](https://github.com/Enivzoy/CosmeticBlocks-decompiled). The original was a
decompiled dump — rough, inconsistent and hard to build on. Reborn cleans that up, keeps the core block-swap
system, and layers a full set of new features on top of it.

## Branches

| Branch | What it is |
| --- | --- |
| `native-java` | The original decompiled-style Java code, lightly reworked. |
| `native-kotlin` | **Current.** A full rewrite of the mod in Kotlin. |

### About `native-kotlin`

`native-kotlin` is a from-the-ground-up rewrite. Everything is now **Kotlin**, except the Mixin classes,
which deliberately stay in **Java** — Mixins are far easier to author and safer against obfuscation that way.
The old custom shader/rendering stack was replaced with
[Cascade](https://github.com/starred-star/cascade), an immediate-mode GUI library: it removed the vendored
`renderkit` pipeline entirely and made the UI code dramatically simpler.

## Features

On top of the original cosmetic block system, Reborn adds:

- **Per-floor config switching** — configs are keyed to the dungeon floor you are on.
- **Per-config water colours** and **per-config block remaps**.
- **Profile system** for saving and reusing configurations.
- **HUD editor** and a **radial block picker**.
- **Command palette (CLI)** for running commands without leaving the GUI.
- **Freecam** *(cheat)*.
- **Per-dungeon load/save** with an automatic revert to your original config when a dungeon is finished.
- **Compact config storage** — see [Config storage](#config-storage).

## Usage

Grab the latest action build, drop it in your `mods` folder, then run **`/cb`** in game to open the GUI.

| Where | What |
| --- | --- |
| `/cb` | Opens the main configuration screen |
| `/cb help` | Lists all commands |
| `/cb migrate <name>` | Rewrites an existing config into the compact format (keeps a `.bak` backup) |
| `.minecraft/config/cb/` | All config files live here |
| `.minecraft/config/cb/cb/` | Individual config profiles |

## Config storage

Block remaps used to be written one line per coordinate, which made large configs enormous — a single
dungeon floor could exceed 50 MB. Reborn stores the exact same data as **axis-aligned cuboid ranges**
grouped by block value, i.e. the same shape a Minecraft `/fill` command uses:

```json
{
  "floor": "f7",
  "format": 2,
  "minecraft:overworld": {
    "minecraft:black_concrete_powder": [
      [112,15,0,133,15,10],
      [83,16,10,98,19,10]
    ]
  }
}
```

Each range is `[x1,y1,z1,x2,y2,z2]`.

- Existing flat-format configs are still read normally — they are converted automatically the next time the
  config is saved.
- To convert a file immediately (for example, to shrink it on disk), run **`/cb migrate <name>`**. A
  `<name>.json.bak` backup is written first.
- Converting a full floor typically shrinks the file by **more than 90%**.

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
- [x] Compact, range-based config storage (`/cb migrate`)
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
