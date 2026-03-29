pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://jitpack.io")
    }
}

rootProject.name = "DaisySeries"

include(
    "series-base",
    "series-material",
    "series-sound",
    "series-itemflag",
    "series-enchantment",
    "series-potion",
    "series-biome",
    "series-entity",
    "series-game-mode",
    "series-particle",
    "series-statistic",
    "series-all",
    "example-plugin",
)
