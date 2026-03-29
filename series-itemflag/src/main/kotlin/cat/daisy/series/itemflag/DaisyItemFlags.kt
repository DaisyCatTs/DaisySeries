package cat.daisy.series.itemflag

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import org.bukkit.inventory.ItemFlag
import java.util.LinkedHashSet

object DaisyItemFlags : DaisySeriesParser<ItemFlag> {
    private val canonicalKeys = ItemFlag.values().associateBy { key(it) }

    private val lookup =
        buildMap {
            canonicalKeys.forEach { (flagKey, flag) ->
                put(flagKey, flag)
            }
        }

    override fun parse(input: String): ItemFlag =
        parseOrNull(input) ?: throw buildUnknownFailure("item flag", input, lookup.keys)

    override fun parseOrNull(input: String): ItemFlag? = lookup[normalizeSeriesInput(input)]

    fun key(flag: ItemFlag): String = enumKey(flag.name)

    fun displayName(flag: ItemFlag): String = displayNameFromKey(key(flag))

    fun aliases(flag: ItemFlag): Set<String> = emptySet()

    fun parseMany(inputs: Iterable<String>): Set<ItemFlag> =
        LinkedHashSet<ItemFlag>().apply {
            inputs.forEach { add(parse(it)) }
        }

    fun parseManyOrNull(inputs: Iterable<String>): Set<ItemFlag>? =
        runCatching { parseMany(inputs) }.getOrNull()
}
