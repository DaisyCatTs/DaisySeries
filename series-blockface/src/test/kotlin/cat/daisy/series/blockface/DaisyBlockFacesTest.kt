package cat.daisy.series.blockface

import org.bukkit.block.BlockFace
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyBlockFacesTest {
    @Test
    fun `parses normalized block faces`() {
        assertEquals(BlockFace.NORTH, DaisyBlockFaces.parse("north"))
        assertEquals(BlockFace.NORTH_EAST, DaisyBlockFaces.parse("north-east"))
        assertEquals(BlockFace.NORTH_EAST, DaisyBlockFaces.parse("north east"))
        assertEquals(BlockFace.UP, DaisyBlockFaces.parse("minecraft:up"))
    }

    @Test
    fun `parse or null returns null for unknown block faces`() {
        assertNull(DaisyBlockFaces.parseOrNull("northwestish"))
    }

    @Test
    fun `invalid parses include block face suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyBlockFaces.parse("nort_east")
        }
        assertTrue(exception.message!!.contains("north_east"))
    }

    @Test
    fun `keys and display names are stable`() {
        assertEquals("north_east", DaisyBlockFaces.key(BlockFace.NORTH_EAST))
        assertEquals("North East", DaisyBlockFaces.displayName(BlockFace.NORTH_EAST))
        assertTrue(DaisyBlockFaces.aliases(BlockFace.NORTH_EAST).isEmpty())
    }
}
