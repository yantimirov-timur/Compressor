package deflate.huffman

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class HuffmanTest {
    private val testHuffman = Huffman()

    @Test
    fun huffmanEncode() {
        val encodedTestString = "01011101101011000101110"

        assertEquals(encodedTestString, testHuffman.huffmanEncode(Parser().input))
    }

    @Test
    fun huffmanDecode() {
        val encoded = testHuffman.huffmanEncode(Parser().input)
        val tree = testHuffman.huffmanTreeTraversal(testHuffman.codeTreeNodes)
        val decode = testHuffman.huffmanDecode(encoded, tree)

        assertEquals("abracadabra", decode)
    }
}