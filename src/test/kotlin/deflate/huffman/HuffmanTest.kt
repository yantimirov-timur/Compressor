package deflate.huffman

import huffman.Huffman
import huffman.Parser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class HuffmanTest {
    private val testHuffman = Huffman()

    @Test
    fun huffmanEncode() {
        val encodedTestString = "001101101100110001011"

       assertEquals(encodedTestString, testHuffman.encode(Parser().input))
    }

    @Test
    fun huffmanDecode() {
//        val encoded = testHuffman.huffmanEncode(Parser().input)
//        val tree = testHuffman.huffmanTreeTraversal(testHuffman.codeTreeNodes)
//        val decode = testHuffman.huffmanDecode(encoded, tree)

     //   assertEquals("abracadabra", decode)
    }
}