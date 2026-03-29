package cat.daisy.series.gamemode

import org.bukkit.GameMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyGameModesTest {
    @Test
    fun `parses normalized game mode names`() {
        assertEquals(GameMode.ADVENTURE, DaisyGameModes.parse("adventure"))
        assertEquals(GameMode.ADVENTURE, DaisyGameModes.parse("minecraft:adventure"))
    }

    @Test
    fun `parses curated aliases`() {
        assertEquals(GameMode.SURVIVAL, DaisyGameModes.parse("surv"))
        assertEquals(GameMode.SPECTATOR, DaisyGameModes.parse("spec"))
    }

    @Test
    fun `parse or null returns null for unknown game modes`() {
        assertNull(DaisyGameModes.parseOrNull("builder"))
    }

    @Test
    fun `invalid parses include game mode suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyGameModes.parse("survvival")
        }
        assertTrue(exception.message!!.contains("survival"))
    }

    @Test
    fun `keys and display names are stable`() {
        assertEquals("spectator", DaisyGameModes.key(GameMode.SPECTATOR))
        assertEquals("Spectator", DaisyGameModes.displayName(GameMode.SPECTATOR))
    }
}
