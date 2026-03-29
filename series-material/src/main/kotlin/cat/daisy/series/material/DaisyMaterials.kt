package cat.daisy.series.material

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import org.bukkit.Material

object DaisyMaterials : DaisySeriesParser<Material> {
    private val curatedAliases =
        mapOf(
            "gapple" to Material.GOLDEN_APPLE,
            "god_apple" to Material.ENCHANTED_GOLDEN_APPLE,
            "xp_bottle" to Material.EXPERIENCE_BOTTLE,
        )

    private val canonicalKeys = Material.values().associateBy { key(it) }

    private val lookup =
        buildMap {
            canonicalKeys.forEach { (materialKey, material) ->
                put(materialKey, material)
            }
            curatedAliases.forEach { (alias, material) ->
                put(alias, material)
            }
        }

    override fun parse(input: String): Material =
        parseOrNull(input) ?: throw buildUnknownFailure("material", input, lookup.keys)

    override fun parseOrNull(input: String): Material? = lookup[normalizeSeriesInput(input)]

    fun key(material: Material): String = enumKey(material.name)

    fun displayName(material: Material): String = displayNameFromKey(key(material))

    fun aliases(material: Material): Set<String> =
        curatedAliases
            .filterValues { it == material }
            .keys
            .toSortedSet()

    fun isBlock(material: Material): Boolean = material.isBlock

    fun isItem(material: Material): Boolean = material.isItem
}

fun Material.seriesKey(): String = DaisyMaterials.key(this)

fun Material.seriesDisplayName(): String = DaisyMaterials.displayName(this)
