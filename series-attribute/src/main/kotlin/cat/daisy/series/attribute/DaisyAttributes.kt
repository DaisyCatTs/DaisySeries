package cat.daisy.series.attribute

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesKey
import org.bukkit.attribute.Attribute
import java.lang.reflect.Modifier

object DaisyAttributes : DaisySeriesParser<Attribute> {
    private val canonicalKeys by lazy {
        Attribute::class.java.declaredFields
            .filter { field ->
                field.type == Attribute::class.java &&
                    Modifier.isStatic(field.modifiers)
            }
            .map { canonicalKeyFromField(it.name) }
            .toList()
    }

    private val suggestionKeys by lazy {
        buildSet {
            addAll(canonicalKeys)
            canonicalKeys.forEach { canonical ->
                add(prefixedAttributeKey("generic", canonical))
                add(prefixedAttributeKey("player", canonical))
                add(prefixedAttributeKey("zombie", canonical))
            }
        }.toList()
    }

    private val lookup by lazy {
        buildMap {
            canonicalKeys.forEach { attributeKey ->
                resolveAttribute(attributeKey)?.let { attribute ->
                    put(attributeKey, attribute)
                    aliases(attribute).forEach { alias ->
                        put(normalizeAttributeInput(alias), attribute)
                    }
                }
            }
        }
    }

    override fun parse(input: String): Attribute =
        parseOrNull(input) ?: throw buildUnknownFailure("attribute", input, suggestionKeys)

    override fun parseOrNull(input: String): Attribute? = lookup[normalizeAttributeInput(input)]

    fun key(attribute: Attribute): String = canonicalKeyFromField(attribute.name())

    fun displayName(attribute: Attribute): String = displayNameFromKey(key(attribute))

    fun aliases(attribute: Attribute): Set<String> =
        buildSet {
            val canonical = key(attribute)
            val raw = enumKey(attribute.name())
            if (raw != canonical) {
                add(raw)
            }
            add(prefixedAttributeKey("generic", canonical))
            add(prefixedAttributeKey("player", canonical))
            add(prefixedAttributeKey("zombie", canonical))
        }.filterNot { it == key(attribute) }.toSortedSet()
}

private fun canonicalKeyFromField(name: String): String =
    enumKey(name)
        .removePrefix("generic_")
        .removePrefix("player_")
        .removePrefix("zombie_")

private fun prefixedAttributeKey(prefix: String, canonical: String): String = "${prefix}_$canonical"

private fun normalizeAttributeInput(input: String): String =
    normalizeSeriesKey(input.trim().substringAfter(':', input.trim()).replace('.', '_'))
        .removePrefix("generic_")
        .removePrefix("player_")
        .removePrefix("zombie_")

private fun resolveAttribute(canonicalKey: String): Attribute? =
    sequenceOf(
        "GENERIC_${canonicalKey.uppercase()}",
        "PLAYER_${canonicalKey.uppercase()}",
        "ZOMBIE_${canonicalKey.uppercase()}",
    ).mapNotNull { rawName ->
        runCatching { Attribute.valueOf(rawName) }.getOrNull()
    }.firstOrNull()
