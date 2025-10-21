import org.gradle.api.initialization.resolve.RepositoriesMode

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "Codeforces-Visualizer"

include(
    ":composeApp",
    ":shared",
    ":core",
    ":contest",
    ":webview",
    ":profile",
    ":compare",
    ":preference"
)
