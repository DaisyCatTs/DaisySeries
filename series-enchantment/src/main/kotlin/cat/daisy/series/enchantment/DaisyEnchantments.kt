package cat.daisy.series.enchantment

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import cat.daisy.series.internal.normalizeSeriesKey
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import java.lang.reflect.Modifier

object DaisyEnchantments : DaisySeriesParser<Enchantment> {
    private val curatedAliases =
        mapOf(
            "protection" to setOf("prot"),
            "feather_falling" to setOf("ff"),
            "knockback" to setOf("kb"),
        )

    private val canonicalKeys by lazy {
        Enchantment::class.java.declaredFields
            .filter { field ->
                field.type == Enchantment::class.java &&
                    Modifier.isStatic(field.modifiers)
            }
            .map { enumKey(it.name) }
            .toList()
    }

    private val suggestionKeys by lazy { (canonicalKeys + curatedAliases.keys).distinct() }

    private val lookup by lazy {
        buildMap {
            canonicalKeys.forEach { enchantmentKey ->
                resolveEnchantment(enchantmentKey)?.let { enchantment ->
                    put(enchantmentKey, enchantment)
                    curatedAliases[enchantmentKey].orEmpty().forEach { alias ->
                        put(normalizeSeriesKey(alias), enchantment)
                    }
                }
            }
        }
    }

    override fun parse(input: String): Enchantment =
        parseOrNull(input) ?: throw buildUnknownFailure("enchantment", input, suggestionKeys)

    override fun parseOrNull(input: String): Enchantment? = lookup[normalizeSeriesInput(input)]

    fun key(enchantment: Enchantment): String = enchantmentKey(enchantment)

    fun displayName(enchantment: Enchantment): String = displayNameFromKey(key(enchantment))

    fun aliases(enchantment: Enchantment): Set<String> = curatedAliases[key(enchantment)].orEmpty().toSortedSet()
}

private fun enchantmentKey(enchantment: Enchantment): String = normalizeSeriesKey(enchantment.key.key)

private fun resolveEnchantment(key: String): Enchantment? =
    runCatching { Enchantment.getByKey(NamespacedKey.minecraft(key)) }.getOrNull()
