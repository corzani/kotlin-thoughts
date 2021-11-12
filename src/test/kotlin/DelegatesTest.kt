import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.properties.Delegates

class DelegatesTest (){

    @Test
    fun `vetoable variable`(){
        var vetoableValue: String by Delegates.vetoable("Pink") {
            // Will only update if the proposed value starts with an R
                _, _, new -> new.startsWith("R")
        }

        vetoableValue = "Yellow"
        assertEquals("Pink", vetoableValue)
        vetoableValue = "Red"
        assertEquals("Red", vetoableValue)
    }
}