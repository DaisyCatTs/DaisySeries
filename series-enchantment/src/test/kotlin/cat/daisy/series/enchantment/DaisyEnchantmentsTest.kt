package cat.daisy.series.enchantment

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyEnchantmentsTest {
    @Test
    fun `unknown enchantments return null when no live registry is available`() {
        assertNull(DaisyEnchantments.parseOrNull("unknown_enchantment"))
    }

    @Test
    fun `normalized and namespaced enchantment inputs fail soft without bootstrap`() {
        assertNull(DaisyEnchantments.parseOrNull("sharpness"))
        assertNull(DaisyEnchantments.parseOrNull("minecraft:sharpness"))
        assertNull(DaisyEnchantments.parseOrNull("fire aspect"))
        assertNull(DaisyEnchantments.parseOrNull("prot"))
    }

    @Test
    fun `invalid enchantments still include canonical suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyEnchantments.parse("sharpnes")
        }
        assertTrue(exception.message!!.contains("sharpness"))
    }
}
