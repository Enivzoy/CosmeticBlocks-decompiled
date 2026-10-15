# CosmeticBlocks — Reborn
This is a fork of CosmeticBlocks-decompiled, upgrading and updating on the previous system [@native-java branch]    
This build/version adds more features such as per floor config system and more! 

## Usage
grab the latest action build and run `/cb` in game to start configuration       
all config files are in `.minecraft/cb/cb`      
## TODO / Features

- split settings into Global Cosmetics / Dungeon Config / Settings tabs [DONE]
- \*per-config water color + per-config block remaps [DONE]  
    
- Freecam [DONE] *CHEAT
- \*Avoid getting banned for blinking [DONE] whoops!  
    
- \*add per dungeon load/save system [DONE]
- \*incorporate it into the gui [DONE]
- revert back to original config after a dungeon is done [DONE]
    
- Per Island(Outside Dungeons) [SOON]
          
- Make it easier to share configs [SOON]
- add 26.3 support [WORKING ON IT]
## Building From Source & Contribution

1. Open this project folder in IntelliJ IDEA and import it as a Gradle project.
2. Set the Gradle JVM to JDK 25.
3. Let Gradle sync. The included wrapper downloads Gradle 9.7.0 and resolves the Minecraft/Fabric dependencies on first sync.
4. Run the `runClient` Gradle task to start a development client, or run `build` to create the mod JAR.

Configured versions: Minecraft 26.2, Fabric Loader 0.19.5, Fabric API 0.161.0+26.2, Fabric Language Kotlin 1.14.2+kotlin.2.4.21, and Fabric Loom 1.18.4.

feel free to add/change features (i will/most likely accept any pull requests)


## references
https://github.com/skies-starred/OdinClient/blob/master/src/main/kotlin/foo/starred/odinclient/features/impl/dungeons/CheaterMap.kt        
https://github.com/skies-starred/OdinClient/blob/master/src/main/kotlin/foo/starred/odinclient/features/impl/floor7/FuckDiorite.kt    
https://github.com/Synnerz/devonian/blob/26.3/src/main/kotlin/com/github/synnerz/devonian/features/dungeons/clear/DungeonWaypoints.kt           
https://github.com/odtheking/Odin/blob/main/src/main/kotlin/com/odtheking/odin/features/impl/dungeon/dungeonwaypoints/DungeonWaypointPacks.kt          
https://github.com/odtheking/Odin/blob/main/src/main/kotlin/com/odtheking/odin/features/impl/dungeon/dungeonwaypoints/DungeonWaypointEditor.kt          
https://github.com/odtheking/Odin/blob/main/src/main/kotlin/com/odtheking/odin/features/impl/dungeon/dungeonwaypoints/SecretWaypoints.kt            
