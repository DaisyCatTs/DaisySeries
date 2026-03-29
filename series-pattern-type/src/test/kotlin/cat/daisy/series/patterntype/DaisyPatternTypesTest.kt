package cat.daisy.series.patterntype

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyPatternTypesTest {
    @Test
    fun `unknown pattern types return null when no live registry is available`() {
        assertNull(DaisyPatternTypes.parseOrNull("stripez"))
    }

    @Test
    fun `normalized pattern inputs fail soft without bootstrap`() {
        assertNull(DaisyPatternTypes.parseOrNull("small stripes"))
        assertNull(DaisyPatternTypes.parseOrNull("small_stripes"))
        assertNull(DaisyPatternTypes.parseOrNull("straight-cross"))
    }

    @Test
    fun `invalid pattern types still include canonical suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyPatternTypes.parse("small_stripz")
        }
        assertTrue(exception.message!!.contains("small_stripes"))
    }
}
