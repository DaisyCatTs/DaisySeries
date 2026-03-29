package cat.daisy.series.gamemode

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import org.bukkit.GameMode

object DaisyGameModes : DaisySeriesParser<GameMode> {
    private val curatedAliases =
        mapOf(
            "surv" to GameMode.SURVIVAL,
            "spec" to GameMode.SPECTATOR,
        )

    private val canonicalKeys = GameMode.values().associateBy { key(it) }

    private val lookup =
        buildMap {
            canonicalKeys.forEach { (gameModeKey, gameMode) ->
                put(gameModeKey, gameMode)
            }
            curatedAliases.forEach { (alias, gameMode) ->
                put(alias, gameMode)
            }
        }

    override fun parse(input: String): GameMode =
        parseOrNull(input) ?: throw buildUnknownFailure("game mode", input, lookup.keys)

    override fun parseOrNull(input: String): GameMode? = lookup[normalizeSeriesInput(input)]

    fun key(gameMode: GameMode): String = enumKey(gameMode.name)

    fun displayName(gameMode: GameMode): String = displayNameFromKey(key(gameMode))

    fun aliases(gameMode: GameMode): Set<String> =
        curatedAliases
            .filterValues { it == gameMode }
            .keys
            .toSortedSet()
}
