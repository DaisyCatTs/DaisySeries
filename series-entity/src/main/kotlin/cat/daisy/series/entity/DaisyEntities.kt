package cat.daisy.series.entity

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import org.bukkit.entity.EntityType

object DaisyEntities : DaisySeriesParser<EntityType> {
    private val curatedAliases =
        mapOf(
            "mushroom_cow" to EntityType.MOOSHROOM,
        )

    private val canonicalKeys = EntityType.values().associateBy { key(it) }

    private val lookup =
        buildMap {
            canonicalKeys.forEach { (entityKey, entityType) ->
                put(entityKey, entityType)
            }
            curatedAliases.forEach { (alias, entityType) ->
                put(alias, entityType)
            }
        }

    override fun parse(input: String): EntityType =
        parseOrNull(input) ?: throw buildUnknownFailure("entity type", input, lookup.keys)

    override fun parseOrNull(input: String): EntityType? = lookup[normalizeSeriesInput(input)]

    fun key(entityType: EntityType): String = enumKey(entityType.name)

    fun displayName(entityType: EntityType): String = displayNameFromKey(key(entityType))

    fun aliases(entityType: EntityType): Set<String> =
        curatedAliases
            .filterValues { it == entityType }
            .keys
            .toSortedSet()
}
