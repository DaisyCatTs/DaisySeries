# Migration

## Why DaisySeries exists

DaisySeries replaces the repetitive enum parsing helpers that normal plugin projects keep rewriting:

- `Material.valueOf(...)` wrappers
- hand-written alias maps
- ad hoc lowercase serialization
- one-off friendly-name helpers
- XSeries-style utility glue for modern Paper-only projects

## Replace `Material.valueOf(...)`

Instead of:

```kotlin
val material = Material.valueOf(configString.uppercase())
```

Use:

```kotlin
val material = DaisyMaterials.parse(configString)
```

This accepts normalized inputs such as spaces, kebab-case, lowercase names, and curated aliases.

## Replace plugin-local alias maps

Instead of keeping custom maps for common inputs like `gapple` or `hide enchants`, use the curated DaisySeries parsers:

```kotlin
val icon = DaisyMaterials.parse("gapple")
val flags = DaisyItemFlags.parseMany(listOf("hide enchants"))
```

## Replace hand-written friendly-name helpers

Instead of title-casing raw enum names in each plugin:

```kotlin
val label = DaisyMaterials.displayName(material)
val soundLabel = DaisySounds.displayName(sound)
```

## Replace scattered lowercase serialization

Instead of calling `name.lowercase()` everywhere, use the canonical DaisySeries key:

```kotlin
val key = DaisyMaterials.key(material)
```

`key(...)` is the stable config-safe serialization form. Aliases are accepted on input only.

## Add enchantment parsing

Instead of plugin-local enchantment helper maps:

```kotlin
val enchantment = DaisyEnchantments.parse(config.enchantment)
```

## Add potion-effect parsing

Instead of local potion-effect wrappers:

```kotlin
val effect = DaisyPotions.parse(config.effect)
```

## Add biome, entity, particle, game-mode, and statistic parsing

The same replacement pattern now covers more of the modern Paper surface:

```kotlin
val biome = DaisyBiomes.parse(config.biome)
val entity = DaisyEntities.parse(config.entity)
val gameMode = DaisyGameModes.parse(config.defaultGameMode)
val particle = DaisyParticles.parse(config.particle)
val statistic = DaisyStatistics.parse(config.statistic)
```

## Add villager-profession, attribute, difficulty, and block-face parsing

```kotlin
val profession = DaisyVillagerProfessions.parse(config.profession)
val attribute = DaisyAttributes.parse(config.attribute)
val difficulty = DaisyDifficulties.parse(config.difficulty)
val facing = DaisyBlockFaces.parse(config.facing)
```

## Add damage-cause, operation, and banner-pattern parsing

```kotlin
val damageCause = DaisyDamageCauses.parse(config.damageCause)
val operation = DaisyOperations.parse(config.operation)
val patternType = DaisyPatternTypes.parse(config.pattern)
```

## Why DaisySeries instead of XSeries-style glue

For modern Minecraft, DaisySeries is trying to win on:

- Kotlin-first APIs
- stable canonical lowercase keys
- curated aliases instead of giant legacy maps
- stronger docs and migration guidance
- cleaner integration with DaisyConfig and DaisyCore

It is not trying to win by recreating every historical compatibility edge from older helper libraries.

## Adopt incrementally

You do not need to replace every helper at once.

The normal migration order is:

1. replace the worst local `valueOf(...)` wrappers first
2. replace scattered alias maps for the families DaisySeries already ships
3. standardize serialization on `key(...)`
4. pull DaisyConfig in later only if those values need typed YAML or managed lifecycle
5. pull DaisyCore in later only if those parsed values feed runtime systems

That keeps DaisySeries as the parser layer only, which is the intended boundary.

## Current scope vs future scope

DaisySeries already covers the modern parser families most config-heavy plugins hit first:

- materials
- sounds
- item flags
- enchantments
- potion effects
- biomes
- villager professions
- attributes
- entity types
- game modes
- difficulties
- block faces
- damage causes
- operations
- pattern types
- particles
- statistics

The next-wave shortlist is intentionally smaller than "everything XSeries ever touched." Right now the strongest follow-up candidates are:

- world types
- villager types
- map cursor types
