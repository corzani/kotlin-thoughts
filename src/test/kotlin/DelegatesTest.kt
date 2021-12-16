@file:Suppress("RemoveEmptyPrimaryConstructor")

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.properties.Delegates

class DelegatesTest {

    @Test
    fun `vetoable variable`() {
        var vetoableValue: String by Delegates.vetoable("Pink") {
            // Will only update if the proposed value starts with an R
                _, _, new ->
            new.startsWith("R")
        }

        // a is lazy, the lambda would be executed only when that variable is read
        @SuppressWarnings("unused")
        val a by lazy {
            // Long operation that has never been executed if not needed
            54
        }

        vetoableValue = "Yellow"
        assertEquals("Pink", vetoableValue)
        vetoableValue = "Red"
        assertEquals("Red", vetoableValue)
    }
}