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
- `series-biome`
- `series-villager-profession`
- `series-attribute`
- `series-entity`
- `series-game-mode`
- `series-difficulty`
- `series-blockface`
- `series-damage-cause`
- `series-operation`
- `series-pattern-type`
- `series-particle`
- `series-statistic`
- `series-all`
- `series-base`

## Modern Gap Audit

The next DaisySeries wave should stay focused on modern Paper parser families that are clearly reusable and clearly helped by canonical keys plus normalization.

### Tier A: next-wave candidates

These are the strongest current candidates for the next implementation wave:

- `series-world-type`
- `series-villager-type`
- `series-map-cursor-type`

### Tier B: plausible later candidates

These still fit DaisySeries, but they are weaker follow-ups than Tier A:

- `series-damage-source`
- `series-advancement-frame`

### Tier C: intentionally out of scope for now

These should stay out unless repeated real plugin needs prove otherwise:

- broad legacy compatibility helpers
- arbitrary enum families that do not materially benefit from aliases or canonical keys
- helpers that leak config or runtime ownership into DaisySeries

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
val profession = DaisyVillagerProfessions.parse(config.profession)
val attribute = DaisyAttributes.parse(config.attribute)
val difficulty = DaisyDifficulties.parse(config.difficulty)
val sound = DaisySounds.parse(config.feedbackSound)
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
- a dumping ground for every Bukkit enum

## Success Criteria

DaisySeries is succeeding when:

- plugin code stops inventing local enum helpers
- config parsing becomes consistent across projects
- migration between plugins is easier because keys are stable
- every module feels obvious after learning one of them
- the repo reads like a focused utility product, not a dumping ground
