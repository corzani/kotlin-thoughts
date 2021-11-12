import org.junit.jupiter.api.Test

class FuncTest {

    @Test
    fun `Functions to something`() {
        val multiply: (Int, Int) -> Int = { x, y -> x * y }
        multiply(4, 5)
        multiply.invoke(4, 5)

        val f: String.(Int, Int) -> Int = { x, y ->
            this.toInt() * x * y
        }
        f.invoke("4", 5, 8)
        f("4", 5, 9)
        "4".f(5, 3)
    }
}