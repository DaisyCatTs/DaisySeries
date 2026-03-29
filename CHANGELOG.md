## [Unreleased]

### Planned
- next parser-wave evaluation around damage causes, operations, and pattern types
- more cross-product examples using the expanded parser family

## [0.3.0]

### Added
- `series-villager-profession`
- `series-attribute`
- `series-difficulty`
- `series-blockface`
- DaisySeries example updates for villager professions, attributes, difficulties, and block faces
- DaisyConfig codec integration for villager professions, attributes, difficulties, and block faces
- DaisySeries docs, dictionary pages, and API references for the second-wave parser family

### Changed
- DaisySeries migration guidance now treats the second-wave families as part of the modern Paper parser surface
- the DaisySeries gap audit now classifies the next plausible families instead of leaving the second wave as planning-only work

## [0.2.0]

### Added
- `series-enchantment`
- `series-potion`
- `series-biome`
- `series-entity`
- `series-game-mode`
- `series-particle`
- `series-statistic`
- DaisySeries example updates for enchantments and potion effects
- DaisySeries example updates for biomes, entity types, game modes, particles, and statistics
- DaisySeries docs coverage for enchantments, potions, and the expanded modern module family
- DaisyConfig codec integration for the expanded DaisySeries parser family

### Changed
- DaisySeries now positions itself as a modern Paper-first parsing layer instead of a legacy compatibility helper
- runtime notes now explicitly call out registry-backed parser behavior where live Paper resolution is required

## [0.1.0-SNAPSHOT]

### Added
- DaisySeries repo skeleton
- `series-base`
- `series-material`
- `series-sound`
- `series-itemflag`
- `series-all`
- example plugin
- README and migration docs
