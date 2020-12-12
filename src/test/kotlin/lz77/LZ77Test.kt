package lz77

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LZ77Test {

    @Test
    fun encode() {
        val lZ77EncodedTest = listOf(
                LZ77Node(0, 0, "a"),
                LZ77Node(0, 0, "b"),
                LZ77Node(0, 0, "r"),
                LZ77Node(3, 1, "c"),
                LZ77Node(0, 0, "c"),
                LZ77Node(5, 1, "d"),
                LZ77Node(0, 0, "d"),
                LZ77Node(7, 3, "a")
        )
        val lZ77= LZ77()

        assertEquals(lZ77EncodedTest,lZ77.encode("abracadabra"))


    }
}