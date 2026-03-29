package cat.daisy.series.statistic

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import org.bukkit.Statistic

object DaisyStatistics : DaisySeriesParser<Statistic> {
    private val canonicalKeys = Statistic.values().associateBy { key(it) }

    private val lookup =
        buildMap {
            canonicalKeys.forEach { (statisticKey, statistic) ->
                put(statisticKey, statistic)
            }
        }

    override fun parse(input: String): Statistic =
        parseOrNull(input) ?: throw buildUnknownFailure("statistic", input, lookup.keys)

    override fun parseOrNull(input: String): Statistic? = lookup[normalizeSeriesInput(input)]

    fun key(statistic: Statistic): String = enumKey(statistic.name)

    fun displayName(statistic: Statistic): String = displayNameFromKey(key(statistic))

    fun aliases(statistic: Statistic): Set<String> = emptySet()
}
