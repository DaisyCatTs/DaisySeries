package cat.daisy.series.attribute

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyAttributesTest {
    @Test
    fun `unknown attributes return null when no live registry is available`() {
        assertNull(DaisyAttributes.parseOrNull("unknown_attribute"))
    }

    @Test
    fun `normalized and prefixed attribute inputs fail soft without bootstrap`() {
        assertNull(DaisyAttributes.parseOrNull("attack damage"))
        assertNull(DaisyAttributes.parseOrNull("attack_damage"))
        assertNull(DaisyAttributes.parseOrNull("generic_attack_damage"))
        assertNull(DaisyAttributes.parseOrNull("minecraft:generic.attack_damage"))
    }

    @Test
    fun `invalid attributes still include canonical suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyAttributes.parse("attak_damage")
        }
        assertTrue(exception.message!!.contains("attack_damage"))
    }
}
