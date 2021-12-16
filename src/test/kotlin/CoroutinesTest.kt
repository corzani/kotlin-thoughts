import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
/*

From Sonar Rules
The suspend modifier is generally used for functions that might take some time to complete. The caller coroutine might be potentially suspended.

Functions that return results immediately but start a coroutine in the background should be written as extension functions on CoroutineScope.
At the same time, these functions should not be declared suspend, as suspending functions should not leave running background tasks behind.

Noncompliant Code Example

suspend fun CoroutineScope.f(): Int {
    val resource1 = loadResource1()
    val resource2 = loadResource2()
    return resource1.size + resource2.size
}

Compliant Solution

Using suspend:

suspend fun f(): Int {
    val resource1 = loadResource1()
    val resource2 = loadResource2()
    return resource1.size + resource2.size
}

Using extension on CoroutineScope:

fun CoroutineScope.f(): Deferred<Int> = async {
    val resource1 = loadResource1()
    val resource2 = loadResource2()
    resource1.size + resource2.size
}

*
* */
class CoroutinesTest {

    @Test
    fun `Coroutine not calls async funcs when they are not needed`() = runBlockingTest {

        // launch is typically a start and forget coroutine that doesn't return any value
        launch {
            println("I am the worst")
        }

        launch {
            print("123")
        }

        // This one is executed but not used... and that is conceptually wrong...
        @Suppress("unused")
        async {
            print("532")
            532
        }

        // In case we would love to achieve lazy async function we may not want to
        // deal with delegates like lazy but using the case below with CoroutineStart.LAZY param
        @SuppressWarnings("unused")
        val a by lazy {
            async {
                println("Silly Lazyness")
                34
            }
        }

        // Lazy async
        @SuppressWarnings("unused")
        val b = async(start = CoroutineStart.LAZY) {
            println("never executed")
            23
        }
        println("Do you fancy a cup of tea?")

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

        // I know... it's too much
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


        /*
        runBlocking is a low-level construct, to be used only in framework code or self-contained examples like yours.
        It turns an existing thread into an event loop and creates its coroutine with a Dispatcher that posts resuming
        coroutines to the event loop's queue.
        coroutineScope is a user-facing construct, used to delineate the boundaries of a task that is being
        parallel-decomposed inside it. You use it to conveniently await on all the async work happening inside it,
        get the final result, and handle all failures at one central place.
        */

        // Write some tests regarding
        // join
        // cancellation
        // continuation object

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