package cat.daisy.series.itemflag

import org.bukkit.inventory.ItemFlag
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class DaisyItemFlagsTest {
    @Test
    fun `parses normalized flag names`() {
        assertEquals(ItemFlag.HIDE_ENCHANTS, DaisyItemFlags.parse("hide enchants"))
        assertEquals(ItemFlag.HIDE_ENCHANTS, DaisyItemFlags.parse("hide-enchants"))
        assertEquals(ItemFlag.HIDE_ENCHANTS, DaisyItemFlags.parse("HIDE_ENCHANTS"))
    }

    @Test
    fun `parse many returns stable set`() {
        val result = DaisyItemFlags.parseMany(listOf("hide enchants", "hide attributes", "hide enchants"))
        assertEquals(listOf(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES), result.toList())
    }

    @Test
    fun `parse many or null returns null on failure`() {
        assertNull(DaisyItemFlags.parseManyOrNull(listOf("hide enchants", "missing")))
    }

    @Test
    fun `parse many throws on invalid input`() {
        assertFailsWith<IllegalArgumentException> {
            DaisyItemFlags.parseMany(listOf("hide enchants", "missing"))
        }
    }

    @Test
    fun `keys and display names are stable`() {
        assertEquals("hide_enchants", DaisyItemFlags.key(ItemFlag.HIDE_ENCHANTS))
        assertEquals("Hide Enchants", DaisyItemFlags.displayName(ItemFlag.HIDE_ENCHANTS))
    }
}
