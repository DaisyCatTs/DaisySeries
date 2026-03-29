package cat.daisy.series

class DaisySeriesParseException(
    val failure: DaisyParseFailure,
) : IllegalArgumentException(failure.reason)

