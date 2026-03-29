package cat.daisy.series.potion

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import cat.daisy.series.internal.normalizeSeriesKey
import org.bukkit.NamespacedKey
import org.bukkit.potion.PotionEffectType
import java.lang.reflect.Modifier

object DaisyPotions : DaisySeriesParser<PotionEffectType> {
    private val curatedAliases =
        mapOf(
            "resistance" to setOf("res"),
            "fire_resistance" to setOf("fire_res"),
        )

    private val canonicalKeys by lazy {
        PotionEffectType::class.java.declaredFields
            .filter { field ->
                field.type == PotionEffectType::class.java &&
                    Modifier.isStatic(field.modifiers)
            }
            .map { enumKey(it.name) }
            .toList()
    }

    private val suggestionKeys by lazy { (canonicalKeys + curatedAliases.keys).distinct() }

    private val lookup by lazy {
        buildMap {
            canonicalKeys.forEach { effectKey ->
                resolvePotion(effectKey)?.let { effect ->
                    put(effectKey, effect)
                    curatedAliases[effectKey].orEmpty().forEach { alias ->
                        put(normalizeSeriesKey(alias), effect)
                    }
                }
            }
        }
    }

    override fun parse(input: String): PotionEffectType =
        parseOrNull(input) ?: throw buildUnknownFailure("potion effect", input, suggestionKeys)

    override fun parseOrNull(input: String): PotionEffectType? = lookup[normalizeSeriesInput(input)]

    fun key(effect: PotionEffectType): String = potionKey(effect)

    fun displayName(effect: PotionEffectType): String = displayNameFromKey(key(effect))

    fun aliases(effect: PotionEffectType): Set<String> = curatedAliases[key(effect)].orEmpty().toSortedSet()
}

private fun potionKey(effect: PotionEffectType): String = normalizeSeriesKey(effect.key.key)

private fun resolvePotion(key: String): PotionEffectType? =
    runCatching { PotionEffectType.getByKey(NamespacedKey.minecraft(key)) }.getOrNull()
