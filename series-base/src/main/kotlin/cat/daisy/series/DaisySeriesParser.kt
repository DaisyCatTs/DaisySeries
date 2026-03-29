package cat.daisy.series

/**
 * Shared strict/nullable parsing contract for DaisySeries module entrypoints.
 */
interface DaisySeriesParser<T> {
    fun parse(input: String): T

    fun parseOrNull(input: String): T?
}

