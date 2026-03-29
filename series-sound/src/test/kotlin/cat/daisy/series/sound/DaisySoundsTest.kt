package cat.daisy.series.sound

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisySoundsTest {
    @Test
    fun `unknown sounds return null without bootstrapping paper`() {
        assertNull(DaisySounds.parseOrNull("missing_sound"))
    }

    @Test
    fun `invalid sounds include suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisySounds.parse("entity player level")
        }
        assertTrue(exception.message!!.contains("entity_player_levelup"))
    }

}
