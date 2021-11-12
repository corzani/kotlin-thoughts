import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class DslTest {
    private fun doTest(expected: String, sql: QueryBuilder.() -> Unit) =
        Assertions.assertEquals(expected, query(sql).build())

    @Test
    fun `when no columns are specified then star is used`() =
        doTest("select * from table1") {
            from("table1")
        }

    @Test
    fun `Some little problems`() {
        assertFailsWith(IllegalStateException::class) {
            doTest("select * from table1") {
            }
        }
    }

    @Test
    fun `when no condition is specified then correct query is built`() =
        doTest("select column1, column2 from table1") {
            select("column1", "column2")
            from("table1")
        }
}