package cat.daisy.series.material

import org.bukkit.Material
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyMaterialsTest {
    @Test
    fun `parses normalized material names`() {
        assertEquals(Material.DIAMOND_SWORD, DaisyMaterials.parse("diamond sword"))
        assertEquals(Material.DIAMOND_SWORD, DaisyMaterials.parse("diamond-sword"))
        assertEquals(Material.DIAMOND_SWORD, DaisyMaterials.parse("DIAMOND_SWORD"))
    }

    @Test
    fun `parses curated aliases`() {
        assertEquals(Material.GOLDEN_APPLE, DaisyMaterials.parse("gapple"))
        assertEquals(Material.ENCHANTED_GOLDEN_APPLE, DaisyMaterials.parse("god_apple"))
    }

    @Test
    fun `parse or null returns null for unknown values`() {
        assertNull(DaisyMaterials.parseOrNull("not_a_real_material"))
    }

    @Test
    fun `invalid parses include suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyMaterials.parse("diamond sord")
        }
        assertTrue(exception.message!!.contains("diamond_sword"))
    }

    @Test
    fun `keys and display names are stable`() {
        assertEquals("diamond_sword", DaisyMaterials.key(Material.DIAMOND_SWORD))
        assertEquals("Diamond Sword", DaisyMaterials.displayName(Material.DIAMOND_SWORD))
    }
}
