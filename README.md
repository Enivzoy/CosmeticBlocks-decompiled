# CB — reconstructed Minecraft 26.2 project

This is a source reconstruction of the supplied `cb-1.0.0.jar`, not the original repository. The JAR contained compiled Kotlin classes; CFR recovered Java-like reference source, but that output is incomplete and cannot be compiled as Java. See [`docs/cfr-summary.txt`](docs/cfr-summary.txt). The build uses the original classes in [`libs/cb-1.0.0.jar`](libs/cb-1.0.0.jar) and packages the reconstructed resources.

The project targets Minecraft 26.2 and Fabric. The source was updated for the 26.2 screen API (`Minecraft.gui.setScreen` / `gui.getScreen`). The NanoVG renderer calls LWJGL OpenGL APIs directly, so use Minecraft's OpenGL backend; the experimental Vulkan backend is not supported by this reconstruction.

## Open and run in IntelliJ IDEA

1. Open this project folder in IntelliJ IDEA and import it as a Gradle project.
2. Set the Gradle JVM to JDK 25.
3. Let Gradle sync. The included wrapper downloads Gradle 9.7.0 and resolves the Minecraft/Fabric dependencies on first sync.
4. Run the `runClient` Gradle task to start a development client, or run `build` to create the mod JAR.

The wrapper scripts are `gradlew` and `gradlew.bat`. From PowerShell, you can also run `./gradlew.bat build -x test`.

Configured versions: Minecraft 26.2, Fabric Loader 0.19.5, Fabric API 0.161.0+26.2, Fabric Language Kotlin 1.14.2+kotlin.2.4.21, and Fabric Loom 1.18.4.

The original JAR declared CC0-1.0 in `fabric.mod.json`; no original GitHub URL or author names were embedded in the JAR. This reconstructed tree should not be presented as the original upstream source.
