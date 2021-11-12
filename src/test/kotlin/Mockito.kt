import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

//vararg
class Mockito {

    @Test
    fun mocking() {
        val expected = "first"
        val mockedList = mock<LinkedList<String>>()

        whenever(mockedList[0]).thenReturn(expected,"AAAAAA!")
        Assertions.assertEquals(expected, mockedList[0])
        Assertions.assertEquals("AAAAAA!", mockedList[0])
        Assertions.assertEquals(null, mockedList[999])
    }
}