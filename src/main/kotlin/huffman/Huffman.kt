package huffman

import deflate.Compressor

class Huffman : Compressor {
    val codeTreeNodes = mutableListOf<HuffmanNode>()

    /**
     * Кодирование по Хаффману
     */
    override fun encode(str: String): String {
        for (char in Parser().countFrequency(str).keys) {
            codeTreeNodes.add(HuffmanNode(char, Parser().countFrequency(str)[char]))
        }
        val tree = Huffman().huffmanTreeTraversal(codeTreeNodes)
        val codes = tree.fillCodesTable()

        var encoded = ""
        for (element in Parser().input) {
            encoded += codes[element]
        }

        return encoded
    }


    /**
     * Обход дерева Хаффмана
     */
    fun huffmanTreeTraversal(codeTreeNodes: MutableList<HuffmanNode>): HuffmanNode {
        while (codeTreeNodes.size > 1) {
            codeTreeNodes.sort()
            val left = codeTreeNodes.removeAt(codeTreeNodes.size - 1)
            val right = codeTreeNodes.removeAt(codeTreeNodes.size - 1)
            val parent = HuffmanNode(null, right.weight!! + left.weight!!, left, right)

            codeTreeNodes.add(parent)
        }
        return codeTreeNodes[0]
    }

    /**
     * Декодирование по Хаффману
     */
    fun decode(encoded: String, tree: HuffmanNode): String {
        var decoded = ""
        var node = tree;

        for (i in encoded.indices) {
            node = if (encoded[i] == '0') {
                node.leftChild!!
            } else {
                node.rightChild!!
            }
            if (node.content != null) {
                decoded += node.content
                node = tree
            }
        }
        return decoded
    }

}