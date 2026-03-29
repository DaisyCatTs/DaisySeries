# DaisySeries

Kotlin-first enum and config parsing utilities for modern Paper plugins.

## Why DaisySeries

DaisySeries removes the boring helper layer every plugin ends up rebuilding for Bukkit enums:

- parse config-friendly strings into Bukkit values
- expose stable lowercase serialization keys
- expose UI-friendly display names
- support a small curated alias set where it genuinely helps

This first snapshot ships a starter pack for:

- Materials
- Sounds
- Item flags
- Enchantments
- Potions

## Installation

```kotlin
repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    implementation("cat.daisy:DaisySeries:0.1.0-SNAPSHOT")
}
```

Or install a narrower module:

```kotlin
implementation("cat.daisy:series-material:0.1.0-SNAPSHOT")
implementation("cat.daisy:series-sound:0.1.0-SNAPSHOT")
implementation("cat.daisy:series-itemflag:0.1.0-SNAPSHOT")
implementation("cat.daisy:series-enchantment:0.1.0-SNAPSHOT")
implementation("cat.daisy:series-potion:0.1.0-SNAPSHOT")
```

## Quick example

```kotlin
val icon = DaisyMaterials.parse("diamond sword")
val sound = DaisySounds.parse("entity player levelup")
val flags = DaisyItemFlags.parseMany(listOf("hide enchants", "hide attributes"))
val enchantment = DaisyEnchantments.parse("sharpness")
val effect = DaisyPotions.parse("slow falling")

logger.info("Icon: ${DaisyMaterials.displayName(icon)} (${DaisyMaterials.key(icon)})")
logger.info("Sound: ${DaisySounds.displayName(sound)}")
logger.info("Flags: ${flags.joinToString { DaisyItemFlags.displayName(it) }}")
logger.info("Enchant: ${DaisyEnchantments.displayName(enchantment)}")
logger.info("Effect: ${DaisyPotions.displayName(effect)}")
```

## IntelliJ Setup

- Open the repo as a Gradle project in IntelliJ IDEA.
- Use the checked-in Gradle wrapper.
- Recommended JDK: Java 21.
- On this machine and similar Windows setups, Corretto 21 has been the safest local verification JDK.
- Useful Gradle runs from IntelliJ or the terminal:

```bash
./gradlew.bat --no-daemon test
./gradlew.bat --no-daemon :example-plugin:compileKotlin
```

## Parsing rules

- accepts lowercase input
- accepts spaced input
- accepts kebab-case input
- accepts canonical underscore names
- accepts a small curated alias set
- canonical keys are always lowercase underscore names

Examples:

- `diamond_sword`
- `diamond sword`
- `diamond-sword`
- `minecraft:diamond_sword`
- `sharpness`
- `slow_falling`

## Runtime note

`DaisyEnchantments` and `DaisyPotions` resolve against the live Paper registry at runtime.
That means their successful parsing path is intended for real plugin execution on Paper, while plain JVM unit tests cover the normalization, suggestion, and fail-soft behavior around those modules.

## What DaisySeries does not do

- it is not a full framework
- it is not a config system
- it does not try to solve every legacy-version enum rename
- it does not return aliases as canonical output

The first snapshot is modern Paper-first on purpose.

## Example plugin

See [`example-plugin`](./example-plugin) for a small copyable Paper example using the starter-pack modules together.

## Module family

The current DaisySeries family now includes:

- Materials
- Sounds
- Item flags
- Enchantments
- Potions

## Vision

- Final product direction: [DAISYSERIES_FINAL_GOAL.md](./DAISYSERIES_FINAL_GOAL.md)
- Migration guide: [MIGRATION.md](./MIGRATION.md)
- Changelog: [CHANGELOG.md](./CHANGELOG.md)

## Related Projects

- [DaisyCore](https://github.com/DaisyCatTs/DaisyCore): the Kotlin-first Paper runtime platform for commands, menus, sidebars, tablists, and shared text
- [DaisyConfig](https://github.com/DaisyCatTs/DaisyConfig): typed YAML config loading and DaisyCore-friendly text/config integration

## License

MIT
