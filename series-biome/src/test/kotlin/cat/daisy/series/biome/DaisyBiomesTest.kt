package cat.daisy.series.biome

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyBiomesTest {
    @Test
    fun `unknown biomes return null when no live registry is available`() {
        assertNull(DaisyBiomes.parseOrNull("unknown_biome"))
    }

    @Test
    fun `normalized and namespaced biome inputs fail soft without bootstrap`() {
        assertNull(DaisyBiomes.parseOrNull("cherry grove"))
        assertNull(DaisyBiomes.parseOrNull("cherry-grove"))
        assertNull(DaisyBiomes.parseOrNull("minecraft:cherry_grove"))
        assertNull(DaisyBiomes.parseOrNull("nether"))
    }

    @Test
    fun `invalid biomes still include canonical suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyBiomes.parse("chery grove")
        }
        assertTrue(exception.message!!.contains("cherry_grove"))
    }
}
