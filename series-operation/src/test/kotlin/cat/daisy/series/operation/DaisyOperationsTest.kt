package cat.daisy.series.operation

import org.bukkit.attribute.AttributeModifier
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DaisyOperationsTest {
    @Test
    fun `parses normalized operations`() {
        assertEquals(AttributeModifier.Operation.ADD_NUMBER, DaisyOperations.parse("add_number"))
        assertEquals(AttributeModifier.Operation.ADD_NUMBER, DaisyOperations.parse("add number"))
        assertEquals(AttributeModifier.Operation.MULTIPLY_SCALAR_1, DaisyOperations.parse("multiply-scalar-1"))
        assertEquals(AttributeModifier.Operation.MULTIPLY_SCALAR_1, DaisyOperations.parse("minecraft:multiply_scalar_1"))
    }

    @Test
    fun `parse or null returns null for unknown operations`() {
        assertNull(DaisyOperations.parseOrNull("multiply_scalarish"))
    }

    @Test
    fun `invalid parses include operation suggestions`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DaisyOperations.parse("add_numbr")
        }
        assertTrue(exception.message!!.contains("add_number"))
    }

    @Test
    fun `keys and display names are stable`() {
        assertEquals("multiply_scalar_1", DaisyOperations.key(AttributeModifier.Operation.MULTIPLY_SCALAR_1))
        assertEquals("Multiply Scalar 1", DaisyOperations.displayName(AttributeModifier.Operation.MULTIPLY_SCALAR_1))
        assertTrue(DaisyOperations.aliases(AttributeModifier.Operation.MULTIPLY_SCALAR_1).isEmpty())
    }
}
