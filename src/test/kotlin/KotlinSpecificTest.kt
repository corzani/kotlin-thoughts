import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KotlinSpecificTest {

    @Test
    fun `Inline if`() {
        val result = if (true) {
            1
        } else {
            2
        }

        assertEquals(1, result)
    }
}