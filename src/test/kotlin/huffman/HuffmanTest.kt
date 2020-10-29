package huffman

import org.junit.jupiter.api.Assertions.*

internal class HuffmanTest {

    @org.junit.jupiter.api.Test
    fun huffmanEncode() {
        val testString = "01011101101011000101110"
        val testHuffman = Huffman()

        assertEquals(testString,testHuffman.huffmanEncode(Parser().input))
    }

    @org.junit.jupiter.api.Test
    fun huffmanDecode() {
    }
}