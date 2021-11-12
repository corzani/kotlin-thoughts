import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource

class SampleTest {

    @Test
    fun whenAdding1and3_thenAnswerIs4() {
        Assertions.assertEquals(4, 1 + 3)
    }

    @TestFactory
    fun testSquares() = listOf(
        1 to 1,
        2 to 4,
        3 to 9,
        4 to 16,
        5 to 25
    )
        .map { (input, expected) ->
            DynamicTest.dynamicTest("when I calculate $input^2 then I get $expected") {
                Assertions.assertEquals(expected, input * input)
            }
        }

    @ParameterizedTest
    @MethodSource("squares")
    fun testSquares(input: Int, expected: Int) {
        Assertions.assertEquals(expected, input * input)
    }

    companion object {
        @JvmStatic
        fun squares() = listOf(
            Arguments.of(1, 1),
            Arguments.of(2, 4)
        )
    }

    @ParameterizedTest
    @CsvSource(
        "1, 1",
        "2, 4",
        "3, 9"
    )
    fun testSquaresCsv(input: Int, expected: Int) {
        Assertions.assertEquals(expected, input * input)
    }

    // Tests with Tags can be handled separately. Ex. Ignoring or executing them

    @Tags(
        Tag("slow"),
        Tag("logarithms")
    )
    @Test
    fun `Log to base 2 of 8 should be equal to 3`() {
        Assertions.assertEquals(3.0, 3.0)
    }
}