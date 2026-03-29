package cat.daisy.series.villagerprofession

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyVillagerProfessionsTest {
    @Test
    fun `unknown professions return null when no live registry is available`() {
        assertNull(DaisyVillagerProfessions.parseOrNull("blacksmith"))
    }

    @Test
    fun `normalized profession inputs fail soft without bootstrap`() {
        assertNull(DaisyVillagerProfessions.parseOrNull("toolsmith"))
        assertNull(DaisyVillagerProfessions.parseOrNull("tool-smith"))
        assertNull(DaisyVillagerProfessions.parseOrNull("tool smith"))
        assertNull(DaisyVillagerProfessions.parseOrNull("weapon smith"))
    }

    @Test
    fun `invalid professions still include canonical suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyVillagerProfessions.parse("toolsmth")
        }
        assertTrue(exception.message!!.contains("toolsmith"))
    }
}
