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

## Why DaisySeries instead of XSeries-style glue

For modern Minecraft, DaisySeries is trying to win on:

- Kotlin-first APIs
- stable canonical lowercase keys
- curated aliases instead of giant legacy maps
- stronger docs and migration guidance
- cleaner integration with DaisyConfig and DaisyCore

It is not trying to win by recreating every historical compatibility edge from older helper libraries.
