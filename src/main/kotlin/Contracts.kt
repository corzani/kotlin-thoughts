import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@ExperimentalContracts
class Service {

    fun process(request: String?) {
        validate(request)
        println(request) // Compiles fine now
    }
}

@ExperimentalContracts
private fun validate(request: String?) {
    contract {
        returns() implies (request != null)
    }
    if (request == null) {
        throw IllegalArgumentException("Undefined request")
    }
    if (request.isBlank()) {
        throw IllegalArgumentException("No argument is provided")
    }
}

@ExperimentalContracts
inline fun <R> myRun(block: () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block()
}

@ExperimentalContracts
fun isInterested(event: Any?): Boolean {
    contract {
        returns(true) implies (event is List<Any?>)
    }
    return event is List<Any?>
}