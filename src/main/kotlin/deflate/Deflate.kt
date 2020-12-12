package deflate

import huffman.Huffman
import huffman.Parser
import lz77.LZ77

class Deflate {
    val huffman = Huffman()
    val lZ77 = LZ77()

    val lz77Encoded = lZ77.encode("abracadabra")

    val encoded = huffman.encode(Parser().input)
    val tree = huffman.huffmanTreeTraversal(huffman.codeTreeNodes)
    val decoded = huffman.decode(encoded, tree)

    fun deflateCompression() {
        TODO()
    }
}