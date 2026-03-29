package cat.daisy.series.damagecause

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import org.bukkit.event.entity.EntityDamageEvent

object DaisyDamageCauses : DaisySeriesParser<EntityDamageEvent.DamageCause> {
    private val canonicalKeys = EntityDamageEvent.DamageCause.values().associateBy { key(it) }

    private val lookup =
        buildMap {
            canonicalKeys.forEach { (damageCauseKey, damageCause) ->
                put(damageCauseKey, damageCause)
            }
        }

    override fun parse(input: String): EntityDamageEvent.DamageCause =
        parseOrNull(input) ?: throw buildUnknownFailure("damage cause", input, lookup.keys)

    override fun parseOrNull(input: String): EntityDamageEvent.DamageCause? = lookup[normalizeSeriesInput(input)]

    fun key(damageCause: EntityDamageEvent.DamageCause): String = enumKey(damageCause.name)

    fun displayName(damageCause: EntityDamageEvent.DamageCause): String = displayNameFromKey(key(damageCause))

    fun aliases(damageCause: EntityDamageEvent.DamageCause): Set<String> = emptySet()
}
