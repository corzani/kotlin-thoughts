import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun simple(): Flow<Int> = flow {
    println("Flow started")
    (1..3).forEach { i ->
        delay(100)
        emit(i)
    }
}

fun main() = runBlocking<Unit> {
    println("Calling simple function...")
    val flow = simple()
    println("Calling collect...")
    flow.collectIndexed { index, value ->
        println("$index - $value")
    }
//    flow.collect { value -> println(value) }
    println("Calling collect again...")
    flow.collectIndexed { index, value ->
        println("$index - $value")
    }

//    flow.collect { value -> println(value) }
}