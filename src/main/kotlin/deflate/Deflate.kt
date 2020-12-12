package deflate

import huffman.Huffman
import lz77.LZ77
import lz77.LZ77Node

/**
 * Класс Deflate
 * Обьединяет в себе 2 компрессора LZ77 И Сжатие Хаффмана
 */
class Deflate : Compressor<LZ77Node> {
    private val huffman = Huffman()
    private val lZ77 = LZ77()

    override fun encode(source: String): List<LZ77Node> {
        val lz77Encoded = lZ77.encode(source)
        var intermediateString = ""
        for (node in lz77Encoded) {
            intermediateString += node.next
        }
        val huffmanEncoded = huffman.encode(intermediateString)
        for ((i, node) in lz77Encoded.withIndex()) {
            node.next = huffmanEncoded[i]
        }
        return lz77Encoded
    }
}