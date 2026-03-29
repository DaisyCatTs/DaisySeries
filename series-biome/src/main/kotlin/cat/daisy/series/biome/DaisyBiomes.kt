package cat.daisy.series.biome

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import cat.daisy.series.internal.normalizeSeriesKey
import org.bukkit.NamespacedKey
import org.bukkit.block.Biome
import java.lang.reflect.Modifier

object DaisyBiomes : DaisySeriesParser<Biome> {
    private val curatedAliases =
        mapOf(
            "nether_wastes" to setOf("nether"),
            "the_end" to setOf("end"),
        )

    private val canonicalKeys by lazy {
        Biome::class.java.declaredFields
            .filter { field ->
                field.type == Biome::class.java &&
                    Modifier.isStatic(field.modifiers)
            }
            .map { enumKey(it.name) }
            .toList()
    }

    private val suggestionKeys by lazy { (canonicalKeys + curatedAliases.values.flatten()).distinct() }

    private val lookup by lazy {
        buildMap {
            canonicalKeys.forEach { biomeKey ->
                resolveBiome(biomeKey)?.let { biome ->
                    put(biomeKey, biome)
                    curatedAliases[biomeKey].orEmpty().forEach { alias ->
                        put(normalizeSeriesKey(alias), biome)
                    }
                }
            }
        }
    }

    override fun parse(input: String): Biome =
        parseOrNull(input) ?: throw buildUnknownFailure("biome", input, suggestionKeys)

    override fun parseOrNull(input: String): Biome? = lookup[normalizeSeriesInput(input)]

    fun key(biome: Biome): String = normalizeSeriesKey(biome.key.key)

    fun displayName(biome: Biome): String = displayNameFromKey(key(biome))

    fun aliases(biome: Biome): Set<String> = curatedAliases[key(biome)].orEmpty().toSortedSet()
}

private fun resolveBiome(key: String): Biome? =
    runCatching { Biome.valueOf(NamespacedKey.minecraft(key).key.uppercase()) }.getOrNull()
