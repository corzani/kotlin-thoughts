import org.junit.jupiter.api.Test
import kotlin.contracts.ExperimentalContracts
import kotlin.test.assertEquals

@ExperimentalContracts
class ContractsTest {

    @Test
    fun `Called once`() {

        // The following one works only because of the contract specified on myRun (CALLED ONCE)
        val something: String
        myRun {
            something = "123"
        }

        assertEquals("123", something)

        fun a(fn: () -> Unit) = fn()
        val somethingElse: String

        // The following code won't work because it has no contract so the compiler doesn't know if that variable can be
        // reassigned
        // Captured values initialization is forbidden due to possible reassignment

        //        a {
        //            somethingElse = ""
        //        }
    }
}