package deflate

import lz77.LZ77Node
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class DeflateTest {

    @Test
    fun encode() {
       val deflateTest = listOf(
               LZ77Node(0, 0, "01"),
               LZ77Node(0, 0, "101"),
               LZ77Node(0, 0, "100"),
               LZ77Node(3, 1, "00"),
               LZ77Node(0, 0, "00"),
               LZ77Node(5, 1, "11"),
               LZ77Node(0, 0, "11"),
               LZ77Node(7, 3, "01")
        )
        val deflate = Deflate()

        assertEquals(deflateTest,deflate.encode("abracadabra"))
    }
}