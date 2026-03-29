# DaisySeries Final Goal

## Purpose

DaisySeries exists to remove the repeated config-parsing and enum-helper layer that most Paper plugins end up rebuilding by hand.

The end goal is a small family of Kotlin-first utilities that make config-driven plugin code:

- shorter
- safer
- more consistent
- easier to serialize
- easier to migrate

## Product Position

DaisySeries is not DaisyCore.

- `DaisyCore` is the runtime/platform library
- `DaisySeries` is the lightweight parser and utility library

Users should be able to adopt DaisySeries:

- with DaisyCore
- without DaisyCore
- in tiny plugins
- in large network codebases

## Core Rules

Every DaisySeries module should feel the same.

Each value family should expose:

- `parse(input)`
- `parseOrNull(input)`
- `key(value)`
- `displayName(value)`
- `aliases(value)`

The contract is the product.

## Current Module Family

Implemented now:

- `series-material`
- `series-sound`
- `series-itemflag`
- `series-enchantment`
- `series-potion`
- `series-all`
- `series-base`

## Expected Future Module Family

Likely next DaisySeries candidates:

- `series-biome`
- `series-entity`
- `series-villager-profession`
- `series-game-mode`
- `series-damage-cause`
- `series-particle`
- `series-statistic`

Potential second wave if there is real reuse:

- `series-blockface`
- `series-attribute`
- `series-operation`
- `series-pattern-type`
- `series-difficulty`
- `series-world-type`

## End-State Design Standard

Every mature DaisySeries module should provide:

- normalized input parsing
- curated aliases only
- stable canonical lowercase underscore keys
- UI-friendly display names
- useful parse failures with suggestions
- docs with examples
- tests that prove normalization and failure behavior

## Runtime Truth

Some Paper types are safe to resolve in plain JVM tests.

Some Paper types are registry-backed enough that real successful resolution belongs to plugin runtime instead of plain unit bootstrap.

That means DaisySeries should stay honest:

- unit tests prove normalization, suggestions, and fail-soft behavior
- runtime resolution happens on live Paper when the API actually requires it

## Example Of The Final Authoring Style

```kotlin
val icon = DaisyMaterials.parse(config.icon)
val flags = DaisyItemFlags.parseMany(config.flags)
val enchantment = DaisyEnchantments.parse(config.enchantment)
val effect = DaisyPotions.parse(config.effect)
val sound = DaisySounds.parse(config.feedbackSound)

logger.info("Icon: ${DaisyMaterials.displayName(icon)}")
logger.info("Enchant: ${DaisyEnchantments.displayName(enchantment)}")
logger.info("Effect: ${DaisyPotions.displayName(effect)}")
```

The plugin author should not need:

- `valueOf(...)` wrappers
- lowercase helper functions
- giant alias maps
- hand-written friendly-name utilities
- inconsistent serialization rules

## Non-Goals

DaisySeries should not become:

- a framework
- a config loader
- a kitchen-sink compatibility swamp
- a legacy-name museum
- a replacement for DaisyCore

## Success Criteria

DaisySeries is succeeding when:

- plugin code stops inventing local enum helpers
- config parsing becomes consistent across projects
- migration between plugins is easier because keys are stable
- every module feels obvious after learning one of them
- the repo reads like a focused utility product, not a dumping ground
