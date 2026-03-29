package cat.daisy.series.villagerprofession

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import cat.daisy.series.internal.normalizeSeriesKey
import org.bukkit.entity.Villager
import java.lang.reflect.Modifier

object DaisyVillagerProfessions : DaisySeriesParser<Villager.Profession> {
    private val curatedAliases =
        mapOf(
            "toolsmith" to setOf("tool_smith"),
            "weaponsmith" to setOf("weapon_smith"),
            "leatherworker" to setOf("leather_worker"),
        )

    private val canonicalKeys by lazy {
        Villager.Profession::class.java.declaredFields
            .filter { field ->
                field.type == Villager.Profession::class.java &&
                    Modifier.isStatic(field.modifiers)
            }
            .map { enumKey(it.name) }
            .toList()
    }

    private val suggestionKeys by lazy { (canonicalKeys + curatedAliases.values.flatten()).distinct() }

    private val lookup by lazy {
        buildMap {
            canonicalKeys.forEach { professionKey ->
                resolveProfession(professionKey)?.let { profession ->
                    put(professionKey, profession)
                    curatedAliases[professionKey].orEmpty().forEach { alias ->
                        put(normalizeSeriesKey(alias), profession)
                    }
                }
            }
        }
    }

    override fun parse(input: String): Villager.Profession =
        parseOrNull(input) ?: throw buildUnknownFailure("villager profession", input, suggestionKeys)

    override fun parseOrNull(input: String): Villager.Profession? = lookup[normalizeSeriesInput(input)]

    fun key(profession: Villager.Profession): String = enumKey(profession.name())

    fun displayName(profession: Villager.Profession): String = displayNameFromKey(key(profession))

    fun aliases(profession: Villager.Profession): Set<String> = curatedAliases[key(profession)].orEmpty().toSortedSet()
}

private fun resolveProfession(key: String): Villager.Profession? =
    runCatching { Villager.Profession.valueOf(key.uppercase()) }.getOrNull()
