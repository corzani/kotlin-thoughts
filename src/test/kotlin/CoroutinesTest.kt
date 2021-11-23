import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

class CoroutinesTest {

    @Test
    fun `Coroutine not calls async funcs when they are not needed`() = runBlockingTest {

        // launch is typically a start and forget coroutine that doesn't return any value
        launch {
            println("Helena is the worst")
        }

        launch {
            print("123")
        }

        // This one is executed but not used...
        async {
            print("532")
            532
        }

        @SuppressWarnings("unused")
        val a by lazy {
            async {
                println("Cicciput")
                34
            }
        }

        // Lazy async
        @SuppressWarnings("unused")
        val b = async(start = CoroutineStart.LAZY) {
            println("never executed")
            23
        }
        println("Put Cicci?")

        delay(3423)
    }

    @Test
    fun `Coroutine async still execute itself even when the result is not used`() = runBlockingTest {

        val c = mock<() -> Unit>()
        val d = mock<() -> Unit>()

        val invokedAsync = async {
            c()
        }

        val notInvokedAsync = async(start = CoroutineStart.LAZY) {
            d()
        }


        delay(10000000000000)
        verify(c).invoke()
        verifyNoInteractions(d)
    }

    @Test
    fun `Launch vs coroutineScope`() = runBlockingTest {

        /*
        * launch execute the code asynchronously and let the flow of the function continue
        * during its execution.
        *
        * coroutineScope is like having await at the end of a launch statement.
        * It executes its internal code asynchronously waiting to the end of computation
        * to give back the execution to the caller function
        * */

        val func = mock<(String) -> Unit>()


        // This function doesn't block the flow of the execution
        launch {
            delay(1000)
            func("launch")
        }

        // This function does stop the execution until everything is executed
        //
        coroutineScope {
            delay(500)
            func("coroutineScope")
        }

        // This part of the code is not executed until the end of the coroutineScope
        func("end")

        // This is cool... runBlockingTest does handle delays internally without a real wait
        delay(500000000)

        func.inOrder {
            // Can I do this with a single call instead of 3?
            verify()("coroutineScope")
            verify()("end")
            verify()("launch")
        }

    }
}