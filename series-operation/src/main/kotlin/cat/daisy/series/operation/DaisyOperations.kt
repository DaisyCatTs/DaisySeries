package cat.daisy.series.operation

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import org.bukkit.attribute.AttributeModifier

object DaisyOperations : DaisySeriesParser<AttributeModifier.Operation> {
    private val canonicalKeys = AttributeModifier.Operation.values().associateBy { key(it) }

    private val lookup =
        buildMap {
            canonicalKeys.forEach { (operationKey, operation) ->
                put(operationKey, operation)
            }
        }

    override fun parse(input: String): AttributeModifier.Operation =
        parseOrNull(input) ?: throw buildUnknownFailure("operation", input, lookup.keys)

    override fun parseOrNull(input: String): AttributeModifier.Operation? = lookup[normalizeSeriesInput(input)]

    fun key(operation: AttributeModifier.Operation): String = enumKey(operation.name)

    fun displayName(operation: AttributeModifier.Operation): String = displayNameFromKey(key(operation))

    fun aliases(operation: AttributeModifier.Operation): Set<String> = emptySet()
}
