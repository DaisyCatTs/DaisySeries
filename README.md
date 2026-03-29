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
```

## Quick example

```kotlin
val icon = DaisyMaterials.parse("diamond sword")
val sound = DaisySounds.parse("entity player levelup")
val flags = DaisyItemFlags.parseMany(listOf("hide enchants", "hide attributes"))

logger.info("Icon: ${DaisyMaterials.displayName(icon)} (${DaisyMaterials.key(icon)})")
logger.info("Sound: ${DaisySounds.displayName(sound)}")
logger.info("Flags: ${flags.joinToString { DaisyItemFlags.displayName(it) }}")
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

## What DaisySeries does not do

- it is not a full framework
- it is not a config system
- it does not try to solve every legacy-version enum rename
- it does not return aliases as canonical output

The first snapshot is modern Paper-first on purpose.

## Example plugin

See [`example-plugin`](./example-plugin) for a small copyable Paper example using the starter-pack modules together.

## Roadmap

Planned next modules after the starter pack:

- Enchantments
- Potions

## License

MIT

