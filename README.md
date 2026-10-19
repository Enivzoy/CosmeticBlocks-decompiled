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
- **Compact config storage** with optional extra compression stages — see [Config storage](#config-storage).

## Usage

Grab the latest action build, drop it in your `mods` folder, then run **`/cb`** in game to open the GUI.
The command also answers to **`/cosmeticblocks`**, **`/cosmeticb`**, **`/cosm`** and **`/cosblocks`**.

| Where | What |
| --- | --- |
| `/cb` | Opens the main configuration screen |
| `/cb help` | Lists all commands |
| `/cb migrate <name>` | Rewrites an existing config into the compact format (keeps a `.bak` backup) |
| `/cb migrate <name> -mini` | Same, but with the extra compression stages enabled (aliases `-m`, `-minified`) |
| `/cb migrate <name> --settings` | Uses whatever stages are toggled in the Settings tab |
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

### Optional compression stages

The range format above is already small, but it stays **human-readable by default** — a config saved
normally, and a plain `/cb migrate`, never change that. If you want to shrink a file even further, there
are optional extra stages. Open the **Settings** tab and turn on **Minimize Config**; a list of stages
appears underneath:

| Stage | What it does |
| --- | --- |
| **Gzip** | Compresses the whole file with gzip. |
| **No Readability** | Drops all pretty-printing whitespace. |
| **Block Dictionary** | Stores each unique block value once and references it by index. |
| **Blockstate Palette** | Splits blocks into base block + blockstate, stored as two index tables. Only available once **Block Dictionary** is on. |
| **RLE Encoding** | Run-length-encodes the coordinate ranges. |

Nothing changes on disk until you run **`/cb migrate <configname> --settings`**, which re-encodes a config
using the stages you enabled. The one-off **`-mini`** flag (`-m` / `-minified`) is a shortcut that enables a
sensible set of stages for that single migration without touching the Settings tab.

A migrated file records which stages it used, so it is always read back correctly regardless of your current
settings, and old plain/legacy files keep working. On a real floor-7 config this took a 50 MB file down to
roughly **0.6 MB (~99% smaller)** — though note that enabling *every* stage is not automatically the
smallest: gzip alone already captures most of the redundancy, so `Gzip` + `No Readability` + `Block
Dictionary` tends to win.

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
- [x] Optional extra compression stages (gzip / minify / dictionary / palette / RLE)
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
