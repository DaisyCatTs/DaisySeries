# DaisySeries

Kotlin-first enum and config parsing utilities for modern Paper plugins.

## Why DaisySeries

DaisySeries removes the boring helper layer every plugin ends up rebuilding for Bukkit enums:

- parse config-friendly strings into Bukkit values
- expose stable lowercase serialization keys
- expose UI-friendly display names
- support a small curated alias set where it genuinely helps

This release ships a broader modern parsing pack for:

- Materials
- Sounds
- Item flags
- Enchantments
- Potions
- Biomes
- Entity types
- Game modes
- Particles
- Statistics

## Installation

```kotlin
repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    implementation("cat.daisy:DaisySeries:0.2.0")
}
```

DaisySeries `0.2.0` is the first formal release of the modern parser surface. It is meant to be the focused modern Paper alternative to plugin-local enum glue and XSeries-style parsing helpers, not a legacy compatibility museum.

Or install a narrower module:

```kotlin
implementation("cat.daisy:series-material:0.2.0")
implementation("cat.daisy:series-sound:0.2.0")
implementation("cat.daisy:series-itemflag:0.2.0")
implementation("cat.daisy:series-enchantment:0.2.0")
implementation("cat.daisy:series-potion:0.2.0")
implementation("cat.daisy:series-biome:0.2.0")
implementation("cat.daisy:series-entity:0.2.0")
implementation("cat.daisy:series-game-mode:0.2.0")
implementation("cat.daisy:series-particle:0.2.0")
implementation("cat.daisy:series-statistic:0.2.0")
```

## Quick example

```kotlin
val icon = DaisyMaterials.parse("diamond sword")
val sound = DaisySounds.parse("entity player levelup")
val flags = DaisyItemFlags.parseMany(listOf("hide enchants", "hide attributes"))
val enchantment = DaisyEnchantments.parse("sharpness")
val effect = DaisyPotions.parse("slow falling")
val biome = DaisyBiomes.parse("cherry grove")
val entity = DaisyEntities.parse("zombie villager")
val gameMode = DaisyGameModes.parse("surv")
val particle = DaisyParticles.parse("totem")
val statistic = DaisyStatistics.parse("player kills")

logger.info("Icon: ${DaisyMaterials.displayName(icon)} (${DaisyMaterials.key(icon)})")
logger.info("Sound: ${DaisySounds.displayName(sound)}")
logger.info("Flags: ${flags.joinToString { DaisyItemFlags.displayName(it) }}")
logger.info("Enchant: ${DaisyEnchantments.displayName(enchantment)}")
logger.info("Effect: ${DaisyPotions.displayName(effect)}")
logger.info("Biome: ${DaisyBiomes.displayName(biome)}")
logger.info("Entity: ${DaisyEntities.displayName(entity)}")
logger.info("Game mode: ${DaisyGameModes.displayName(gameMode)}")
logger.info("Particle: ${DaisyParticles.displayName(particle)}")
logger.info("Statistic: ${DaisyStatistics.displayName(statistic)}")
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

`DaisyEnchantments`, `DaisyPotions`, and some newer registry-sensitive families resolve against live Paper behavior at runtime.
That means their successful parsing path is intended for real plugin execution on Paper, while plain JVM unit tests cover normalization, suggestions, and fail-soft behavior where Paper bootstrap is not available.

## What DaisySeries does not do

- it is not a full framework
- it is not a config system
- it does not try to solve every legacy-version enum rename
- it does not return aliases as canonical output

DaisySeries is modern Paper-first on purpose.

That also means DaisySeries is not trying to win by recreating every legacy-era XSeries edge. The target is the modern Minecraft parsing layer you actually want to build on now: modern Paper-first, canonical-key-first, and small enough to stay coherent.

## Example plugin

See [`example-plugin`](./example-plugin) for a small copyable Paper example using the starter-pack modules together.

## Module family

The current DaisySeries family now includes:

- Materials
- Sounds
- Item flags
- Enchantments
- Potions
- Biomes
- Entity types
- Game modes
- Particles
- Statistics

## What comes next

The next DaisySeries implementation wave is not open-ended. The current audit direction is:

- **Next-wave candidates**: villager professions, attributes, difficulties, and block faces
- **Likely later**: damage causes, operations, and pattern types
- **Currently out of scope unless a strong need appears**: world types and broad legacy compatibility helpers

See [DAISYSERIES_FINAL_GOAL.md](./DAISYSERIES_FINAL_GOAL.md) for the reasoning and guardrails.

## Vision

- Final product direction: [DAISYSERIES_FINAL_GOAL.md](./DAISYSERIES_FINAL_GOAL.md)
- Migration guide: [MIGRATION.md](./MIGRATION.md)
- Changelog: [CHANGELOG.md](./CHANGELOG.md)

## Related Projects

- [DaisyCore](https://github.com/DaisyCatTs/DaisyCore): the Kotlin-first Paper runtime platform for commands, menus, sidebars, tablists, and shared text
- [DaisyConfig](https://github.com/DaisyCatTs/DaisyConfig): typed YAML, managed lifecycle, module bundles, and DaisyCore-friendly text/config integration

## License

MIT
