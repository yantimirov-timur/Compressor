package huffman

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class HuffmanTest {
    private val testHuffman = Huffman()

    @Test
    fun huffmanEncode() {
        val testString = "01011101101011000101110"

        assertEquals(testString, testHuffman.huffmanEncode(Parser().input))
    }

    @Test
    fun huffmanDecode() {
        val encoded = testHuffman.huffmanEncode(Parser().input)
        val tree = testHuffman.huffmanTreeTraversal(testHuffman.codeTreeNodes)

        val decode  = testHuffman.huffmanDecode(encoded,tree)

        assertEquals("abracadabra",decode)
    }
}