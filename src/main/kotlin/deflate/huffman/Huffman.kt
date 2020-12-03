package deflate.huffman

import Compressor

class Huffman : Compressor {
    val codeTreeNodes = mutableListOf<CodeTreeNode>()

    /**
     * Кодирование по Хаффману
     */
    override fun encode(sourceText: String): String {
        for (char in Parser().countFrequency(sourceText).keys) {
            codeTreeNodes.add(CodeTreeNode(char, Parser().countFrequency(sourceText)[char]))
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
    fun huffmanTreeTraversal(codeTreeNodes: MutableList<CodeTreeNode>): CodeTreeNode {
        while (codeTreeNodes.size > 1) {
            codeTreeNodes.sort()
            val left = codeTreeNodes.removeAt(codeTreeNodes.size - 1)
            val right = codeTreeNodes.removeAt(codeTreeNodes.size - 1)
            val parent = CodeTreeNode(null, right.weight!! + left.weight!!, left, right)

            codeTreeNodes.add(parent)
        }
        return codeTreeNodes[0]
    }

    /**
     * Декодирование по Хаффману
     */
    fun decode(encoded: String, tree: CodeTreeNode): String {
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