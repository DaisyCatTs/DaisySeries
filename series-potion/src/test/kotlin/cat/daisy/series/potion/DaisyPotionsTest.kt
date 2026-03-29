package cat.daisy.series.potion

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyPotionsTest {
    @Test
    fun `unknown potion effects return null when no live registry is available`() {
        assertNull(DaisyPotions.parseOrNull("missing_effect"))
    }

    @Test
    fun `normalized and namespaced potion inputs fail soft without bootstrap`() {
        assertNull(DaisyPotions.parseOrNull("speed"))
        assertNull(DaisyPotions.parseOrNull("minecraft:speed"))
        assertNull(DaisyPotions.parseOrNull("slow falling"))
        assertNull(DaisyPotions.parseOrNull("fire_res"))
    }

    @Test
    fun `invalid potion effects still include canonical suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyPotions.parse("slow fallng")
        }
        assertTrue(exception.message!!.contains("slow_falling"))
    }
}
