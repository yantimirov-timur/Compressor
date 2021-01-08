package huffman

import java.util.*

/**
 * Дерево Хаффмана
 * Обход и составление кодов для битов
 */
class HuffmanTree(frequencyMap: Map<Byte, Int>) {
    class IntHolder {
        //фикс
        var value = 0
    }

    private val root: HuffmanTreeNode

    /**
     * Вложенный класс описывающий узлы
     */
    data class HuffmanTreeNode(
        var character: Byte,
        var frequency: Int,
        var isLeaf: Boolean
    ) : Comparable<HuffmanTreeNode?> {

        var left: HuffmanTreeNode? = null
        var right: HuffmanTreeNode? = null

        override fun compareTo(other: HuffmanTreeNode?): Int {
            val cmp = frequency.compareTo(other!!.frequency)
            return if (cmp != 0)
                cmp
             else character.compareTo(other.character)
        }

        companion object {
            fun merge(node1: HuffmanTreeNode, node2: HuffmanTreeNode): HuffmanTreeNode {
                val newNode = HuffmanTreeNode(0.toByte(), node1.frequency + node2.frequency, false)

                if (node1.frequency < node2.frequency) {
                    newNode.left = node1
                    newNode.right = node2
                } else {
                    newNode.left = node2
                    newNode.right = node1
                }
                newNode.character = maxOf(node1.character, node2.character)
                return newNode
            }
        }
    }

    /**
     * Декодирование битовой строки
     */
    fun decodeBitString(index: IntHolder, bitString: BitString): Byte {
        if (root.isLeaf) {
            index.value++
            return root.character
        }
        var currentNode: HuffmanTreeNode? = root
        while (!currentNode!!.isLeaf) {
            val bit = bitString.readBit(index.value++)
            currentNode = if (bit) currentNode.right else currentNode.left
        }
        return currentNode.character
    }

    /**
     * Создание таблицы закодированных битов по дереву
     */
    fun fillFrequencyTable(): Map<Byte, BitString> {
        val frequencyTable = mutableMapOf<Byte, BitString>().toSortedMap()

        if (root.isLeaf) {
            root.isLeaf = false
            root.left = HuffmanTreeNode(root.character, 1, true)
            val bs = BitString()
            bs.appendBit(false)

            frequencyTable[root.character] = bs
            return frequencyTable
        }
        val bitStringBuilder = BitString()
        huffmanTreeTraversal(bitStringBuilder, root, frequencyTable)

        return frequencyTable
    }

    /**
     * Обход дерева в глубину и создание кодов
     */
    private fun huffmanTreeTraversal(
        currentCodeWord: BitString,
        currentTreeNode: HuffmanTreeNode?,
        map: MutableMap<Byte, BitString>
    ) {
        if (currentTreeNode!!.isLeaf) {
            map[currentTreeNode.character] = BitString(currentCodeWord)
            return
        }
        currentCodeWord.appendBit(false)
        huffmanTreeTraversal(currentCodeWord, currentTreeNode.left, map)
        currentCodeWord.removeLastBit()

        currentCodeWord.appendBit(true)
        huffmanTreeTraversal(currentCodeWord, currentTreeNode.right, map)
        currentCodeWord.removeLastBit()
    }


    init {
        require(frequencyMap.isNotEmpty()) { "Compressor requires a non-empty text." }
        val queue: Queue<HuffmanTreeNode> = PriorityQueue()

        for ((key, value) in frequencyMap) {
            queue.add(HuffmanTreeNode(key, value, true))
        }

        while (queue.size > 1) {
            val node1 = queue.remove()
            val node2 = queue.remove()
            queue.add(HuffmanTreeNode.merge(node1, node2))
        }
        root = queue.peek()
    }
}