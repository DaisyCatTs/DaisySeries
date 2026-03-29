package cat.daisy.series.damagecause

import org.bukkit.event.entity.EntityDamageEvent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyDamageCausesTest {
    @Test
    fun `parses normalized damage causes`() {
        assertEquals(EntityDamageEvent.DamageCause.FIRE_TICK, DaisyDamageCauses.parse("fire_tick"))
        assertEquals(EntityDamageEvent.DamageCause.FIRE_TICK, DaisyDamageCauses.parse("fire tick"))
        assertEquals(EntityDamageEvent.DamageCause.ENTITY_ATTACK, DaisyDamageCauses.parse("entity-attack"))
        assertEquals(EntityDamageEvent.DamageCause.ENTITY_ATTACK, DaisyDamageCauses.parse("minecraft:entity_attack"))
    }

    @Test
    fun `parse or null returns null for unknown damage causes`() {
        assertNull(DaisyDamageCauses.parseOrNull("fir_tick"))
    }

    @Test
    fun `invalid parses include damage cause suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyDamageCauses.parse("entty_attack")
        }
        assertTrue(exception.message!!.contains("entity_attack"))
    }

    @Test
    fun `keys and display names are stable`() {
        assertEquals("fire_tick", DaisyDamageCauses.key(EntityDamageEvent.DamageCause.FIRE_TICK))
        assertEquals("Fire Tick", DaisyDamageCauses.displayName(EntityDamageEvent.DamageCause.FIRE_TICK))
        assertTrue(DaisyDamageCauses.aliases(EntityDamageEvent.DamageCause.FIRE_TICK).isEmpty())
    }
}
