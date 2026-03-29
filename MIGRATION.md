# Migration

## Why DaisySeries exists

DaisySeries replaces the repetitive enum parsing helpers that normal plugin projects keep rewriting:

- `Material.valueOf(...)` wrappers
- hand-written alias maps
- ad hoc lowercase serialization
- one-off friendly-name helpers

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

