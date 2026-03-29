package cat.daisy.series.difficulty

import org.bukkit.Difficulty
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyDifficultiesTest {
    @Test
    fun `parses normalized difficulties`() {
        assertEquals(Difficulty.PEACEFUL, DaisyDifficulties.parse("peaceful"))
        assertEquals(Difficulty.NORMAL, DaisyDifficulties.parse("normal"))
        assertEquals(Difficulty.HARD, DaisyDifficulties.parse("minecraft:hard"))
    }

    @Test
    fun `parse or null returns null for unknown difficulties`() {
        assertNull(DaisyDifficulties.parseOrNull("brutal"))
    }

    @Test
    fun `invalid parses include difficulty suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyDifficulties.parse("peacful")
        }
        assertTrue(exception.message!!.contains("peaceful"))
    }

    @Test
    fun `keys and display names are stable`() {
        assertEquals("hard", DaisyDifficulties.key(Difficulty.HARD))
        assertEquals("Hard", DaisyDifficulties.displayName(Difficulty.HARD))
        assertTrue(DaisyDifficulties.aliases(Difficulty.HARD).isEmpty())
    }
}
