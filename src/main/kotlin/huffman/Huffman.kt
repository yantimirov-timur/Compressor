package huffman

import deflate.Compressor

/**
 * Алгоритм сжатия Хаффмана
 */
class Huffman : Compressor<String> {
    private val huffmanNodes = mutableListOf<HuffmanNode>()
    /**
     * Кодирование по Хаффману
     */
    override fun encode(source: String): MutableList<String> {
        val frequencies = Parser().countFrequency(source).keys
        for (char in frequencies) {
            huffmanNodes.add(HuffmanNode(char, Parser().countFrequency(source)[char]))
        }
        val tree = Huffman().huffmanTreeTraversal(huffmanNodes)
        val codes = tree.fillCodesTable(frequencies)
        val encoded = mutableListOf<String>()
        for (element in source) {
            codes[element]?.let { encoded.add(it) }
        }
        return encoded
    }

    /**
     * Обход дерева Хаффмана
     */
    private fun huffmanTreeTraversal(codeTreeNodes: MutableList<HuffmanNode>): HuffmanNode {
        while (codeTreeNodes.size > 1) {
            codeTreeNodes.sort()
            val left = codeTreeNodes.removeAt(codeTreeNodes.size - 1)
            val right = codeTreeNodes.removeAt(codeTreeNodes.size - 1)
            val parent = HuffmanNode(null, right.weight!! + left.weight!!, left, right)
            codeTreeNodes.add(parent)
        }
        return codeTreeNodes[0]
    }
}