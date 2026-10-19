# Kotlin Reconstruction Prompt — CosmeticBlocks-Reborn (Minecraft 26.2 / Fabric)

> Hand this prompt + this repository to an agent. The repository is the **reference
> implementation** (decompiled Kotlin, cross-compiled to plain Java). The task is to
> produce a **clean, native Kotlin** rewrite of the mod that is behaviorally equivalent,
> with optional optimizations. Read the reference freely; do not blindly transliterate the
> decompiler noise (`Intrinsics.*`, `$cb_client`, `$inlined$`, `CollectionsKt`, etc.).

---

## 0. Mission

Recreate the Fabric client mod **CosmeticBlocks-Reborn** in **Kotlin**, targeting
**Minecraft 26.2**, **Fabric Loader 0.19.5**, **Java 25**, **Fabric Loom 1.18.4**.
It is a client-side cosmetic block-replacement mod for Hypixel Skyblock Dungeons: it
client-only overrides block appearances (textures, shapes, water/lava) per dimension and
per dungeon floor, with in-world editing tools, a GUI, and a CLI.

Equivalence target: every feature below must work the same way. Optimization is optional
and must not change observable behavior or config file formats (backward compatible).

---

## 1. Build / Environment (exact)

`gradle.properties`:
```
org.gradle.jvmargs=-Xmx4G
org.gradle.parallel=true
minecraft_version=26.2
loader_version=0.19.5
loom_version=1.18.4
fabric_api_version=0.161.0+26.2
fabric_kotlin_version=1.14.2+kotlin.2.4.21
devauth_version=1.2.2
mod_version=1.0.0-reconstructed+mc26.2
maven_group=com.cb
archives_base_name=cb
```

- `build.gradle`: plugin `net.fabricmc.fabric-loom` version `${loom_version}`;
  deps `minecraft`, `fabric-loader`, `fabric-api`, `fabric-language-kotlin`;
  `runtimeOnly me.djtheredstoner:DevAuth-fabric:${devauth_version}`;
  `loom { accessWidenerPath = file('src/main/resources/cb.accesswidener'); runs { client { property 'devauth.enabled','true' } } }`;
  `java.toolchain.languageVersion = JavaLanguageVersion.of(25)`, `withSourcesJar()`;
  `JavaCompile.options.release = 25`, `encoding UTF-8`, `-Xmaxerrs 100000`;
  `processResources.filesMatching('fabric.mod.json'){ expand 'version': project.version }`.
- Use the **Kotlin** Fabric template (`kotlin("jvm")` + `fabric-language-kotlin`) instead of
  the pure-Java compile step; keep sources under `src/main/kotlin` and resources under
  `src/main/resources`. Keep the `cb.accesswidener` + mixin JSONs + `fabric.mod.json`.
- Repositories: `https://maven.fabricmc.net/`, `mavenCentral()`,
  `https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1`.
- Build/run on Windows: `.\gradlew.bat build --console=plain`,
  `.\gradlew.bat runClient --console=plain`. Gradle "UP-TO-DATE" can be wrong; force with
  `--rerun-tasks` when verifying. PowerShell prints `NativeCommandError`/restricted-method
  `WARNING:` on normal output — not failures.

`fabric.mod.json` essentials:
- `id`: `cb`; `name`: `CosmeticBlocks-Reborn`; `version`: `${version}`; author `Enivzoy`; license `CC0-1.0`; icon `assets/cb/icon.png`.
- `accessWidener`: `cb.accesswidener`.
- entrypoints: `main` → `com.cb.Cb` (singleton `INSTANCE`); `client` → `com.cb.CbClient` (singleton `INSTANCE`).
- mixins: `cb.mixins.json` (common, package `com.cb.mixin`, effectively empty), `cb.client.mixins.json` (client, package `com.cb.mixin.client`, `JAVA_25`), `renderkit.mixins.json` (client, package `renderkit.mixin`).
- depends: `fabricloader >=0.19.5`, `minecraft ~26.2`, `java >=25`, `fabric-api`, `fabric-language-kotlin`.

