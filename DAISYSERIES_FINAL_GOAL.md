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

## Module Inclusion Policy

DaisySeries should become the modern replacement for XSeries-style helper glue by being better scoped, not by wrapping every Paper enum.

A new DaisySeries family is allowed only if it passes all of these gates:

1. **Repetition gate**
   The family shows up repeatedly across real modern plugin code, not just one niche feature.
2. **Config and serialization gate**
   Plugin authors commonly put it in config, storage, migrations, or admin-editable values.
3. **Normalization gate**
   The family benefits from flexible parse input like lowercase, spaces, kebab-case, underscore, or namespaced forms.
4. **Canonical-key gate**
   A stable lowercase key materially improves storage, migration, docs, or interoperability.
5. **Parser-contract gate**
   The family fits the standard DaisySeries surface cleanly:
   - `parse(input)`
   - `parseOrNull(input)`
   - `key(value)`
   - `displayName(value)`
   - `aliases(value)`
6. **Scope gate**
   The family does not drag DaisySeries into runtime ownership, config ownership, giant alias maintenance, or framework behavior.
7. **Usage-frequency gate**
   A normal plugin author is likely to use the family more than once and benefit from removing repeated helper glue.

If any of those fail, the family should stay out.

### The practical gate

Before approving a family, ask:

> Will this remove helper glue that plugin authors would otherwise write repeatedly in many places, not just once?

Good signals:

- shows up in config often
- shows up in admin-editable settings
- shows up in serialized state or migrations
- shows up in UI labels or docs examples
- shows up across multiple feature areas
- authors often normalize the same strings repeatedly

Bad signals:

- only used in one command or one niche feature
- mostly hardcoded by developers
- plain enum names are already good enough
- little to no alias or canonical-key value
- only useful to claim broader coverage

## Scoring Rubric

Every proposed family should be scored out of 25 before implementation.

### 1. Reuse across plugins: 0-5

- `0`: basically one-off
- `1`: niche single-feature use
- `3`: moderate reuse
- `5`: broad repeated reuse across many plugin types

### 2. Config frequency: 0-5

- `0`: rarely configured
- `1`: mostly hardcoded in code
- `3`: sometimes configured
- `5`: commonly config-backed

### 3. Normalization value: 0-5

- `0`: normalization adds almost nothing
- `1`: tiny convenience only
- `3`: useful in practice
- `5`: normalization removes real repeated glue

### 4. Canonical-key value: 0-5

- `0`: not really useful
- `1`: minor value
- `3`: useful
- `5`: clearly valuable and repeatedly useful

### 5. Contract fit and maintenance sanity: 0-5

- `0`: bad fit
- `1`: awkward fit
- `3`: manageable
- `5`: clean natural fit

## Approval Thresholds

- `21-25`: Tier A, approved next-wave candidate
- `16-20`: Tier B, plausible later candidate
- `10-15`: Tier C, weak or uncertain candidate
- `<10`: Tier D, reject

## Automatic Rejection Rules

Reject automatically even if the raw score looks decent when any of these are true:

- it is mostly legacy-compatibility baggage
- it is primarily runtime-owned behavior, not parsing and normalization
- it needs huge alias maps to feel useful
- it is too niche to justify the public API and docs overhead
- it is mainly something plugin authors would use once in one tiny feature

## Candidate Tier Definitions

- **Tier A**
  Clearly useful, clearly repeated, clearly improved by DaisySeries, worth immediate implementation.
- **Tier B**
  Useful enough to track, but not urgent enough for the immediate next wave.
- **Tier C**
  Weak or uncertain. Needs more real evidence before inclusion.
- **Tier D**
  Explicitly rejected. Not a DaisySeries problem or not useful enough to justify the product cost.

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

### Current scored candidates

| Family | Paper type | Reuse | Config | Normalization | Canonical key | Contract fit | Total | Tier | Notes |
| --- | --- | ---: | ---: | ---: | ---: | ---: | ---: | --- | --- |
| `series-world-type` | `WorldType` | 2 | 2 | 1 | 2 | 4 | 11 | C | Clean contract fit, but too rarely configured in modern Paper work to justify immediate inclusion. |
| `series-villager-type` | `Villager.Type` | 3 | 2 | 2 | 2 | 4 | 13 | C | Some config value for NPC-heavy plugins, but not broad enough yet. |
| `series-map-cursor-type` | `MapCursor.Type` | 2 | 1 | 1 | 2 | 4 | 10 | C | Fits the contract, but mostly niche and weak on repeated config use. |
| `series-damage-source` | `DamageSource` | 2 | 1 | 1 | 1 | 1 | 6 | D | Too runtime-owned and awkward for the standard parser contract. |
| `series-advancement-frame` | `AdvancementDisplay.Frame` | 1 | 1 | 1 | 1 | 4 | 8 | D | Too niche and too low-frequency to justify a public DaisySeries family. |

### Current Tier A

No family currently clears the approval threshold.

That is acceptable. DaisySeries should not add modules just to keep expanding.

### Current Tier B

No family currently clears Tier B strongly enough to schedule immediately.

### Current Tier C

- `series-world-type`
- `series-villager-type`
- `series-map-cursor-type`

These should stay tracked but deferred until repeated real plugin use proves they deserve promotion.

### Current Tier D

- `series-damage-source`
- `series-advancement-frame`
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

It also should not add parser families just because another library exposed them once. The bar is repeated real modern-plugin usefulness, not raw module count.

## Success Criteria

DaisySeries is succeeding when:

- plugin code stops inventing local enum helpers
- config parsing becomes consistent across projects
- migration between plugins is easier because keys are stable
- every module feels obvious after learning one of them
- the repo reads like a focused utility product, not a dumping ground
