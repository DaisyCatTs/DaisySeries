package cat.daisy.series

data class DaisyParseFailure(
    val input: String,
    val type: String,
    val reason: String,
    val suggestions: List<String> = emptyList(),
)

