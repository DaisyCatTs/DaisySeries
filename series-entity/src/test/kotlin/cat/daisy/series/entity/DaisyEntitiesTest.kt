package cat.daisy.series.entity

import org.bukkit.entity.EntityType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyEntitiesTest {
    @Test
    fun `parses normalized entity names`() {
        assertEquals(EntityType.ZOMBIE_VILLAGER, DaisyEntities.parse("zombie villager"))
        assertEquals(EntityType.ZOMBIE_VILLAGER, DaisyEntities.parse("zombie-villager"))
        assertEquals(EntityType.ZOMBIE_VILLAGER, DaisyEntities.parse("minecraft:zombie_villager"))
    }

    @Test
    fun `parses curated aliases`() {
        assertEquals(EntityType.MOOSHROOM, DaisyEntities.parse("mushroom_cow"))
    }

    @Test
    fun `parse or null returns null for unknown entity values`() {
        assertNull(DaisyEntities.parseOrNull("unknown_entity"))
    }

    @Test
    fun `invalid parses include entity suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyEntities.parse("zombie villagr")
        }
        assertTrue(exception.message!!.contains("zombie_villager"))
    }

    @Test
    fun `keys and display names are stable`() {
        assertEquals("zombie_villager", DaisyEntities.key(EntityType.ZOMBIE_VILLAGER))
        assertEquals("Zombie Villager", DaisyEntities.displayName(EntityType.ZOMBIE_VILLAGER))
    }
}
