package cat.daisy.series.particle

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import org.bukkit.Particle

object DaisyParticles : DaisySeriesParser<Particle> {
    private val curatedAliases =
        mapOf(
            "totem" to Particle.TOTEM_OF_UNDYING,
        )

    private val canonicalKeys = Particle.values().associateBy { key(it) }

    private val lookup =
        buildMap {
            canonicalKeys.forEach { (particleKey, particle) ->
                put(particleKey, particle)
            }
            curatedAliases.forEach { (alias, particle) ->
                put(alias, particle)
            }
        }

    override fun parse(input: String): Particle =
        parseOrNull(input) ?: throw buildUnknownFailure("particle", input, lookup.keys)

    override fun parseOrNull(input: String): Particle? = lookup[normalizeSeriesInput(input)]

    fun key(particle: Particle): String = enumKey(particle.name)

    fun displayName(particle: Particle): String = displayNameFromKey(key(particle))

    fun aliases(particle: Particle): Set<String> =
        curatedAliases
            .filterValues { it == particle }
            .keys
            .toSortedSet()
}
