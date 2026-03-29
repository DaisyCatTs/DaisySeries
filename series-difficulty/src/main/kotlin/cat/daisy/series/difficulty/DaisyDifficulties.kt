package cat.daisy.series.difficulty

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import org.bukkit.Difficulty

object DaisyDifficulties : DaisySeriesParser<Difficulty> {
    private val canonicalKeys = Difficulty.values().associateBy { key(it) }

    private val lookup =
        buildMap {
            canonicalKeys.forEach { (difficultyKey, difficulty) ->
                put(difficultyKey, difficulty)
            }
        }

    override fun parse(input: String): Difficulty =
        parseOrNull(input) ?: throw buildUnknownFailure("difficulty", input, lookup.keys)

    override fun parseOrNull(input: String): Difficulty? = lookup[normalizeSeriesInput(input)]

    fun key(difficulty: Difficulty): String = enumKey(difficulty.name)

    fun displayName(difficulty: Difficulty): String = displayNameFromKey(key(difficulty))

    fun aliases(difficulty: Difficulty): Set<String> = emptySet()
}
