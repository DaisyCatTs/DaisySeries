package cat.daisy.series.internal

import cat.daisy.series.DaisyParseFailure
import cat.daisy.series.DaisySeriesParseException
import java.util.Locale

data class SeriesCandidate<T>(
    val key: String,
    val value: T,
    val aliases: Set<String> = emptySet(),
)

fun normalizeSeriesInput(input: String): String {
    val trimmed = input.trim()
    return normalizeSeriesKey(trimmed.substringAfter(':', trimmed))
}

fun enumKey(name: String): String = name.lowercase(Locale.ROOT)

fun normalizeSeriesKey(input: String): String =
    input
        .lowercase(Locale.ROOT)
        .replace(Regex("[\\s\\-]+"), "_")
        .replace(Regex("_+"), "_")
        .trim('_')

fun displayNameFromKey(key: String): String =
    key.split('_')
        .filter { it.isNotBlank() }
        .joinToString(" ") { token ->
            token.replaceFirstChar { char ->
                if (char.isLowerCase()) {
                    char.titlecase(Locale.ROOT)
                } else {
                    char.toString()
                }
            }
        }

fun suggestCandidates(
    normalizedInput: String,
    candidates: Collection<String>,
    limit: Int = 5,
): List<String> {
    if (normalizedInput.isBlank()) {
        return candidates.sorted().take(limit)
    }

    val distinctCandidates = candidates.distinct()

    val startsWith = distinctCandidates.filter { it.startsWith(normalizedInput) }
    val contains = distinctCandidates.filter { it !in startsWith && it.contains(normalizedInput) }
    val distance = distinctCandidates
        .filter { it !in startsWith && it !in contains }
        .map { it to levenshtein(normalizedInput, it) }
        .sortedWith(compareBy<Pair<String, Int>> { it.second }.thenBy { it.first })
        .filter { it.second <= 3 }
        .map { it.first }

    return buildList {
        addAll(startsWith.sorted())
        addAll(contains.sorted())
        addAll(distance)
    }.distinct().take(limit)
}

fun buildUnknownFailure(
    type: String,
    input: String,
    candidates: Collection<String>,
): DaisySeriesParseException {
    val normalized = normalizeSeriesInput(input)
    val suggestions = suggestCandidates(normalized, candidates)
    val reason =
        if (suggestions.isEmpty()) {
            "Unknown $type '$input'."
        } else {
            "Unknown $type '$input'. Try one of: ${suggestions.joinToString(", ")}."
        }

    return DaisySeriesParseException(
        DaisyParseFailure(
            input = input,
            type = type,
            reason = reason,
            suggestions = suggestions,
        ),
    )
}

fun <T> lookupCandidates(
    values: Iterable<SeriesCandidate<T>>,
): Map<String, T> =
    buildMap {
        values.forEach { candidate ->
            put(candidate.key, candidate.value)
            candidate.aliases.forEach { alias ->
                put(normalizeSeriesKey(alias), candidate.value)
            }
        }
    }

private fun levenshtein(left: String, right: String): Int {
    if (left == right) return 0
    if (left.isEmpty()) return right.length
    if (right.isEmpty()) return left.length

    var previous = IntArray(right.length + 1) { it }
    var current = IntArray(right.length + 1)

    for (i in left.indices) {
        current[0] = i + 1
        for (j in right.indices) {
            val cost = if (left[i] == right[j]) 0 else 1
            current[j + 1] = minOf(
                current[j] + 1,
                previous[j + 1] + 1,
                previous[j] + cost,
            )
        }
        val swap = previous
        previous = current
        current = swap
    }

    return previous[right.length]
}
