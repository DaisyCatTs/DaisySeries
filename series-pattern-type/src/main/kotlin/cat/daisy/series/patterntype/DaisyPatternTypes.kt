package cat.daisy.series.patterntype

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import org.bukkit.block.banner.PatternType
import java.lang.reflect.Modifier

object DaisyPatternTypes : DaisySeriesParser<PatternType> {
    private val canonicalKeys by lazy {
        PatternType::class.java.declaredFields
            .filter { field ->
                field.type == PatternType::class.java &&
                    Modifier.isStatic(field.modifiers)
            }
            .map { enumKey(it.name) }
            .toList()
    }

    private val lookup by lazy {
        buildMap {
            canonicalKeys.forEach { patternKey ->
                resolvePatternType(patternKey)?.let { patternType ->
                    put(patternKey, patternType)
                }
            }
        }
    }

    override fun parse(input: String): PatternType =
        parseOrNull(input) ?: throw buildUnknownFailure("pattern type", input, canonicalKeys)

    override fun parseOrNull(input: String): PatternType? = lookup[normalizeSeriesInput(input)]

    fun key(patternType: PatternType): String = enumKey(patternType.key.key)

    fun displayName(patternType: PatternType): String = displayNameFromKey(key(patternType))

    fun aliases(patternType: PatternType): Set<String> = emptySet()
}

private fun resolvePatternType(key: String): PatternType? =
    runCatching { PatternType.valueOf(key.uppercase()) }.getOrNull()
