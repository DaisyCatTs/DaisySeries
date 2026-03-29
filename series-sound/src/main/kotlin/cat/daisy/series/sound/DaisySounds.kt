package cat.daisy.series.sound

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import org.bukkit.Sound
import java.lang.reflect.Modifier

object DaisySounds : DaisySeriesParser<Sound> {
    private val curatedAliases by lazy {
        mapOf(
            "levelup" to "ENTITY_PLAYER_LEVELUP",
            "xp_pickup" to "ENTITY_EXPERIENCE_ORB_PICKUP",
        )
    }

    private val canonicalNames by lazy {
        Sound::class.java.declaredFields
            .filter { field ->
                field.type == Sound::class.java &&
                    Modifier.isStatic(field.modifiers)
            }
            .map { it.name }
            .sorted()
    }

    private val canonicalKeys by lazy { canonicalNames.associateBy { enumKey(it) } }

    private val lookup by lazy {
        buildMap {
            canonicalKeys.forEach { (soundKey, soundName) ->
                put(soundKey, soundName)
            }
            curatedAliases.forEach { (alias, soundName) ->
                put(alias, soundName)
            }
        }
    }

    override fun parse(input: String): Sound =
        parseOrNull(input) ?: throw buildUnknownFailure("sound", input, lookup.keys)

    override fun parseOrNull(input: String): Sound? =
        lookup[normalizeSeriesInput(input)]?.let { soundName ->
            runCatching { Sound.valueOf(soundName) }.getOrNull()
        }

    fun key(sound: Sound): String = enumKey(sound.name())

    fun displayName(sound: Sound): String = displayNameFromKey(key(sound))

    fun aliases(sound: Sound): Set<String> =
        curatedAliases
            .filterValues { it == sound.name() }
            .keys
            .toSortedSet()
}
