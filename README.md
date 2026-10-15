# CB — reconstructed Minecraft 26.2 project

This is a source reconstruction of `cb-1.0.0.jar`, not the original repository.    
[@native-java]   
The JAR contained compiled Kotlin classes; CFR recovered Java-like reference source, but that output is incomplete and cannot be compiled as Java. See [`docs/cfr-summary.txt`](docs/cfr-summary.txt). The build uses the original classes in [`libs/cb-1.0.0.jar`](libs/cb-1.0.0.jar) and packages the reconstructed resources.

This branch scraps the need to bundle the original jar and to reference it, achieving it by compiling natively

The project targets Minecraft 26.2 and Fabric. The source was updated for the 26.2 screen API (`Minecraft.gui.setScreen` / `gui.getScreen`).  
The NanoVG renderer has been updated to support Vulkan!  

## Building From Source

1. Open this project folder in IntelliJ IDEA and import it as a Gradle project.
2. Set the Gradle JVM to JDK 25.
3. Let Gradle sync. The included wrapper downloads Gradle 9.7.0 and resolves the Minecraft/Fabric dependencies on first sync.
4. Run the `runClient` Gradle task to start a development client, or run `build` to create the mod JAR.

Configured versions: Minecraft 26.2, Fabric Loader 0.19.5, Fabric API 0.161.0+26.2, Fabric Language Kotlin 1.14.2+kotlin.2.4.21, and Fabric Loom 1.18.4.

The original JAR declared CC0-1.0 in `fabric.mod.json`; THIS IS NOT THE ORIGINAL SOURCE!

## Contribution

feel free to add/change features (i will/most likely accept any pull requests)

## TODO
- add 26.3 support [WORKING ON IT]
- port to 26.2 [DONE]
- \*add per dungeon load/save system [DONE]
- \*incorporate it into the gui [DONE]
- Per Island(Outside Dungeons) [SOON]
- Freecam [DONE]
- \*Avoid getting banned for blinking [DONE] whoops!
- Make it easier to share configs [SOON]
- revert back to original config after a dungeon is done [DONE]
- split settings into Global Cosmetics / Dungeon Config / Settings tabs [DONE]
- \*per-config water color + per-config block remaps [DONE]
## references
https://github.com/skies-starred/OdinClient/blob/master/src/main/kotlin/foo/starred/odinclient/features/impl/dungeons/CheaterMap.kt        
https://github.com/skies-starred/OdinClient/blob/master/src/main/kotlin/foo/starred/odinclient/features/impl/floor7/FuckDiorite.kt    
https://github.com/Synnerz/devonian/blob/26.3/src/main/kotlin/com/github/synnerz/devonian/features/dungeons/clear/DungeonWaypoints.kt           
https://github.com/odtheking/Odin/blob/main/src/main/kotlin/com/odtheking/odin/features/impl/dungeon/dungeonwaypoints/DungeonWaypointPacks.kt          
https://github.com/odtheking/Odin/blob/main/src/main/kotlin/com/odtheking/odin/features/impl/dungeon/dungeonwaypoints/DungeonWaypointEditor.kt          
https://github.com/odtheking/Odin/blob/main/src/main/kotlin/com/odtheking/odin/features/impl/dungeon/dungeonwaypoints/SecretWaypoints.kt            
