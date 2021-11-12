import kotlin.properties.Delegates

fun main() {
    var vetoableValue: String by Delegates.vetoable("Real Kotlin") {
        // Will only update if the proposed value starts with an R
            _, _, new -> new.startsWith("R")
    }

    vetoableValue = "DShjsd"
    println(vetoableValue)
    vetoableValue = "R DShjsd"
    println(vetoableValue)
}