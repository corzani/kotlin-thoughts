import org.junit.jupiter.api.BeforeAll

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle

/*
* By default, both JUnit 4 and 5 create a new instance of the test class before running each test method.
*
* In the case below largeContent would be recreated for every single test. To avoid that
* Life Cycle has to be PER_CLASS with an init method like the following one...
*
* */

@TestInstance(Lifecycle.PER_CLASS)
class LifeCycleTest {

    // Javish style
    private val largeContent: String? = null

    @BeforeAll
    fun setUpFixture() {
        // read the file
    }

}

