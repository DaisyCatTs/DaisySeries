package cat.daisy.series.particle

import org.bukkit.Particle
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyParticlesTest {
    @Test
    fun `parses normalized particle names`() {
        assertEquals(Particle.TOTEM_OF_UNDYING, DaisyParticles.parse("totem of undying"))
        assertEquals(Particle.TOTEM_OF_UNDYING, DaisyParticles.parse("minecraft:totem_of_undying"))
    }

    @Test
    fun `parses curated aliases`() {
        assertEquals(Particle.TOTEM_OF_UNDYING, DaisyParticles.parse("totem"))
    }

    @Test
    fun `parse or null returns null for unknown particles`() {
        assertNull(DaisyParticles.parseOrNull("unknown_particle"))
    }

    @Test
    fun `invalid parses include particle suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyParticles.parse("totem_of_undyng")
        }
        assertTrue(exception.message!!.contains("totem_of_undying"))
    }

    @Test
    fun `keys and display names are stable`() {
        assertEquals("totem_of_undying", DaisyParticles.key(Particle.TOTEM_OF_UNDYING))
        assertEquals("Totem Of Undying", DaisyParticles.displayName(Particle.TOTEM_OF_UNDYING))
    }
}