---

## 2. Package / Class Inventory (parity checklist)

Recreate these responsibilities (names may be idiomatic Kotlin; keep public API used by mixins):

**Root**
- `Cb` — common (server-safe) initializer, `ModInitializer`, singleton `INSTANCE`.
- `CbClient` — `ClientModInitializer`; wires keybinds, HUD, command, render/tick hooks, loads stores.

**config/**
- `Config` — root settings persistence (`config/cb/cb-ui.json`), tab registry, legacy migration.
- `SettingsTab` — base tab/setting container (static registration model).
- `Tabs` — ordered tab list: `CosmeticBlocksTab, GlobalCosmeticsTab, DungeonConfigTab, RoomProfilesTab, PreferencesTab, GuiTab, HelpTab`.
- `tabs/CosmeticBlocksTab` — replacement master settings/keybinds.
- `tabs/GlobalCosmeticsTab` — global water/lava + global remaps.
- `tabs/DungeonConfigTab` — active/floor config selection + per-config water/lava + per-config remaps.
- `tabs/PreferencesTab` — auto-load floor config behavior.
- `tabs/RoomProfilesTab`, `tabs/GuiTab`, `tabs/HelpTab`.

**setting/**
- `Setting<T>` (+ `SettingKt` with generic `visibleWhen(setting, predicate)`).
- `BooleanSetting`, `NumberSetting`, `ColorSetting`, `ModeSetting`, `KeybindSetting`,
  `StringSetting`, `ButtonSetting`, `LabelSetting`, `ConfigListSetting`.

**gui/**
- `ClickGuiScreen` (NVG-based click GUI; panels placed cumulatively), `NvgScreen`,
  `SettingsPanel`, `ProfileManager`.
- `component/`: `SettingComponent`, `BooleanComponent`, `ButtonComponent`, `ColorComponent`,
  `ConfigListComponent`, `KeybindComponent`, `ModeComponent`, `SliderComponent`,
  `StringComponent`, `TextComponent`.
- `hud/`: `HudElement`, `HudManager`, `HudRenderer`.
- `screens/`: `CliScreen`, `CliEngine` (huge command dispatcher), `BlockSelectorScreen`,
  `RadialBlockScreen`, `GlobalRemapEditorScreen`, `HudEditorScreen`, `KeybindEditorScreen`, `ProfileScreen`.

**cosmetic/ (core)**
- `CosmeticBlocks` — orchestrator/facade (effective accessors, routing).
- `ReplacementStore` — per-dimension replacement map + config switching; `configDir = config/cb/cb`.
- `ReplacementCache` — in-memory caches: `activeCache` (pos→state), `phantomPositions`,
  `retextureCache`, `originalShapeCache`, `shapedPositions`.
- `ReplacementIO` — load/save dimension replacements + water fields + per-config remap.
- `ConfigSwitcher` — create/load/list/delete configs (excludes `-BlockRemap.json`).
- `ConfigRemap` / `GlobalRemap` / `RemapStore` — per-config and global block remaps.
- `WaterConfig` — global + per-config water/lava color state (`WaterConfig.Fields`).
- `FloorUtil` — floor canonicalization, display options, colors, `floorNumber`.
- `BlockStateCodec` — parse/serialize block-state strings, placement state, original-shape.
- `RoomStore` — room-relative block storage + resolve.
- `RoomProfileManager` — `.roomprofile` files.
- `SaveScheduler` — debounced async save (2s delay / 10s max), `CFME-Save` daemon thread.
- `DirtySections` — section remesh queue (≤4/tick).
- `EditMode`, `WorldEditMode` (fill/replace/remove/floodfill/undo), `Ruler`, `Freecam`,
  `Favorites` (`config/cb/cb_favorites.txt`), `CosmeticRenderer`, `CFMEModelWrapper`.

**dungeon/**
- `RoomScanner` (room grid + room DB), `RoomInfo`, `RoomShape`, `RoomType`, `Rotation`,
  `ScannedRoom`, `RoomDataKt` (rotation math), `Vec2i`.

**events/** — lightweight bus: `Event`, `CancellableEvent`, `EventBus`, `EventBusKt`,
`EventDispatcher`, `EventPriority`, `TickEvent`, `WorldEvent`, plus one inlined comparator helper.

**features/** — `Feature`, `FeatureManager`.

**render/** — `NVGRenderer` (NanoVG bridge), `OverlayRenderer`, `Theme`, `Anim`,
`PulsiveRenderBootstrap`.

**utils/** — `ChatUtil`, `ConfigBackup`, `RenderLayers`, `Scheduler`, `ServerUtils`,
`SkyblockerRenderPipelines`; `util/KeyNames`.

**mixin/ (client)** — 21 client mixins (see §6) + `renderkit/mixin/*` accessors.

**renderkit/** — retained third-party NanoVG/bloom/blur/font toolkit (`NVGRenderer`,
`PulsivePipelines`, blur/bloom chains, rounded-rect/text render states, `renderkit.font.*`,
`particle/*`, `mixin/*`). Reuse as-is or replace; behavior parity of visuals required.

---

## 3. Core Data Model & Persistence (must match on disk)

**Config dir:** `config/cb/`. Per-config files: `config/cb/cb/<name>.json`.
The file name **is** the config name (no in-game rename). Auto-save on edit to the active
config; `/cb save` forces.

Per-config JSON:
```json
{
  "floor": "f1|f2|...|f7|m1|...|m7|e|none",
  "allowWaterColor": true/false,
  "rgbWaterColor": <int ARGB/0xRRGGBB>,
  "lavaAsWater": true/false,
  "<dimension>": { "x,y,z": "blockstate-string" }
}
```
- Water/lava values are written **only** when an override is active (omit otherwise).
- Per-config remaps: `config/cb/cb/<name>-BlockRemap.json` (written only when non-empty).
- `ConfigSwitcher.listConfigs()` must exclude `*-BlockRemap.json`.
- `ConfigSwitcher.deleteConfig()` must also delete the matching `-BlockRemap.json`.

Other files:
- `config/cb/cb-ui.json` — UI/settings store (tabs/settings keyed by label).
- `config/cb/cb_rooms.json` — room store.
- `config/cb/cb_favorites.txt` — one block id per line.
- Room profiles: `<name>.roomprofile` under `config/cb/cb` (`PROFILE_EXT=".roomprofile"`, default `default`).

**Legacy migration** in `Config`: explicit `Map<String, String[][]> legacyMigration`
(do NOT use generic tab/key scans — labels collide across tabs):
- `Settings` ← `["Auto-load Floor Config"]`
- `Global Cosmetics` ← `["Custom Water Color"→"Allow Water Color", "Water Color"→"RGB Water Color", "Lava as Water", "Block Remaps.Enabled", "Block Remaps.Keep Block Shape"]`
- `Dungeon Config` ← `["Active Config", "Config Floor", "Custom Water Color", "Water Color", "Lava as Water"]`

**Block state string format:** `namespace:path` optionally `[prop=val,...]`, optionally
suffixed `|shape=<shapeState>` for retexture (shape + separate texture). Phantom (placed in
air) is prefixed `!`. Example cached raw: `!minecraft:stone|shape=minecraft:oak_fence[...]`.

**Floor canonical + colors (exact):**
- Stored tokens: `none | e | f1..f7 | m1..m7`.
- Display options: `None, Entrance, f1/m1 .. f7/m7`; f/m share by `floorNumber`.
- Colors: `NONE=#181A2A`, `ENTRANCE=#2B1D3A`, floors `#3E1C4B,#581B5E,#74196E,#8E1674,#AA0D74,#C8006E,#E60060`.
- No poetic floor names; hex only. Black text when item bg is bright
  (`r*299+g*587+b*114 > 70000` → black on non-transparent bg).

---

## 4. Feature Spec (point-by-point)

### 4.1 Block replacement engine
- Client-only visual replacement keyed by world `BlockPos` per dimension.
- `ReplacementCache.getReplacementAt(x,y,z,originalAir)` returns the cosmetic state, honoring
  master `Enabled` and `Only in Dungeons`; air positions only when phantom.
- Supports: full-block texture swap; keep-original-shape retexture (`|shape=` + `ShapedPositions`);
  phantom placement in air (`!` prefix); original-shape cache.
- `hasAdjacentReplacement`, `rebuildActiveCache` (rebuild from `ReplacementStore` current dim),
  `clearCacheForPos`, `applyCacheForEntry`.
- Editing triggers section remesh through `DirtySections` (batched ≤4 sections/tick).

### 4.2 Water / Lava
- Global (`GlobalCosmeticsTab`): `Allow Water Color`, `RGB Water Color`, `Lava as Water`.
- Per-config (`DungeonConfigTab`, written to `<name>.json`): same three, override global.
- Applied via `BiomeColorsMixin` + block-render mixins; per-config wins over global.

### 4.3 Block remaps (global + per-config)
- `GlobalRemap` and `ConfigRemap` both implement `RemapStore`.
- `GlobalRemap` enable check reads `GlobalCosmeticsTab.INSTANCE.getGlobalRemapEnabled()`;
  `Resolved` is package-visible.
- Dungeon gate: while `inDungeon`, global remaps are suppressed **only** when the current
  floor has a per-config remap; otherwise global stays active; restored when dungeon ends.
- Global tab has Import/Export buttons; Dungeon Config tab does **not**.
- `GlobalRemapEditorScreen(RemapStore, showImportExport)` — one screen, parameterized.
- `remapKeepShape` feeds `BlockStateCodec.applyOriginalShape`:
  `if (!KeepBlockSize && !remapKeepShape) return replacement;` else keep original shape.

### 4.4 Edit mode (`EditMode`)
- Toggle via `/cb em`. Turning ON forces `Enabled`.
- Brush = selected block-state (`selectedBlock`, default `minecraft:stone`).
- Mouse: R-click place, L-click remove, M-click pick (world or phantom).
- `Place Mode`: place adjacent to target; supports phantom (in-air) placement with
  synthetic `BlockHitResult`; refuses occupied/CFME positions.
- Fill mode: R-click flood-fills connected same-block regions (respect `Fill Mode Limit`);
  room-relative when `inDungeon` and scanned room rotated.
- `computePlacementState` derives orientation from hit face; explicit `[...]` props bypass it.
- Sub-commands: set brush / set property / list properties (`hasExplicitProperties`).

### 4.5 WorldEdit mode (`WorldEditMode`)
- Pos1 (L-click) / Pos2 (R-click) selection; `weFillOnClick` fills immediately on Pos2.
- `fillRegion(spec)` weighted random (weights `block:weight`), `replaceRegion(from,toSpecs)`,
  `removeInRegion(filter?)`, `floodFill(start,spec)` (flat or room-relative), `undoLastOperation`.
- Undo snapshot per op (dimension map or room snapshot). Emits `§a/§c` status strings.
- Selection visualized via gizmos (`CosmeticRenderer.emitGizmos`, `showReplacedBlocks`, overlay range/color).

### 4.6 Ruler (`Ruler`)
- Keybind toggles; scroll adjusts reach (1..100, default 4).
- R sets start, R again places Bresenham3D line via `CosmeticBlocks.setReplacement`; L cancels.
- Gizmos: reach outline (green) / preview fill (white), always-on-top.

### 4.7 Freecam (`Freecam`)
- Keybind toggles; eye-anchored free camera, `SPEED=0.7`, look sensitivity `0.15`, reach `5.0`.
- WASD/jump/shift move in `onFrame(deltaTicks)`; `raycast()` uses `ClipContext(...OUTLINE, NONE, player)`.
- Entering any GUI screen suspends movement; exiting world disables.
- Camera applied via `CameraMixin`.

### 4.8 Room profiling
- `RoomScanner` scans the dungeon room grid (grid `START=-185`, `ROOM_SIZE_SHIFT=5`,
  room DB from JSON) and tracks current room + `Rotation`.
- `RoomStore` stores room-relative replacements; `resolveRoomBlocks`;
  `rawAtWorld`/`setRoomReplacementRaw`; `forceRoomSave`.
- `RoomProfilesTab` + `RoomProfileManager` + `ProfileScreen`: save/load/export/import `.roomprofile`.
- `RoomDataKt.rotateToNorth(offset, rotation)` for room-relative coordinates.

### 4.9 Floor auto-load / revert
- `LocationTracker` detects Skyblock/dungeon state via player-info/scoreboard/team packets:
  `FORMAT_CODES="§."`, `CATACOMBS_FLOOR="The Catacombs\b(?:\s*\((\w+)\))?"`; tracks
  `isInSkyblock`, `currentArea`, `inDungeon`, `currentFloor`.
- `PreferencesTab.Auto-load Floor Config` (default **ON**): on entering a dungeon floor,
  auto-switch to the config whose `floor` matches.
  - Children (visible only when ON): `Revert After Completion` (ON), `Show Title After Load` (ON),
    `Load Chat Message` (OFF).
- After dungeon completes, revert to the previously active config.

### 4.10 Config management
- `Active Config` (dropdown, from `ConfigSwitcher.listConfigs()`; sorted desc by
  `floorNumber(floorOfConfig(name))`), `Config Floor` (9-option dropdown + colors).
- `/cb create <name>` (empty file), `/cb load <name>` (save current then load target),
  `/cb delete <name>` (also deletes remap), `/cb save`, `/cb list`, `/cb configs`,
  `/cb reload`, `/cb backup`.

### 4.11 GUI
- `ClickGuiScreen` NVG panels; `SettingsPanel.getWidth()` returns `280.0F` for the
  "Cosmetic Blocks" tab, `240.0F` otherwise; panels placed with cumulative
  `nextX += panel.getWidth() + 14.0F`.
- Components render each `Setting<T>`; `SettingKt.visibleWhen(setting, predicate)` hides rows.
- `ConfigListComponent` draws floor rows with the palette + black-on-bright text rule.
- `HudRenderer`/`HudManager`/`HudEditorScreen`: toggleable HUD elements, draggable.
- `KeybindEditorScreen`, `BlockSelectorScreen`, `RadialBlockScreen`, `CliScreen`.
- Note: base `SettingsTab` static-init subcategory grouping is **dead code** (runs after use);
  implement grouping properly (per-tab categories) if you want subheaders.

### 4.12 CLI (`CliEngine` / `CliScreen` / `/cb`)
Command surface (aliases in parentheses) — full parity:
- GUI/meta: `gui`, `help`, `fps`, `ping`, `calc`/`=`, `coin`/`coinflip`, `dice`/`roll`, `bind`/`binds`,
  `scale`, `timer`, `note`/`notes`/`noteclear`/`noteexport`, `reminder`/`reminders`/`remind`/`rmreminder`/`cancelreminder`/`clearreminders`, `toggle`/`t`.
- CB core: `cb` subcommands — `fill`/`f`, `replace`/`rep`/`r`, `remove`/`rm`, `undo`/`u`,
  `block`/`b`, `shape`, `state`/`st`, `props`/`properties`, `clear`/`clearall`,
  `pos1`/`p1`, `pos2`/`p2`, `sel`/`selection`, `clearselection`, `fav`/`favorites` (`add`,`remove`/`rm`,`list`),
  `save`, `load`, `list`/`ls`, `create`, `delete`/`del`, `reload`, `backup`, `configs`, `gui`, `edit`/`em`.
- Room: `room` (`scan`,`list`/`ls`,`info`,`clear`,`clearall`).
- Profiles: `profiles`, `delprofile`, `export`, `import`.

In-game `/cb` (Brigadier `ClientCommandRegistrationCallback`): `gui`, `profiles`, `keybinds`,
`hud`, `em`, `fillmode`, `freecam`, `block <greedy>`, `remove <block>`, `clear`, `list`,
`save`, `reload`, `create <name>`, `load <name>`, `delete <name>`, `configs`,
`pos1 <coords>`, `pos2 <coords>`, `sel`, `clearselection`, `fill <blocks>`,
`replace <args>`, `undo`, `fav add|remove|list`, `backup`, `props`, `state <prop> <val>`.

---

## 5. Rendering / Mixins

**Client mixins** (`com.cb.mixin.client`, `JAVA_25`) — implement equivalents:
`BiomeColorsMixin` (water/lava tint), `BlockFaceCullingMixin`, `BlockModelShaperMixin`
(swap models to cosmetics), `CameraMixin` (freecam), `ClientInputMixin`, `ClientLevelMixin`,
`ClientPacketListenerMixin` (location/floor detection), `EntityStepSoundMixin`,
`LevelEventHandlerMixin`, `MultiPlayerGameModeMixin` (pick/place hooks), `LevelRendererCompatMixin`,
`LevelRendererMixin`, `GuiCompatMixin`, `KeyboardInputMixin`, `MinecraftCompatMixin`,
`MinecraftPickBlockMixin`, `MouseHandlerMixin`, `RenderSectionRegionMixin`, `ScreenRenderMixin`,
`SodiumBlockOcclusionMixin`, `SodiumLevelSliceMixin` (Sodium compatibility).

**renderkit mixins** (`renderkit.mixin`, client): `GuiGraphicsExtractorAccessor`,
`PostChainAccessor`, `ShaderManagerAccessor`.

Access widener `cb.accesswidener` opens the required Minecraft internals.

---

## 6. MC 26.2 API Notes / Gotchas (verified against reference)

- `KeyboardInput extends ClientInput`; `ClientInput` declares `public Input keyPresses;` and
  `protected Vec2 moveVector;`. Use `Input.EMPTY`, `Vec2.ZERO`.
- `Minecraft.pick(float)` is private; `hitResult` and `crosshairPickEntity` are accessible.
- `ClipContext(Vec3 from, Vec3 to, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, Entity)`.
- `Camera.alignWithEntity(float)`.
- Mixin `@Shadow` does **not** resolve inherited fields — shadow the declaring class if needed.
- Gizmos API: `net.minecraft.gizmos.Gizmos.cuboid(...)`, `GizmoStyle.stroke/fill/strokeAndFill(...)`,
  `.setAlwaysOnTop()`; wrap in try/catch `IllegalStateException`.
- `LevelRenderer` section dirtying: `level.setSectionDirtyWithNeighbors(x,y,z)`; section key `BlockPos.asLong(x>>4,y>>4,z>>4)`.
- `BuiltInRegistries.BLOCK.getOptional(Identifier)` / `.getKey(block).getPath()`.
- `BlockStateDefinition.getProperty(name)`, `.getProperties()`, `Property.getValue(str)` → `Optional`.
- NanoVG renderer ARGB is `0xAARRGGBB`.
- Keep `access.widener` aligned with any newly accessed members.

---

## 7. Behavior Invariants (do not regress)

1. Config **file format and names** unchanged (drop-in compatible with existing `config/cb`).
2. Per-config settings write to `<name>.json`; globals to `cb-ui.json`; per-config remaps to
   `<name>-BlockRemap.json`; per-config overrides global.
3. `Config.load()` must **not** fire `onPerConfigChanged` while programmatically restoring
   tab settings (suppress with a begin/end sync guard) — otherwise a spurious save/override
   is triggered on startup.
4. Global-remap dungeon gating exactly as §4.3.
5. Floor token canonicalization and color/contrast rules exactly as §3.
6. Client-only: running on a dedicated server must be a no-op (`Cb` common safe; no client
   classes referenced from `main` entrypoint).
7. Saves debounced (2s delay, 10s max) on a daemon thread; flush on world change/exit.

---

## 8. Optional Optimizations (allowed, behavior-preserving)

- Replace the static-singleton/`Intrinsics` boilerplate with idiomatic Kotlin
  (top-level `object`, `by lazy`, data classes); drop all decompiler suffixes.
- `ReplacementCache`: boxed `Long` keys → primitive-keyed structures (e.g., `Long2ObjectOpenHashMap`
  from fastutil, already available via Minecraft) to cut allocation/GC; avoid repeated
  `BlockPos.asLong` in hot loops.
- `DirtySections`: coalesce enqueues; batch remesh with a budget-based scheduler.
- `ReplacementStore`: use immutable snapshots + copy-on-write for the dimension map; async IO via coroutines.
- `Config`/`ReplacementIO`: stream JSON with a fast serializer; avoid re-parsing on no-op loads.
- `CliEngine`: replace the giant `switch(hashCode())` with a `Map<String, Command>` registry.
- `RoomScanner`: cache room lookups; spatial hash for scanned rooms.
- Replace the vendored `renderkit` with the upstream dependency if an equivalent is available,
  else keep it (visual parity required).
- Coroutines instead of the manual `ExecutorService` + tick bookkeeping, preserving timing.

---

## 9. Acceptance / Verification

- `.\gradlew.bat build --console=plain` succeeds (Java 25 toolchain).
- `runClient` (DevAuth) loads with no CB exceptions; log shows
  `Loaded config from ...cb-ui.json`.
- Existing `config/cb/cb/*.json` load unchanged; legacy `cb-ui.json` migrates once as in §3.
- Manual: create/load/delete config; set floor; enter a dungeon and confirm auto-load + revert;
  edit/replace/undo; flood fill (flat + rotated room); ruler; freecam; global + per-config
  water and remaps (incl. dungeon gate); HUD toggle; CLI + `/cb` parity.
- CI: keep the raw-jar artifact workflow (`.github/workflows/build.yml`,
  `actions/upload-artifact`, `archive: false`).

---

## 10. Reference Map (where to look in the repo)

- Orchestration: `com/cb/CbClient.java`, `com/cb/cosmetic/CosmeticBlocks.java`.
- Persistence: `ReplacementStore`, `ReplacementIO`, `ConfigSwitcher`, `Config`, `SaveScheduler`.
- Cache/remesh: `ReplacementCache`, `DirtySections`.
- Editing: `EditMode`, `WorldEditMode`, `Ruler`, `Freecam`, `CosmeticRenderer`, `Favorites`.
- Remaps/water: `GlobalRemap`, `ConfigRemap`, `RemapStore`, `WaterConfig`, `BlockStateCodec`.
- Dungeon: `dungeon/RoomScanner`, `RoomDataKt`, `ScannedRoom`, `Rotation`, `RoomStore`, `RoomProfileManager`.
- Detection: `LocationTracker` + `ClientPacketListenerMixin`.
- GUI/CLI: `gui/ClickGuiScreen`, `gui/SettingsPanel`, `gui/component/*`, `gui/screens/*`, `command/CbCommand`.
- Build: `build.gradle`, `gradle.properties`, `src/main/resources/{fabric.mod.json,cb*.mixins.json,renderkit.mixins.json,cb.accesswidener}`.
