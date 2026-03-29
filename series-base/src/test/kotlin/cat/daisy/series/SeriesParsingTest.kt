package cat.daisy.series

import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.normalizeSeriesInput
import cat.daisy.series.internal.normalizeSeriesKey
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SeriesParsingTest {
    @Test
    fun `normalization handles spaces hyphens uppercase and namespaces`() {
        assertEquals("diamond_sword", normalizeSeriesInput(" Minecraft:Diamond-Sword "))
    }

    @Test
    fun `series keys normalize mixed separators`() {
        assertEquals("fire_resistance", normalizeSeriesKey(" Fire-Resistance "))
    }

    @Test
    fun `display names are generated from canonical keys`() {
        assertEquals("Entity Player Levelup", displayNameFromKey("entity_player_levelup"))
    }

    @Test
    fun `failures include deterministic suggestions`() {
        val exception = buildUnknownFailure("material", "diamond sord", listOf("diamond_sword", "diamond_ore", "gold_ingot"))
        assertTrue(exception.failure.suggestions.contains("diamond_sword"))
        assertTrue(exception.message!!.contains("Unknown material 'diamond sord'"))
    }
}
