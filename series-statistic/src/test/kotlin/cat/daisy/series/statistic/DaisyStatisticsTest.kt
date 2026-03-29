package cat.daisy.series.statistic

import org.bukkit.Statistic
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyStatisticsTest {
    @Test
    fun `parses normalized statistic names`() {
        assertEquals(Statistic.PLAYER_KILLS, DaisyStatistics.parse("player kills"))
        assertEquals(Statistic.PLAYER_KILLS, DaisyStatistics.parse("minecraft:player_kills"))
    }

    @Test
    fun `parse or null returns null for unknown statistics`() {
        assertNull(DaisyStatistics.parseOrNull("unknown_statistic"))
    }

    @Test
    fun `invalid parses include statistic suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyStatistics.parse("player kils")
        }
        assertTrue(exception.message!!.contains("player_kills"))
    }

    @Test
    fun `keys and display names are stable`() {
        assertEquals("player_kills", DaisyStatistics.key(Statistic.PLAYER_KILLS))
        assertEquals("Player Kills", DaisyStatistics.displayName(Statistic.PLAYER_KILLS))
    }
}
